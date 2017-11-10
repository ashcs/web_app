package web_app2;

import static java.lang.String.format;

public class Config {

	String prepareDir; 
	String inputDir;
	String outputDir;
	String connection;
	String logDir;
	String hibernateDialect;
	String hibernateDriverClass;
	String hibernateConnectionUrl;
	String hibernateUsername;
	String hibernatePassword;
	
	public String getPrepareDir() {
		return prepareDir;
	}
	public void setPrepareDir(String prepareDir) {
		this.prepareDir = prepareDir;
	}
	public String getInputDir() {
		return inputDir;
	}
	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}
	public String getOutputDir() {
		return outputDir;
	}
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String Connection) {
		connection = Connection;
	}
	public String getLogDir() {
		return logDir;
	}
	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public String getHibernateDialect() {
		return hibernateDialect;
	}
	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}
	public String getHibernateDriverClass() {
		return hibernateDriverClass;
	}
	public void setHibernateDriverClass(String hibernateDriverClass) {
		this.hibernateDriverClass = hibernateDriverClass;
	}
	public String getHibernateConnectionUrl() {
		return hibernateConnectionUrl;
	}
	public void setHibernateConnectionUrl(String hibernateConnectionUrl) {
		this.hibernateConnectionUrl = hibernateConnectionUrl;
	}
	public String getHibernateUsername() {
		return hibernateUsername;
	}
	public void setHibernateUsername(String hibernateUsername) {
		this.hibernateUsername = hibernateUsername;
	}
	public String getHibernatePassword() {
		return hibernatePassword;
	}
	public void setHibernatePassword(String hibernatePassword) {
		this.hibernatePassword = hibernatePassword;
	}	
    @Override
    public String toString() {
        return new StringBuilder()
            .append( format( "Input Dir: %s\n", inputDir ) )
            .append( format( "Output Dir: %s\n", outputDir ) )
            .append( format( "Connecting to database: %s\n", connection ) )
            .append( format( "Log Dir: %s\n", logDir ) )
            .toString();
    }
}
