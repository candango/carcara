package org.candango.carcara.engine.pgsql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.candango.carcara.engine.AbstractDatabaseLoader;
import org.candango.carcara.engine.DatabaseConfiguration;
import org.candango.carcara.model.database.Field;
import org.candango.carcara.model.database.Table;

public class PgsqlDatabaseLoader extends AbstractDatabaseLoader {
	
	public DatabaseConfiguration getConfiguration() {
		return new PgsqlDatabaseConfiguration();
	}
	
	protected void loadTables( DatabaseConfiguration configuration ) {
		Statement statement = null;
		
		try {
			statement = getConnection().createStatement();
			
			ResultSet resultSet = statement.executeQuery( "select table_name " + 
					" from information_schema.tables where table_schema='" + 
					( (PgsqlDatabaseConfiguration) 
							configuration ).getSchema() +  
							"' and table_type='BASE TABLE'" );
			
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
	protected void loadFields( DatabaseConfiguration configuration, 
			Table table ) {
		
		Statement statement = null;
		
		try {
			statement = getConnection().createStatement();
			
			String schema = ( (PgsqlDatabaseConfiguration)
				configuration ).getSchema();
			
			String sql = "SELECT c.column_name, c.data_type, " + 
				"c.column_default  " +
            	" FROM information_schema.columns c " +
            	" WHERE c.table_schema='" + schema + "' and " + 
            	" c.table_name='" + table.getName() + "';";
			
			ResultSet resultSet = statement.executeQuery( sql );
			
			Statement statement1 = null;
			
			while( resultSet.next() ) {
				
				
				Field field = new Field();
				field.setName( "" + resultSet.getObject( 1 ) );
				field.setType( "" + resultSet.getObject( 2 ) );
				
				String cDef = resultSet.getString( 3 );
				if( cDef != null ) {
					if( cDef.length() > 7 ) {
						boolean serial = resultSet.getString( 3 ).substring( 0, 7 ).
							toLowerCase().matches( "nextval" );
					
						field.setSerial( serial );
					}
				}
				
				
				
				//System.out.println( resultSet.getObject( 1 ) + "  " + resultSet.getObject( 2 ) );
				/*field.setPk( resultSet.getInt( 3 ) == 0 ? false : 
					true );*/
				
				statement1 = getConnection().createStatement();
				
				String sql1 = "SELECT tc.constraint_name, tc.constraint_type " +
					" FROM information_schema.table_constraints tc " +  
					" INNER JOIN information_schema.constraint_column_usage ccu " + 
					" ON tc.constraint_name = ccu.constraint_name " + 
					"WHERE  tc.table_schema='" + schema + "' AND " +  
					" tc.table_name='" + table.getName() + "' AND " + 
					" ccu.column_name = '" + 
					resultSet.getObject( 1 ) + "' AND " + 
					" tc.constraint_type='PRIMARY KEY';";
				
				ResultSet resultSet1 = statement1.executeQuery( sql1 );
				
				while( resultSet1.next() ) {
					field.setPk( true );
				}
				
				table.addField( field );
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}