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
			"PgsqlDaoFactory.class.php";
		
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
			"AbstractPgsqlDao.class.php";
	
		String daoFileName = getTablePath( table ) + "/" +
			getEntitySufix( configuration, table ) + "PgsqlDao.class.php";
		
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
			entitySufix + "AbstractPgsqlDao.class.php\";\n\n";
		
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
		
//		String entitySufix = getEntitySufix( configuration, table );
//		
//		String tableSufix = CodeHandler.getEntityName( table.getName() );
//		
//		String tableAttr = CodeHandler.getAttributeName( table.getName() );
//		
//		String pks = "";
//		
//		for( Field field : table.getFields() ) {
//			if( field.isPk() ) {
//				pks += ( pks.equals( "" ) ? "" : ", " ) + "$" + 
//					CodeHandler.getAttributeName( field.getName() );
//			}
//		}
//		
//		String out = "<?php\n";
//		
//		out += "require_once \"" + "dao/" + table.getName() + "/" + 
//			entitySufix + "Dto.class.php\";\n\n";
//		out += "require_once \"" + "dao/" + table.getName() + "/" + 
//			entitySufix + "Dao.class.php\";\n\n";
//		
//		out += "abstract class " + entitySufix + "AbstractPgsqlDao {\n\n";
//		
//		out += "    /**\n";
//		out += "     *\n";
//		out += "     * @var " + CodeHandler.getEntityName( 
//				configuration.getIdentifier() ) + 
//				"PgsqlDaoFactory\n";
//		out += "     */\n";
//		out += "    protected $factory;\n\n";
//		
//		
//		out += "    public function __construct( " + CodeHandler.getEntityName( 
//				configuration.getIdentifier() ) + 
//				"PgsqlDaoFactory $factory = null ) {\n";
//		out += "        $this->factory = $factory;\n";
//		out += "    }\n\n";
//		
//		out += "    public function get" + tableSufix + 
//			"s( $criteria = null ) { \n";
//		
//		out += "        if( is_null( $criteria ) ) {\n";
//		out += "            $criteria[ 'fields' ] = \"*\";\n";
//		out += "            $criteria[ 'from' ] = \"" + 
//			configuration.getIdentifier() + "." + table.getName() + "\"; \n";
//		out += "            $criteria[ 'where' ] = null;\n";
//		out += "            $criteria[ 'order_by' ] = null;\n";
//		out += "        }\n";
//		out += "        else {\n";
//		out += "            if( !isset( $criteria[ 'fields' ] ) ) {\n";
//		out += "                $criteria[ 'fields' ] = \"*\";\n";
//		out += "            }\n";
//		out += "            if( !isset( $criteria[ 'from' ] ) ) {\n";
//		out += "                $criteria[ 'from' ] = \"" + 
//			configuration.getIdentifier() + "." + table.getName() + "\";\n";
//		out += "            }\n";
//		out += "            if( !isset( $criteria[ 'where' ] ) ) {\n";
//		out += "                $criteria[ 'where' ] = null;\n";
//		out += "            }\n";
//		out += "            if( !isset( $criteria[ 'order_by' ] ) ) {\n";
//                out += "                $criteria[ 'order_by' ] = null;\n";
//                out += "            }\n";
//		out += "        }\n\n";
//		
//		out += "        $sql = \"SELECT \" . $criteria[ 'fields' ] .\n";
//		out += "            \" FROM \" . $criteria[ 'from' ];\n\n";
//		
//		out += "        if( !is_null( $criteria[ 'where' ] ) ) {\n";
//		out += "            $sql .= \" WHERE \" . $criteria[ 'where' ];\n";
//		out += "        }\n\n";
//		out += "        if( !is_null( $criteria[ 'order_by' ] ) ) {\n";
//                out += "            $sql .= \" ORDER BY \" . $criteria[ 'order_by' ];\n";
//                out += "        }\n\n";
//		
//		out += "        $sth = $this->factory->getConnection(" + 
//			")->prepare( $sql );\n\n";
//		
//		out += "        $sth->execute( isset( $criteria[ 'bind' ] ) " + 
//			"? $criteria[ 'bind' ] :\n            null );\n\n";
//		
//		out += "        $" + tableAttr + "s = array();\n\n";
//	    
//		out += "        while( $row = $sth->fetch( PDO::FETCH_ASSOC ) ) {\n";
//		out += "            $" + tableAttr + "s[] = $this->fill" + 
//			tableSufix + "( $row );\n";
//		out += "        }\n\n";
//		
//		out += "        return $" + tableAttr + "s;\n";
//		
//		out += "    }\n\n";
//		
//		out += "    public function get" + tableSufix + "ByPk( " + pks + 
//			" ){\n";
//		out += "        $criteria = null;\n\n";
//		
//		out += "        $criteria[ 'where' ] = ";
//		String wheres = "";
//		for( Field field : table.getFields() ) {
//			if( field.isPk() ) {
//				if( wheres.equals( "" ) ) {
//					wheres += "\" " + field.getName() + " = :" + 
//						field.getName() + "\"";
//				}
//				else {
//					wheres += ".\n                                " + 
//						"\"AND " + field.getName() + " = :" + 
//						field.getName() + "\"";
//				}
//			}
//			
//		}
//		
//		out += wheres + ";\n\n";
//		
//		out += "        $values = array(\n";
//		
//		String binds = "";
//		
//		for( Field field : table.getFields() ) {
//			if( field.isPk() ) {
//				if( binds.equals( "" ) ) {
//					binds += "            \":" + field.getName() + "\" => $" + 
//						CodeHandler.getAttributeName( field.getName() );
//				}
//				else {
//					binds += ",\n            \":" + field.getName() + 
//						"\" => $" + 
//						CodeHandler.getAttributeName( field.getName() );
//				}
//			}
//			
//		}
//		out += binds + "\n        );\n\n";
//
//		out += "        $criteria[ 'bind' ] = $values;\n\n";
//
//		out += "        $" + tableAttr + "s = $this->get" + 
//			tableSufix + "s( $criteria );\n\n";
//		
//		out += "        return $" + tableAttr + "s[ 0 ];\n";
//		
//		out += "    }\n\n";
//		
//		out += "    /**\n";
//		out += "     * Return one " + entitySufix +  
//			" Dto with properties filled with the row values.\n";
//		out += "     *\n";
//		out += "     * @param array $row\n";
//		out += "     * @return " + entitySufix + "IadminGroupDto\n";
//		out += "     */\n";
//		out += "    public function fill" + tableSufix + "( $row ){\n";
//		
//		out += "        $" + tableAttr +  " = new " + entitySufix + 
//			"Dto();\n\n";
//		
//		for( Field field : table.getFields() ) {
//			out += "        $" + tableAttr +  "->set" + 
//				CodeHandler.getEntityName( field.getName() ) + 
//				"( $row[ '" + field.getName() +  "' ] );\n";
//		}
//		out += "\n";
//		
//		out += "        return $" + tableAttr +  ";\n";
//		
//		out += "    }\n\n";
//		
//		String dtoVar = "$" + CodeHandler.getAttributeName( table.getName() );
//		
//		out += "    public function save" + tableSufix + "( " + 
//			entitySufix + "Dto " + dtoVar + ",\n         $transaction ){\n";
//		
//		out += "        $sql = \"\";\n";
//		out += "        $sth = null;\n";
//		out += "        $values = array(\n";
//		binds = "";
//		
//		for( Field field : table.getFields() ) {
//			if( binds.equals( "" ) ) {
//				binds += "            \":" + field.getName() + "\" => " + 
//					dtoVar + "->get" + 
//					CodeHandler.getEntityName( field.getName() ) + "()";
//			}
//			else {
//				binds += ",\n            \":" + field.getName() + "\" => " + 
//				dtoVar + "->get" + 
//				CodeHandler.getEntityName( field.getName() ) + "()";;
//			}
//		}
//		out += binds + "\n        );\n\n";
//		
//		out += "        if( $transaction == " + 
//			CodeHandler.getEntityName( configuration.getIdentifier() ) + 
//			"AbstractDaoFactory::INSERT_TRANSACTION ) {\n";
//		
//		String selects = "";
//		String values = "";
//		String unsets = "";
//		
//		int numNomPks = 0;
//		for( Field field : table.getFields() ) {
//			
//			if( !field.isPk() ) {
//				numNomPks++;
//			}
//			
//			if( !field.isSerial()  ) {
//				if( selects.equals( "" ) ) {
//					selects += "                       \"" + field.getName();
//					values += "                       \":" + field.getName();
//				}
//				else {
//					selects += ", \" .\n                       \"" + 
//						field.getName();
//					values += ", \" .\n                       \":" + 
//						field.getName();
//				}
//			}
//			else {
//				unsets = "            unset( $values[ '" + ":" + 
//					field.getName() + "' ] );\n";
//			}
//		}
//		out += unsets;
//		out += "\n            $sql = \"INSERT INTO  " +  
//			configuration.getIdentifier() + "." + 
//			table.getName() +  "  ( \" .\n";
//		
//		out += selects + " )\" .\n";
//		
//		out += "                   \"VALUES ( \" .\n";
//		out += values + " )\";\n";
//		out += "        }\n";
//		if( numNomPks > 0 ) {
//			
//			out += "        else{\n";
//			
//			out += "            $sql = \"UPDATE " +  
//				configuration.getIdentifier() + "." + table.getName() +  
//				" SET \" .\n";
//			
//			String sets = "";
//			wheres = "";
//			for( Field field : table.getFields() ) {
//				if( field.isPk() ) {
//					if( wheres.equals( "" ) ) {
//						wheres += "                       \"" + 
//							field.getName() + " = :" + field.getName();
//					}
//					else {
//						wheres += " AND \" .\n                       \"" + 
//							field.getName() + " = :" + field.getName();
//					}
//				}
//				else{
//					if( sets.equals( "" ) ) {
//						sets += "                       \"" + field.getName() + 
//							" = :" + field.getName();
//					}
//					else {
//						sets += ", \" .\n                       \"" + 
//							field.getName() + " = :" + field.getName();
//					}
//				}
//			}
//			out += sets + " \" .\n                   \"WHERE \" .\n";
//			out += wheres + "\";\n";
//			out += "        }\n";
//		}
//		out += "\n        $sth = $this->factory->getConnection()->prepare(" + 
//			" $sql );\n\n";
//		out += "        $sth->execute( $values );\n";
//		
//		out += "    }\n\n";
//		
//		out += "    public function delete" + tableSufix + "( " + pks +  
//			" ) { \n";
//		
//		
//		out += "        $sql = \"DELETE FROM \" .\n                   \"" +  
//			configuration.getIdentifier() + "." + table.getName() +  
//			" \" .\n               \"WHERE \" .\n";
//		
//		wheres = "";
//		values = "";
//		for( Field field : table.getFields() ) {
//			if( field.isPk() ) {
//				if( wheres.equals( "" ) ) {
//					wheres += "                   \"" + field.getName() + 
//						" = :" + field.getName();
//				}
//				else {
//					wheres += " AND \" .\n                   \"" + 
//						field.getName() + " = :" + field.getName();
//				}
//				
//				if( values.equals( "" ) ) {
//					values += "            \":" + field.getName() + "\" => $" + 
//						CodeHandler.getAttributeName( field.getName() );
//				}
//				else {
//					values += ",\n            \":" + field.getName() + 
//						"\" => $" + 
//						CodeHandler.getAttributeName( field.getName() );
//				}
//			}
//		}
//		
//		out += wheres + "\";\n\n";
//		
//		out += "        $values = array(\n";
//		
//		out += values + "\n        );\n\n";
//		
//		out += "        $sth = $this->factory->getConnection()->prepare(" + 
//			" $sql );\n\n";
//		
//		out += "        $sth->execute( $values );\n";
//
//		out += "    }\n\n";
//		
//		out += "}";
//		
//		return out;
	}
	
}