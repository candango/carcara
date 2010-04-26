package org.candango.carcara.engine.mysql;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.candango.carcara.engine.AbstractDaoBuilder;
import org.candango.carcara.engine.DatabaseConfiguration;
import org.candango.carcara.engine.DatabaseLoader;
import org.candango.carcara.model.database.Field;
import org.candango.carcara.model.database.Table;
import org.candango.carcara.util.CodeHandler;

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
			"MysqlDaoFactory.class.php";
		
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
				daoName + ".class.php\";\n";
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
			"AbstractMysqlDao.class.php";
	
		String daoFileName = getTablePath( table ) + "/" +
			getEntitySufix( configuration, table ) + "MysqlDao.class.php";
		
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
			entitySufix + "AbstractMysqlDao.class.php\";\n\n";
		
		out += "class " + entitySufix + "MysqlDao extends " + 
			entitySufix + "AbstractMysqlDao {\n\n";
		
		out += "}";
		
		return out;
	}
	
	private String getMysqlAbstractDaoCode( DatabaseConfiguration configuration,
			Table table ) {
		
		String entitySufix = getEntitySufix( configuration, table );
		
		String tableSufix = CodeHandler.getEntityName( table.getName() );
		
		String tableAttr = CodeHandler.getAttributeName( table.getName() );
		
		String pks = "";
		
		for( Field field : table.getFields() ) {
			if( field.isPk() ) {
				pks += ( pks.equals( "" ) ? "" : ", " ) + "$" + 
					CodeHandler.getAttributeName( field.getName() );
			}
		}
		
		String out = "<?php\n";
		
		out += "require_once \"" + "dao/" + 
			table.getName() + "/" + 
			entitySufix + "Dto.class.php\";\n\n";
		out += "require_once \"" + "dao/" + 
			table.getName() + "/" + 
			entitySufix + "Dao.class.php\";\n\n";
		
		out += "abstract class " + entitySufix + "AbstractMysqlDao {\n\n";
		
		out += "    /**\n";
		out += "     *\n";
		out += "     * @var " + CodeHandler.getEntityName( 
				configuration.getIdentifier() ) + 
				"MysqlDaoFactory\n";
		out += "     */\n";
		out += "    protected $factory;\n\n";
		
		
		out += "    public function __construct( " + CodeHandler.getEntityName( 
				configuration.getIdentifier() ) + 
				"MysqlDaoFactory $factory = null ) {\n";
		out += "        $this->factory = $factory;\n";
		out += "    }\n\n";
		
		out += "    /**\n";
		out += "     * Returns the mysql dao factory\n";
		out += "     *\n";
		out += "     * @returns " + CodeHandler.getEntityName( 
				configuration.getIdentifier() ) + 
				"MysqlDaoFactory\n";
		out += "     */\n";
		out += "    protected function getFactory() {\n";
		out += "        return $this->factory;\n";
		out += "    }\n\n";
		
		
		
		
		out += "    /**\n";
		out += "     * Returns an array of " + entitySufix + 
			"Dto's by a given criteria\n";
		out += "     *\n";
		out += "     * @param array $criteria\n";
		out += "     * @returns array an array of " + entitySufix + "Dto's\n";
		out += "     */\n";
		out += "    public function get" + tableSufix + 
			"s( $criteria = null, $fillMethod = 'fill" + tableSufix + 
			"' ) { \n";
		
		out += "        if( is_null( $criteria ) ) {\n";
		out += "            $criteria[ 'fields' ] = \"*\";\n";
		out += "            $criteria[ 'from' ] = " + 
			"$this->getFactory()->getDbName() . \"." + 
			table.getName() + "\"; \n";
		out += "            $criteria[ 'where' ] = null;\n";
		out += "            $criteria[ 'order_by' ] = null;\n";
		out += "            $criteria[ 'limit' ] = null;\n";
		out += "        }\n";
		out += "        else {\n";
		out += "            if( !isset( $criteria[ 'fields' ] ) ) {\n";
		out += "                $criteria[ 'fields' ] = \"*\";\n";
		out += "            }\n";
		out += "            if( !isset( $criteria[ 'from' ] ) ) {\n";
		out += "                $criteria[ 'from' ] = " + 
			"$this->getFactory()->getDbName() . \"." + 
			table.getName() + "\"; \n            }\n";
		out += "            if( !isset( $criteria[ 'where' ] ) ) {\n";
		out += "                $criteria[ 'where' ] = null;\n";
		out += "            }\n";
		out += "            if( !isset( $criteria[ 'order_by' ] ) ) {\n";
		out += "                $criteria[ 'order_by' ] = null;\n";
		out += "            }\n";
		
		out += "            if( !isset( $criteria[ 'limit' ] ) ) {\n";
		out += "                $criteria[ 'limit' ] = null;\n";
		out += "            }\n";
		
		out += "        }\n\n";
		
		out += "        $sql = \"SELECT \" . $criteria[ 'fields' ] .\n";
		out += "            \" FROM \" . $criteria[ 'from' ];\n\n";
		
		out += "        if( !is_null( $criteria[ 'where' ] ) ) {\n";
		out += "            $sql .= \" WHERE \" . $criteria[ 'where' ];\n";
		out += "        }\n\n";
		out += "        if( !is_null( $criteria[ 'order_by' ] ) ) {\n";
        out += "            $sql .= \" ORDER BY \" . $criteria[ 'order_by' ];\n";
        out += "        }\n\n";
		
        out += "        if( !is_null( $criteria[ 'limit' ] ) ) {\n";
        out += "            $sql .= \" LIMIT \" . $criteria[ 'limit' ];\n";
        out += "        }\n\n";
        
		out += "        $sth = $this->getFactory()->getConnection(" + 
			")->prepare( $sql );\n\n";
		
		out += "        $sth->execute( isset( $criteria[ 'bind' ] ) " + 
			"? $criteria[ 'bind' ] :\n            null );\n\n";
		
		out += "        $" + tableAttr + "s = array();\n\n";
	    
		out += "        while( $row = $sth->fetch( PDO::FETCH_ASSOC ) ) {\n";
		out += "            $" + tableAttr + 
			"s[] = $this->$fillMethod( $row );\n";
		out += "        }\n\n";
		
		out += "        return $" + tableAttr + "s;\n";
		
		out += "    }\n\n";
		
		
		
		out += "    /**\n";
		out += "     * Returns the count of " + entitySufix + 
			"Dto's affected by the a given criteria\n";
		out += "     *\n";
		out += "     * @param array $criteria\n";
		out += "     * @returns integer\n";
		out += "     */\n";
		out += "    public function get" + tableSufix + 
			"sCount( $criteria = null ) { \n";
		
		out += "        if( is_null( $criteria ) ) {\n";
		out += "            $criteria[ 'from' ] = " + 
			"$this->getFactory()->getDbName() . \"." + 
			table.getName() + "\"; \n";
		out += "            $criteria[ 'where' ] = null;\n";
		out += "            $criteria[ 'order_by' ] = null;\n";
		out += "            $criteria[ 'limit' ] = null;\n";
		out += "        }\n";
		out += "        else {\n";
		out += "            if( !isset( $criteria[ 'from' ] ) ) {\n";
		out += "                $criteria[ 'from' ] = " + 
			"$this->getFactory()->getDbName() . \"." + 
			table.getName() + "\"; \n            }\n";
		out += "            if( !isset( $criteria[ 'where' ] ) ) {\n";
		out += "                $criteria[ 'where' ] = null;\n";
		out += "            }\n";
		out += "            if( !isset( $criteria[ 'order_by' ] ) ) {\n";
		out += "                $criteria[ 'order_by' ] = null;\n";
		out += "            }\n";
		
		out += "            if( !isset( $criteria[ 'limit' ] ) ) {\n";
		out += "                $criteria[ 'limit' ] = null;\n";
		out += "            }\n";
		
		out += "        }\n\n";
		
		out += "        $sql = \"SELECT count(*) AS count \" .\n";
		out += "            \" FROM \" . $criteria[ 'from' ];\n\n";
		
		out += "        if( !is_null( $criteria[ 'where' ] ) ) {\n";
		out += "            $sql .= \" WHERE \" . $criteria[ 'where' ];\n";
		out += "        }\n\n";
		out += "        if( !is_null( $criteria[ 'order_by' ] ) ) {\n";
        out += "            $sql .= \" ORDER BY \" . $criteria[ 'order_by' ];\n";
        out += "        }\n\n";
		
        out += "        if( !is_null( $criteria[ 'limit' ] ) ) {\n";
        out += "            $sql .= \" LIMIT \" . $criteria[ 'limit' ];\n";
        out += "        }\n\n";
        
		out += "        $sth = $this->getFactory()->getConnection(" + 
			")->prepare( $sql );\n\n";
		
		out += "        $sth->execute( isset( $criteria[ 'bind' ] ) " + 
			"? $criteria[ 'bind' ] :\n            null );\n\n";
		
		out += "        $row = $sth->fetch( PDO::FETCH_ASSOC );\n\n";
		out += "        if( count( $row ) ) {\n";
        out += "            return $row[ 'count' ];\n";
        out += "        }\n\n";
		
		out += "        return 0;\n";
		
		out += "    }\n\n";
		
		
		
		out += "    public function get" + tableSufix + "ByPk( " + pks + 
			" ){\n";
		out += "        $criteria = null;\n\n";
		
		out += "        $criteria[ 'where' ] = ";
		String wheres = "";
		for( Field field : table.getFields() ) {
			if( field.isPk() ) {
				if( wheres.equals( "" ) ) {
					wheres += "\" " + field.getName() + " = :" + 
						field.getName() + "\"";
				}
				else {
					wheres += ".\n                                " + 
						"\"AND " + field.getName() + " = :" + 
						field.getName() + "\"";
				}
			}
			
		}
		
		out += wheres + ";\n\n";
		
		out += "        $values = array(\n";
		
		String binds = "";
		
		for( Field field : table.getFields() ) {
			if( field.isPk() ) {
				if( binds.equals( "" ) ) {
					binds += "            \":" + field.getName() + "\" => $" + 
						CodeHandler.getAttributeName( field.getName() );
				}
				else {
					binds += ",\n            \":" + field.getName() + 
						"\" => $" + 
						CodeHandler.getAttributeName( field.getName() );
				}
			}
			
		}
		out += binds + "\n        );\n\n";

		out += "        $criteria[ 'bind' ] = $values;\n\n";

		out += "        $" + tableAttr + "s = $this->get" + 
			tableSufix + "s( $criteria );\n\n";
		
		out += "        return $" + tableAttr + "s[ 0 ];\n";
		
		out += "    }\n\n";
		
		out += "    /**\n";
		out += "     * Return one " + entitySufix +  
			"Dto with his properties filled with a properly row data.\n";
		out += "     *\n";
		out += "     * @param array $row\n";
		out += "     * @return " + entitySufix + "Dto\n";
		out += "     */\n";
		out += "    public function fill" + tableSufix + "( $row, $fillClass = '" + entitySufix + 
			"Dto' ){\n";
		
		out += "        $" + tableAttr +  " = new $fillClass();\n\n";
		
		for( Field field : table.getFields() ) {
			out += "        $" + tableAttr +  "->set" + 
				CodeHandler.getEntityName( field.getName() ) + 
				"( $row[ '" + field.getName() +  "' ] );\n";
		}
		out += "\n";
		
		out += "        return $" + tableAttr +  ";\n";
		
		out += "    }\n\n";
		
		String dtoVar = "$" + CodeHandler.getAttributeName( table.getName() );
		
		out += "    public function save" + tableSufix + "( " + 
			entitySufix + "Dto " + dtoVar + ",\n         $operation ){\n";
		
		out += "        $sql = \"\";\n";
		out += "        $sth = null;\n";
		out += "        $values = array(\n";
		binds = "";
		
		for( Field field : table.getFields() ) {
			if( binds.equals( "" ) ) {
				binds += "            \":" + field.getName() + "\" => " + 
					dtoVar + "->get" + 
					CodeHandler.getEntityName( field.getName() ) + "()";
			}
			else {
				binds += ",\n            \":" + field.getName() + "\" => " + 
				dtoVar + "->get" + 
				CodeHandler.getEntityName( field.getName() ) + "()";;
			}
		}
		out += binds + "\n        );\n\n";
		
		out += "        if( $operation == " + 
			CodeHandler.getEntityName( configuration.getIdentifier() ) + 
			"AbstractDaoFactory::INSERT_TRANSACTION ) {\n";
		
		String selects = "";
		String values = "";
		String unsets = "";
		
		int numNomPks = 0;
		for( Field field : table.getFields() ) {
			
			if( !field.isPk() ) {
				numNomPks++;
			}
			
			if( !field.isSerial()  ) {
				if( selects.equals( "" ) ) {
					selects += "                       \"" + field.getName();
					values += "                       \":" + field.getName();
				}
				else {
					selects += ", \" .\n                       \"" + 
						field.getName();
					values += ", \" .\n                       \":" + 
						field.getName();
				}
			}
			else {
				unsets = "            unset( $values[ '" + ":" + 
					field.getName() + "' ] );\n";
			}
		}
		out += unsets;
		out += "\n            $sql = \"INSERT INTO " +  
			"\" . $this->getFactory()->getDbName() . \"" + "." + 
			table.getName() +  "  ( \" .\n";
		
		out += selects + " )\" .\n";
		
		out += "                   \"VALUES ( \" .\n";
		out += values + " )\";\n";
		out += "        }\n";
		if( numNomPks > 0 ) {
			
			out += "        else{\n";
			
			out += "            $sql = \"UPDATE " +  
				"\" . $this->getFactory()->getDbName() . \"" 
				+ "." + table.getName() +  
				" SET \" .\n";
			
			String sets = "";
			wheres = "";
			for( Field field : table.getFields() ) {
				if( field.isPk() ) {
					if( wheres.equals( "" ) ) {
						wheres += "                       \"" + 
							field.getName() + " = :" + field.getName();
					}
					else {
						wheres += " AND \" .\n                       \"" + 
							field.getName() + " = :" + field.getName();
					}
				}
				else{
					if( sets.equals( "" ) ) {
						sets += "                       \"" + field.getName() + 
							" = :" + field.getName();
					}
					else {
						sets += ", \" .\n                       \"" + 
							field.getName() + " = :" + field.getName();
					}
				}
			}
			out += sets + " \" .\n                   \"WHERE \" .\n";
			out += wheres + "\";\n";
			out += "        }\n";
		}
		out += "\n        $sth = $this->getFactory()->getConnection()->" + 
			"prepare( $sql );\n\n";
		out += "        $sth->execute( $values );\n";
		
		out += "    }\n\n";
		
		out += "    public function delete" + tableSufix + "( " + pks +  
			" ) { \n";
		
		
		out += "        $sql = \"DELETE FROM \" .\n                   " +  
			"$this->getFactory()->getDbName() . \"" + 
			"." + table.getName() +  
			" \" .\n               \"WHERE \" .\n";
		
		wheres = "";
		values = "";
		for( Field field : table.getFields() ) {
			if( field.isPk() ) {
				if( wheres.equals( "" ) ) {
					wheres += "                   \"" + field.getName() + 
						" = :" + field.getName();
				}
				else {
					wheres += " AND \" .\n                   \"" + 
						field.getName() + " = :" + field.getName();
				}
				
				if( values.equals( "" ) ) {
					values += "            \":" + field.getName() + "\" => $" + 
						CodeHandler.getAttributeName( field.getName() );
				}
				else {
					values += ",\n            \":" + field.getName() + 
						"\" => $" + 
						CodeHandler.getAttributeName( field.getName() );
				}
			}
		}
		
		out += wheres + "\";\n\n";
		
		out += "        $values = array(\n";
		
		out += values + "\n        );\n\n";
		
		out += "        $sth = $this->factory->getConnection()->prepare(" + 
			" $sql );\n\n";
		
		out += "        $sth->execute( $values );\n";

		out += "    }\n\n";
		
		out += "}";
		
		return out;
	}
	
}