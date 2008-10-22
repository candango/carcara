package org.candango.carcara.engine;

public interface DatabaseConfiguration {
	
	public String getUrl();
	
	public String getHost();

	public void setHost(String host);

	public String getPort();

	public void setPort(String port);

	public String getDatabase();

	public void setDatabase(String database);

	public String getPassword();

	public void setPassword(String password);

	public String getUser();

	public void setUser(String user);
	
	public String getDriver();
	
	public String getIdentifier();
	
}