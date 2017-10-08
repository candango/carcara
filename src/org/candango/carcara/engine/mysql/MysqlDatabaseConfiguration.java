package org.candango.carcara.engine.mysql;

import org.candango.carcara.engine.AbstractDatabaseConfiguration;

public class MysqlDatabaseConfiguration extends AbstractDatabaseConfiguration {

	public MysqlDatabaseConfiguration() {
		super();
		setDriver( "com.mysql.cj.jdbc.Driver" );
		setPort( "3306" );
	}
	
	public String getUrl() {
		// Added serverTimeZone explicitly to use UTC time zone
		// @see https://stackoverflow.com/questions/26515700/mysql-jdbc-driver-5-1-33-time-zone-issue
		String url = "jdbc:mysql://" + getHost() + ":" + getPort() + "/" + 
			getDatabase() + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&" +
				"serverTimezone=UTC";
		return url;
	}

	public String getIdentifier() {
		return getDatabase();
	}

}
