package web_app2;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.examples.Utils;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;



/**
 * Servlet implementation class PhonebookServlet
 */
public class PhonebookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PhonebookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userPath = request.getServletPath();
		
		if ("/phone/add".equals(userPath)) {
			doAdd(request, response);
			return;
		}

		if ("/phone/clear".equals(userPath)) {
			doClear(request, response);
			return;
		}
		
		if ("/phone/send".equals(userPath)) {
			doSend(request, response);
			return;
		}
		
		if ("/phone/find".equals(userPath)) {
			doFind(request, response);
			return;
		}
		
	}
	
	protected void doSend(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=UTF-8;");
		File file = new File(ConfigFactory.getConfig().getPrepareDir() + "/" + request.getParameter("filename"));
		
		DateFormat dateFormat = new SimpleDateFormat("HHmm");
		
		String filename = "json_" + request.getParameter("filename").substring(5, 11) + "_" + dateFormat.format(new Date());
		
		file.renameTo(new File(ConfigFactory.getConfig().getInputDir()+ "/" + filename  + ".txt"));
		
		file = new File(ConfigFactory.getConfig().getInputDir()+ "/" + filename  + ".ready");
		file.createNewFile();
		
	}
	
	protected void doClear(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=UTF-8;");
		File file = new File(ConfigFactory.getConfig().getPrepareDir() + "/" + request.getParameter("filename"));
        file.delete();
        JSONObject json = new JSONObject();
		json.put("result", "1");
		json.put("message", "Запись очищена");
		response.getWriter().write(json.toString());

	}

	
	protected void doAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=UTF-8;");
		JSONObject json = new JSONObject();
		
		PhonebookModel phonebook = new PhonebookModel();
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		
		phonebook.setLastname(request.getParameter("lastname"));
		phonebook.setFirstname(request.getParameter("firstname"));
		phonebook.setWorkphone(request.getParameter("workphone"));			
		phonebook.setMobilephone(request.getParameter("mobilephone"));
		phonebook.setEmail(request.getParameter("email"));
		phonebook.setBirthdate(request.getParameter("birthdate"));

		Set<ConstraintViolation<PhonebookModel>> violations = validator.validate(phonebook);
		
		if (violations.isEmpty()) {
			if (!phonebook.getWorkphone().substring(0, 2).equals(phonebook.getMobilephone().substring(0, 2))) {
				JSONArray list = new JSONArray();
				list.add("Коды рабочего и мобильного телефона должны совпадать");
				json.put("result", "0");
				json.put("errors", list);
			}
			else {
				//session.save(phonebook);
				//session.getTransaction().commit();	
		
			    File directory = new File(ConfigFactory.getConfig().getPrepareDir());
			    if (! directory.exists()){
			        directory.mkdirs();
			    }				

				DateFormat dateFormat = new SimpleDateFormat("ddMMyy_HHmm");
			    
				String filename = "data_" + dateFormat.format(new Date()) + ".json";
				
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(new File(ConfigFactory.getConfig().getPrepareDir() + "/" + filename), phonebook);
				json.put("result", "1");
				json.put("filename", filename);
				json.put("message", "Запись для <strong>'" + phonebook.getFirstname() + " " + phonebook.getLastname() + "'</strong> добавлена");
				
			}
		} 
		else {
			json.put("result", "0");
			JSONArray list = new JSONArray();
			for (ConstraintViolation<PhonebookModel> violation : violations) {
				list.add(violation.getMessage()); 
			}
			json.put("errors", list);
		}

        response.getWriter().write(json.toString());
		return;		
	}

	protected void doFind(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("application/json; charset=UTF-8;");
		JSONObject json = new JSONObject();
		
		Session session = HibernateSessionProvider.getSession();
		session.beginTransaction();
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<PhonebookModel> criteria = builder.createQuery( PhonebookModel.class );
		Root<PhonebookModel> root = criteria.from( PhonebookModel.class );

		criteria.select( root ).where(
			builder.and(builder.like(root.<String>get("lastname"), "%"+request.getParameter("lastname")+"%")),
			builder.and(builder.like(root.<String>get("firstname"), "%"+request.getParameter("firstname")+"%")),
			builder.and(builder.like(root.<String>get("workphone"), "%"+request.getParameter("workphone")+"%")),
			builder.and(builder.like(root.<String>get("mobilephone"), "%"+request.getParameter("mobilephone")+"%")),
			builder.and(builder.like(root.<String>get("email"), "%"+request.getParameter("email")+"%")),
			builder.and(builder.like(root.<String>get("birthdate"), "%"+request.getParameter("birthdate")+"%"))
		);

		List<PhonebookModel> jobs = session.createQuery( criteria ).getResultList();
		
		JSONArray list = new JSONArray();
		
        for (PhonebookModel job : jobs) {
            list.add("<td>" + job.getId() + "</td><td>" + job.getLastname() + "</td><td>" + job.getFirstname() + "</td><td>" + job.getWorkphone() + "</td><td>" + job.getMobilephone() + "</td><td>" + job.getEmail() + "</td><td>" + job.getBirthdate() + "</td>");
        }

        session.getTransaction().commit();
        session.close();
        
		json.put("rows", list);
		json.put("result", "1");
        response.getWriter().write(json.toString());
		return;			
	}
}
