package org.candango.carcara;

import org.candango.carcara.engine.AbstractDatabaseLoader;
import org.candango.carcara.engine.DaoBuilder;
import org.candango.carcara.engine.DatabaseLoader;
import org.candango.carcara.engine.mysql.MysqlDatabaseConfiguration;
import org.candango.carcara.engine.mysql.MysqlDaoBuilder;
import org.candango.carcara.ui.MainFrame;

public class MainApp {
	
	public static void main( String[] args ) {
		
		
		//MainFrame frame = new MainFrame();
		
		DatabaseLoader loader = AbstractDatabaseLoader.getLoader( 
				
				AbstractDatabaseLoader.MYSQL_DATABASE );
		
		MysqlDatabaseConfiguration configuration = (MysqlDatabaseConfiguration)loader.getConfiguration();
		
		//configuration.setSchema( "intranet" );
		
		configuration.setHost( "localhost" );
		
		configuration.setDatabase( "carlinhosveiga" );
		
		configuration.setUser( "root" );
		
		configuration.setPassword( "rootpass" );
		
		loader.connect( configuration );
		
		loader.doLoad( configuration );
		
		loader.disconnect();
		
		DaoBuilder builder = new MysqlDaoBuilder();
		
		builder.setPath( "./" );
		
		builder.build( configuration, loader );
		
	}
	
}