package org.candango.carcara.engine.pgsql;

import org.candango.carcara.engine.AbstractDatabaseConfiguration;

public class PgsqlDatabaseConfiguration extends AbstractDatabaseConfiguration {
	
	private String schema;
	
	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public PgsqlDatabaseConfiguration() {
		super();
		setDriver( "org.postgresql.Driver" );
		setPort( "5432" );
	}
	
	public String getUrl() {
		String url = "jdbc:postgresql://" + getHost() + ":" + getPort() + "/" + 
			getDatabase();
		return url;
	}

	public String getIdentifier() {
		return getSchema();
	}
	
}
