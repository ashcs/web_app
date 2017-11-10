package web_app2;

import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionProvider {

	private static Session session;
	private static Configuration configuration;
	
	private HibernateSessionProvider() { }
	
	static {
		
		configuration = new Configuration()
	    
		.setProperty("hibernate.dialect", ConfigFactory.getConfig().getHibernateDialect())
	    .setProperty("hibernate.connection.driver_class", ConfigFactory.getConfig().getHibernateDriverClass())
	    .setProperty("hibernate.connection.url", ConfigFactory.getConfig().getHibernateConnectionUrl())
	    .setProperty("hibernate.connection.username", ConfigFactory.getConfig().getHibernateUsername())
	    .setProperty("hibernate.connection.password", ConfigFactory.getConfig().getHibernatePassword())
	    .addAnnotatedClass(web_app2.PhonebookModel.class)
	    .addAnnotatedClass(web_app2.JobModel.class);
	}
	
	public static Session getSession() {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		SessionFactory factory = configuration.buildSessionFactory();
		session = factory.openSession();
		return session;
	}

}
