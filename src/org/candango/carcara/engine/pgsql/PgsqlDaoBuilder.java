package org.candango.carcara.engine.pgsql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.velocity.VelocityContext;
import org.candango.carcara.engine.AbstractDaoBuilder;
import org.candango.carcara.engine.DatabaseConfiguration;
import org.candango.carcara.engine.DatabaseLoader;
import org.candango.carcara.model.database.Field;
import org.candango.carcara.model.database.Table;
import org.candango.carcara.util.CodeHandler;
import org.candango.carcara.util.VelocityHandler;

/**
 * This class builds Pgsql DAO files
 * 
 * @author Flavio Goncalves Garcia
 *
 */
public class PgsqlDaoBuilder extends AbstractDaoBuilder {

	@Override
	protected void buildConcreteDaoFactory(DatabaseConfiguration configuration,
			DatabaseLoader loader) {
		
		String pgsqlDaoFileName = getDaoPath() + "/" +
			CodeHandler.upperCaseFirst( configuration.getIdentifier() ) + 
			"PgsqlDaoFactory.php";
		
		File pgsqlDaoFile = new File( pgsqlDaoFileName );
		try {
			if( !pgsqlDaoFile.exists() ) {
				pgsqlDaoFile.createNewFile();
			}
			
			BufferedWriter out = 
				new BufferedWriter( new FileWriter( pgsqlDaoFile ) );
			
			out.write( getPgsqlDaoFactoryCode( configuration, loader ) );
			
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param configuration
	 * @param loader
	 * @return
	 */
	protected String getPgsqlDaoFactoryCode( DatabaseConfiguration configuration, 
			DatabaseLoader loader ) {
		// Creating the velocity context  
		VelocityContext context = new VelocityContext();  
		
		// adding variables to context 
		context.put("identifier-name", configuration.getIdentifier() );
		
		context.put("identifier-name-upper", 
				CodeHandler.upperCaseFirst( configuration.getIdentifier() ) );
		
		context.put( "tables", loader.getTables() );
		
		String out = VelocityHandler.getTemplateString( context, 
				"template/pgsql/dao/pgsql_dao_factory.vm" );
		
		return out;
	}


	@Override
	protected void buildConcreteDao(DatabaseConfiguration configuration,
			Table table) {
		
		String abstractDaoFileName = getTablePath( table ) + "/" +
			getEntitySufix( configuration, table ) + 
			"AbstractPgsqlDao.php";
	
		String daoFileName = getTablePath( table ) + "/" +
			getEntitySufix( configuration, table ) + "PgsqlDao.php";
		
		File abstractDaoFile = new File( abstractDaoFileName );
		
		File daoFile = new File( daoFileName );
		
		try {
			
			if( !daoFile.exists() ) {
				daoFile.createNewFile();
			}
			
			BufferedWriter out = 
				new BufferedWriter( new FileWriter( daoFile ) );
			
			out.write( getPgsqlDaoCode( configuration, table ) );
			
			out.close();
			
			if( !abstractDaoFile.exists() ) {
				abstractDaoFile.createNewFile();
			}
			
			BufferedWriter out1 = 
				new BufferedWriter( new FileWriter( abstractDaoFile ) );
			
			out1.write( getPgsqlAbstractDaoCode( configuration, table ) );
			
			out1.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param configuration
	 * @param table
	 * @return
	 */
	private String getPgsqlDaoCode( DatabaseConfiguration configuration,
			Table table ) {
		
		String entitySufix = getEntitySufix( configuration, table );
		
		/*String tableSufix = CodeHandler.getEntityName( table.getName() );
		
		String tableAttr = CodeHandler.getAttributeName( table.getName() );*/
		
		String pks = "";
		
		for( Field field : table.getFields() ) {
			if( field.isPk() ) {
				pks += ( pks.equals( "" ) ? "" : ", " ) + "$" + 
					CodeHandler.getAttributeName( field.getName() );
			}
		}
		
		String out = "<?php\n";
		
		out += "require_once \"" + "dao/" + table.getName() +  "/" +
			entitySufix + "AbstractPgsqlDao.php\";\n\n";
		
		out += "class " + entitySufix + "PgsqlDao extends " + 
			entitySufix + "AbstractPgsqlDao {\n\n";
		
		out += "}";
		
		return out;
	}
	
	private String getPgsqlAbstractDaoCode( DatabaseConfiguration configuration,
			Table table ) {
		
		// Creating the velocity context  
		VelocityContext context = new VelocityContext();  
		
		// adding variables to context 
		context.put("identifier-name", configuration.getIdentifier() );
		
		context.put("identifier-name-upper", 
				CodeHandler.upperCaseFirst( configuration.getIdentifier() ) );
		
		context.put( "table", table );
		
		String out = VelocityHandler.getTemplateString( context, 
				"template/pgsql/dao/abstract_pgsql_dao.vm" );
		
		return out;
	}
	
}