package org.candango.carcara.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import org.candango.carcara.engine.mysql.MysqlDatabaseLoader;
import org.candango.carcara.engine.pgsql.PgsqlDatabaseLoader;
import org.candango.carcara.model.database.Table;

public abstract class AbstractDatabaseLoader implements DatabaseLoader {
	
	public static final int MYSQL_DATABASE = 1;
	
	public static final int PGSQL_DATABASE = 2;
	
	private Connection conn;
	
	private ArrayList<Table> tableList = new ArrayList<Table>();
	
	public static DatabaseLoader getLoader( int witchLoader ) {
		
		switch( witchLoader ) {
			case( MYSQL_DATABASE ):
				return new MysqlDatabaseLoader();
			case( PGSQL_DATABASE ):
				return new PgsqlDatabaseLoader();
		}
		
		return null;
	}
	
	public Connection getConnection(){
		return conn;
	}
	
	public void setConnection( Connection conn ) {
		this.conn = conn;
	}
	
	public void connect( DatabaseConfiguration configuration ) {
		System.out.println( "Connecting..." );
		try {
			Class.forName( configuration.getDriver() );
			
			setConnection( DriverManager.getConnection( 
					configuration.getUrl(),
					configuration.getUser(), configuration.getPassword() ) );
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println( "Oukie!!" );
		
	}
	
	public void disconnect() {
		try {
			getConnection().close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doLoad( DatabaseConfiguration configuration ) {
		
		loadTables( configuration );
		
		for( Table table : getTables() ) {
			loadFields( configuration, table );
		}
		
	}
	
	protected abstract void loadTables( DatabaseConfiguration configuration );
	
	protected abstract void loadFields( DatabaseConfiguration configuration, Table table );

	protected void addTable( Table table ) {
		tableList.add( table );
	}
	
	public Table[] getTables() {
		Table[] tables = new Table[ tableList.size() ];
		return tableList.toArray( tables );
	}
	
}