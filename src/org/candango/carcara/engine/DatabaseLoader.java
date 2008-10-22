package org.candango.carcara.engine;

import java.sql.Connection;

import org.candango.carcara.model.database.Table;

public interface DatabaseLoader {

	public void connect( DatabaseConfiguration configuration );
	
	public Connection getConnection();
	
	public void setConnection( Connection conn );
	
	public void disconnect();
	
	public DatabaseConfiguration getConfiguration();
	
	public void doLoad( DatabaseConfiguration configuration );
	
	public Table[] getTables();
	
}