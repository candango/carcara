package org.candango.carcara.engine.mysql;

import org.candango.carcara.engine.AbstractDatabaseConfiguration;

public class MysqlDatabaseConfiguration extends AbstractDatabaseConfiguration {

	public MysqlDatabaseConfiguration() {
		super();
		setDriver( "com.mysql.jdbc.Driver" );
		setPort( "3306" );
	}
	
	public String getUrl() {
		String url = "jdbc:mysql://" + getHost() + ":" + getPort() + "/" + 
			getDatabase();
		return url;
	}

	public String getIdentifier() {
		return getDatabase();
	}

}
