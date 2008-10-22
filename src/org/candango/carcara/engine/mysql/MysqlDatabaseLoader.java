package org.candango.carcara.engine.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.candango.carcara.engine.AbstractDatabaseLoader;
import org.candango.carcara.engine.DatabaseConfiguration;
import org.candango.carcara.model.database.Field;
import org.candango.carcara.model.database.Table;

public class MysqlDatabaseLoader extends AbstractDatabaseLoader {
	
	public DatabaseConfiguration getConfiguration() {
		return new MysqlDatabaseConfiguration();
	}
	
	protected void loadTables( DatabaseConfiguration configuration ) {
		Statement statement = null;
		
		try {
			statement = getConnection().createStatement();
			
			ResultSet resultSet = statement.executeQuery( "SHOW TABLES;" );
			
			while( resultSet.next() ) {
				Table table = new Table();
				table.setName( "" + resultSet.getObject( 1 ) );
				addTable( table );
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void loadFields( DatabaseConfiguration configuration, Table table ) {
		
		Statement statement = null;
		
		try {
			statement = getConnection().createStatement();
			
			ResultSet resultSet = statement.executeQuery( 
					"SHOW FIELDS FROM " + table.getName() + ";" );
			
			while( resultSet.next() ) {
				Field field = new Field();
				field.setName( "" + resultSet.getObject( 1 ) );
				field.setType( "" + resultSet.getObject( 2 ) );
				
				field.setPk( resultSet.getObject( 4 ) .equals( "PRI" ) ? true : 
					false );
				
				table.addField( field );
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}