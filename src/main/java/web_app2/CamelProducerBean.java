package web_app2;

import java.io.File;
import org.apache.camel.Exchange;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CamelProducerBean {
	
	public void filter(Exchange exchange, String name) throws Exception {
		
		Logger logger = LogManager.getLogger(TestRouteBuilder.class);
		Pattern r = Pattern.compile("^json_\\d{6}_\\d{4}$");
		Matcher m = r.matcher(name);
		
		if (!m.find()) {
    		logger.info("Invalid filename " + name);
    		return;			
		}

    	File dataFile = new File(ConfigFactory.getConfig().getInputDir() + "/" + name + ".txt");
    	
    	if (!dataFile.exists()) {
    		logger.info(dataFile.getPath() + " not_exist");
    		return;
    	}

    	ObjectMapper objectMapper = new ObjectMapper();
		
		final JsonNode schemaNode = objectMapper.readTree(CamelProducerBean.class.getResourceAsStream("/schema.json"));
	    final JsonNode data = objectMapper.readTree(dataFile);
	    
	    JsonValidator VALIDATOR  = JsonSchemaFactory.byDefault().getValidator();
	    ProcessingReport report = VALIDATOR.validateUnchecked(schemaNode, data);
    	
    	if (!report.isSuccess()) {
    		logger.info(report);
    		return;
    	}
		
    	File readyFile = new File((String) exchange.getIn().getHeader("CamelFileAbsolutePath"));
    	readyFile.delete();
		
    	dataFile.renameTo(new File(ConfigFactory.getConfig().getOutputDir()+ "/" + dataFile.getName()));

	}
}
