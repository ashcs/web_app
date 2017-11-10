package web_app2;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.Session;
import javax.validation.*;

/**
 * Servlet implementation class JobServlet
 */
public class JobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JobServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userPath = request.getServletPath();
		
		if ("/add".equals(userPath)) {
			response.setContentType("application/json; charset=UTF-8;");
			JSONObject json = new JSONObject();
			
			Session session = HibernateSessionProvider.getSession();
			session.beginTransaction();
			
			JobModel job = new JobModel();
			
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			
			job.setLastname(request.getParameter("lastname"));
			job.setFirstname(request.getParameter("firstname"));
			job.setWorkaddress(request.getParameter("workaddress"));			
			job.setWorkplace(request.getParameter("workplace"));

			Set<ConstraintViolation<JobModel>> violations = validator.validate(job);
			
			if (violations.isEmpty()) {
		        session.save(job);
		        session.getTransaction().commit();	
				json.put("result", "1");
				json.put("message", "Запись для <strong>'" + job.getFirstname() + " " + job.getLastname() + "'</strong> добавлена");
				
			}
			else {
				json.put("result", "0");
				JSONArray list = new JSONArray();
				for (ConstraintViolation<JobModel> violation : violations) {
					list.add(violation.getMessage()); 
				}
				json.put("errors", list);
			}
	        session.close();
	        response.getWriter().write(json.toString());
			return;			
			

		}
		else 
		if ("/find".equals(userPath)) {
			response.setContentType("application/json; charset=UTF-8;");
			JSONObject json = new JSONObject();
			
			Session session = HibernateSessionProvider.getSession();
			session.beginTransaction();

			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<JobModel> criteria = builder.createQuery( JobModel.class );
			Root<JobModel> root = criteria.from( JobModel.class );

			criteria.select( root ).where(
				builder.and(builder.like(root.<String>get("lastname"), "%"+request.getParameter("lastname")+"%")),
				builder.and(builder.like(root.<String>get("firstname"), "%"+request.getParameter("firstname")+"%")),
				builder.and(builder.like(root.<String>get("workplace"), "%"+request.getParameter("workplace")+"%")),
				builder.and(builder.like(root.<String>get("workaddress"), "%"+request.getParameter("workaddress")+"%"))
			);

			List<JobModel> jobs = session.createQuery( criteria ).getResultList();
			
			JSONArray list = new JSONArray();
			
	        for (JobModel job : jobs) {
	            list.add("<td>" + job.getId() + "</td><td>" + job.getLastname() + "</td><td>" + job.getFirstname() + "</td><td>" + job.getWorkplace() + "</td><td>" + job.getWorkaddress() + "</td>");
	        }

	        session.getTransaction().commit();
	        session.close();
	        
			json.put("rows", list);
			json.put("result", "1");
	        response.getWriter().write(json.toString());
			return;			
		}
		else {
			doGet(request, response);
		}
	}

}
