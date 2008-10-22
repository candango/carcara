package org.candango.carcara.engine;

public abstract class AbstractDatabaseConfiguration 
	implements DatabaseConfiguration {
	
	private String driver = "";
	
	private String host = "";
	
	private String port = "";
	
	private String database = "";
	
	private String password = "";
	
	private String user = "";

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String getDriver() {
		return driver;
	}
	
	protected void setDriver( String driver ) {
		this.driver = driver;
	}
	
	
	
}
