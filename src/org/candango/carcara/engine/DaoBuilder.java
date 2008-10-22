package org.candango.carcara.engine;


public interface DaoBuilder {
	
	public String getPath();
	
	public void setPath( String path );
	
	public void build( DatabaseConfiguration configuration, DatabaseLoader loader );
	
}