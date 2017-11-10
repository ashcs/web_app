package web_app2;

import org.apache.camel.builder.RouteBuilder;

public class TestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {
    	
    	from("file:" + ConfigFactory.getConfig().getInputDir() + "?include=json_.*\\.ready&noop=true").
    		log("Detect ${file:name}").
    		to("bean:producerBean?method=filter(${exchange},${file:name.noext})");
    	from("file:" + ConfigFactory.getConfig().getOutputDir() + "?include=json_.*\\.txt&noop=true").
    		log("Processing ${file:name}").
    		process(new CamelContentEnricher()).
    		to("file:" + ConfigFactory.getConfig().getOutputDir());
    }
}