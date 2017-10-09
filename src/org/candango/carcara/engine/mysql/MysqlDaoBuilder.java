package org.candango.carcara.engine.mysql;

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
 * This class builds Mysql DAO files
 * 
 * @author Flavio Goncalves Garcia
 *
 */
public class MysqlDaoBuilder extends AbstractDaoBuilder {

	@Override
	protected void buildConcreteDaoFactory(DatabaseConfiguration configuration,
			DatabaseLoader loader) {
		
		String mysqlDaoFileName = getDaoPath() + "/" +
			CodeHandler.upperCaseFirst( configuration.getIdentifier() ) + 
			"MysqlDaoFactory.php";
		
		File mysqlDaoFile = new File( mysqlDaoFileName );
		
		try {
			if( !mysqlDaoFile.exists() ) {
				mysqlDaoFile.createNewFile();
			}
			
			BufferedWriter out = 
				new BufferedWriter( new FileWriter( mysqlDaoFile ) );
			
			out.write( getMysqlDaoFactoryCode( configuration, loader ) );
			
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	protected String getMysqlDaoFactoryCode( DatabaseConfiguration configuration, 
			DatabaseLoader loader ) {
		
		String abstractDaoClassName = 
			CodeHandler.upperCaseFirst( configuration.getIdentifier() ) + 
			"AbstractDaoFactory";
		
		String mysqlDaoClassName = 
			CodeHandler.upperCaseFirst( configuration.getIdentifier() ) + 
			"MysqlDaoFactory";
		
		String out = "<?php\n";
		out += "class " + mysqlDaoClassName + " extends " + 
			abstractDaoClassName +  "{\n\n";
		
		out += "    /**\n";
		out += "     * Configuration database name\n";
		out += "     *\n";
		out += "     * @var string\n";
		out += "     */\n";
		out += "    private $dbName;\n\n";
		
		out += "    private $connection;\n\n";
		out += "    public function __construct() {\n";
		out += "        $iflux = Iflux::getInstance();\n";
		out += "        $conf = require $iflux->getApplication( \""+ 
						configuration.getIdentifier() + "\" " + 
						")->getPath() .\n            \"conf/" + 
						configuration.getIdentifier() + "_conf.php\";\n";
		out += "        $connStr = \"mysql:host=\" . $conf[ 'server' ] . " + 
						"\";dbname=\" .\n            $conf[ 'database' ];\n";
		out += "        $this->dbName = $conf[ 'database' ];\n";
		
		out += "        try {\n";
		out += "            $this->connection = new PDO( $connStr, " + 
							"$conf[ 'user' ],\n" + 
							"                 $conf[ 'password' ] );\n";
		out += "        }\n";
		out += "        catch ( Exception $e ) {\n";
		out += "            echo \"Failed: \" . $e->getMessage();\n";
		out += "        }\n";
		out += "    }\n\n";
		
		out += "    /**\n";
		out += "     * Returns the configuration database name\n";
		out += "     *\n";
		out += "     * @returns string\n";
		out += "     */\n";
		out += "    public function getDbName() {\n";
		out += "        return $this->dbName;\n";
		out += "    }\n\n";
		
		out += "    /**\n";
		out += "     * Returns the PDO connection object\n";
		out += "     *\n";
		out += "     * @returns PDO\n";
		out += "     */\n";
		out += "    public function getConnection() {\n";
		out += "        return $this->connection;\n";
		out += "    }\n\n";
		
		for( Table table : loader.getTables() ){
			
			String daoName = getEntitySufix( configuration, table ) + "MysqlDao";
			
			String attrName = CodeHandler.getAttributeName( table.getName() );
			
			String methodName = "get" + CodeHandler.getEntityName( 
					table.getName() ) + "Dao" ;
			
			out += "    /**\n";
			out += "     * Return a new " + daoName + "\n";
			out += "     *\n";
			out += "     * @return " + daoName + "\n";
			out += "     **/\n";
			out += "    public function " + methodName + "(){\n";
			out += "        require_once \"dao/" + table.getName() + "/" +  
				daoName + ".php\";\n";
			out += "        return new " + daoName + "( $this );\n";
			out += "    }\n\n";
		}
		
		out += "}";
		return out;
	}


	@Override
	protected void buildConcreteDao(DatabaseConfiguration configuration,
			Table table) {
		
		String abstractDaoFileName = getTablePath( table ) + "/" +
			getEntitySufix( configuration, table ) + 
			"AbstractMysqlDao.php";
	
		String daoFileName = getTablePath( table ) + "/" +
			getEntitySufix( configuration, table ) + "MysqlDao.php";
		
		File abstractDaoFile = new File( abstractDaoFileName );
		
		File daoFile = new File( daoFileName );
		
		try {
			
			if( !daoFile.exists() ) {
				daoFile.createNewFile();
				
				BufferedWriter out = 
					new BufferedWriter( new FileWriter( daoFile ) );
				
				out.write( getMysqlDaoCode( configuration, table ) );
				
				out.close();
				
				
			}
			
			if( !abstractDaoFile.exists() ) {
				abstractDaoFile.createNewFile();
			}
			
			BufferedWriter out1 = 
				new BufferedWriter( new FileWriter( abstractDaoFile ) );
			
			out1.write( getMysqlAbstractDaoCode( configuration, table ) );
			
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
	private String getMysqlDaoCode( DatabaseConfiguration configuration,
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
			entitySufix + "AbstractMysqlDao.php\";\n\n";
		
		out += "class " + entitySufix + "MysqlDao extends " + 
			entitySufix + "AbstractMysqlDao {\n\n";
		
		out += "}";
		
		return out;
	}
	
	private String getMysqlAbstractDaoCode( DatabaseConfiguration configuration,
			Table table ) {

		// Creating the velocity context
		VelocityContext context = new VelocityContext();

		// adding variables to context
		context.put("identifier-name", configuration.getIdentifier() );

		context.put("identifier-name-upper",
				CodeHandler.upperCaseFirst( configuration.getIdentifier() ) );

		context.put( "table", table );

		String out = VelocityHandler.getTemplateString( context,
				"template/mysql/dao/abstract_mysql_dao.vm" );

		return out;
	}
	
}