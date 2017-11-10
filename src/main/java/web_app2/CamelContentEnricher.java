package web_app2;


import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CamelContentEnricher implements Processor {

    public void process(Exchange exchange) throws Exception {

    	Logger logger = LogManager.getLogger(CamelContentEnricher.class);
    	ObjectMapper objectMapper = new ObjectMapper();
    	final JsonNode data = objectMapper.readTree((String) exchange.getIn().getBody(String.class));
    	
	    Session session = HibernateSessionProvider.getSession();
	    session.beginTransaction();	
	    
	    PhonebookModel phonebook = (PhonebookModel) session.createQuery(
	    	    "select j from PhonebookModel j " +
	    	    "where j.lastname like :lastname AND j.firstname like :firstname")
	    	.setParameter("lastname", data.get("lastname").asText())
	    	.setParameter("firstname", data.get("firstname").asText())
	    	.uniqueResult();
	    
	    if  (phonebook != null) {
	    	session.close();
	    	logger.info("Record \"" + data.get("lastname").asText() + "  " + data.get("firstname").asText() + "\" already exist in DB. Ignore.");
	    	exchange.setProperty(Exchange.ROUTE_STOP, true);
	    	return;
	    }
	    
	    
	    JobModel job = (JobModel) session.createQuery(
	    	    "select j from JobModel j " +
	    	    "where j.lastname like :lastname AND j.firstname like :firstname")
	    	.setParameter("lastname", data.get("lastname").asText())
	    	.setParameter("firstname", data.get("firstname").asText())
	    	.uniqueResult();
	    
	    if  (job == null) {
	    	session.close();
	    	logger.info("Record \"" + data.get("lastname").asText() + "  " + data.get("firstname").asText() + "\" not find in DB. Ignore.");
	    	System.out.println("Record \"" + data.get("lastname").asText() + "  " + data.get("firstname").asText() + "\" not find in DB. Ignore.");
	    	exchange.setProperty(Exchange.ROUTE_STOP, true);
	    	return;
	    }
	    
    	((ObjectNode)data).put("work", job.getWorkplace() + " " + job.getWorkaddress()).remove("id");

    	exchange.getIn().setBody(data.toString());

    	phonebook = objectMapper.treeToValue(data, PhonebookModel.class);
    	
		session.save(phonebook);
		session.getTransaction().commit();
		System.out.println("New record for \"" + data.get("lastname").asText() + "  " + data.get("firstname").asText() + "\" enriched && added to DB.");
		logger.info("New record for \"" + data.get("lastname").asText() + "  " + data.get("firstname").asText() + "\" enriched && added to DB.");
    	
    }

}