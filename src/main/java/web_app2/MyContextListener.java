package web_app2;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.util.jndi.JndiContext;

@WebListener
public class MyContextListener implements ServletContextListener{
    
    public void contextInitialized(ServletContextEvent sce) {
    	
    	JndiContext context = null;
		try {
			context = new JndiContext();
			context.bind("producerBean", new CamelProducerBean());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    	
    	CamelContext ctx = new DefaultCamelContext(context);
    	
        try {
        	TestRouteBuilder routeBuilder = new TestRouteBuilder();
            ctx.addRoutes(routeBuilder);
            ctx.start();

        }
        catch (Exception e) {
            e.printStackTrace();
        }  
    }
}
