package web_app2;

import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;

public class ConfigFactory {

	static Config conf;
	
	public static Config getConfig() {
		
		if (conf == null) {
			Yaml yaml = new Yaml(); 
			
			try (InputStream input =  ConfigFactory.class.getResourceAsStream("/config.yaml")) {
				conf = yaml.loadAs( input, Config.class );
			}
			catch (Exception e) {
				System.out.println( e.getMessage());
			}
		}
		
		return conf;
	}
}
