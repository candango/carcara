package org.candango.carcara.engine;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.candango.carcara.model.database.Field;
import org.candango.carcara.model.database.Table;
import org.candango.carcara.util.CodeHandler;

/**
 * 
 * @author Flavio Garcia
 */
public abstract class AbstractDaoBuilder implements DaoBuilder {
	
	private String path;
	
	public String getPath() {
		return path;
	}
	
	public void setPath( String path ) {
		this.path = path;
	}
	
	public void build( DatabaseConfiguration configuration, 
			DatabaseLoader loader ) {
		
		preparePath();
		
		buildFactories( configuration, loader );
		
		for( Table table : loader.getTables() ) {
			
			prepareTablePath( table );
			
			buildDto( configuration, table );
			
			buildDao( configuration, table );
			
		}
		
		
	}
	
	protected String getTablePath( Table table ) {
		return getDaoPath() + "/" + table.getName();
	}
	
	protected String getDaoPath() {
		return getPath() + "lib/dao";
	}
	
	private void buildFactories( DatabaseConfiguration configuration, 
			DatabaseLoader loader ) {
		
		String abstractDaoFileName = getDaoPath() + "/" +
			CodeHandler.upperCaseFirst( configuration.getIdentifier() ) + 
			"AbstractDaoFactory.class.php";
		
		File abstractDaoFile = new File( abstractDaoFileName );
		try {
			if( !abstractDaoFile.exists() ) {
				abstractDaoFile.createNewFile();
			}
			
			BufferedWriter out = 
				new BufferedWriter( new FileWriter( abstractDaoFile ) );
			
			out.write( getAbstractDaoFactoryCode( configuration, loader ) );
			
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		buildConcreteDaoFactory( configuration, loader );
		
	}
	
	protected abstract void buildConcreteDaoFactory( 
			DatabaseConfiguration configuration, DatabaseLoader loader );
	
	private void prepareTablePath( Table table ) {
		
		File dtoDir = new File( getTablePath( table ) );
		
		if( !dtoDir.exists() ) {
			dtoDir.mkdirs();
		}
		
	}
	
	private void preparePath() {
		
		File dir = new File( getPath() );
		
		if( dir.exists() ) {
			File libDir = new File( getDaoPath() );
			if( !libDir.exists() ) {
				libDir.mkdirs();
			}
		}
	}
	
	protected void buildDao( DatabaseConfiguration configuration, 
			Table table ) {
		
		String daoFileName = getTablePath( table ) + "/" +
			getEntitySufix( configuration, table ) + "Dao.class.php";
		
		File daoFile = new File( daoFileName );
		
		try {
			if( !daoFile.exists() ) {
				daoFile.createNewFile();
			}
			
			BufferedWriter out = 
				new BufferedWriter( new FileWriter( daoFileName ) );
			
			out.write( getDaoCode( configuration, table ) );
			
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		buildConcreteDao( configuration, table );
	}
	
	protected abstract void buildConcreteDao( 
			DatabaseConfiguration configuration, Table table );
	
	protected void buildDto( DatabaseConfiguration configuration, Table table ) {
		
		String dtoFileName = getTablePath( table ) + "/" +
			getEntitySufix( configuration, table ) + "Dto.class.php";
		
		String abstactDtoFileName = getTablePath( table ) + "/" +
			getEntitySufix( configuration, table ) + "AbstractDto.class.php";
		
		File dtoFile = new File( dtoFileName );
		File abstractDtoFile = new File( abstactDtoFileName );
		try {
			// creating dto only it doesn't exists
			if( !dtoFile.exists() ) {
				dtoFile.createNewFile();
				
				BufferedWriter out = 
					new BufferedWriter( new FileWriter( dtoFile ) );
				
				out.write( getDtoCode( configuration, table ) );
				
				out.close();
			}
			
			if( !abstractDtoFile.exists() ) {
				abstractDtoFile.createNewFile();
			}
			
			BufferedWriter out = 
				new BufferedWriter( new FileWriter( abstactDtoFileName ) );
			
			out.write( getAbstractDtoCode( configuration, table ) );
			
			out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected String getAbstractDaoFactoryCode( 
			DatabaseConfiguration configuration, 
			DatabaseLoader loader ) {
		
		String abstractDaoClassName = 
			CodeHandler.upperCaseFirst( configuration.getIdentifier() ) + 
			"AbstractDaoFactory";
		
		String out = "<?php\n";
		out += "abstract class " + abstractDaoClassName +  "{\n\n";
		out += "    const ORACLE_DAO = 1;\n\n";
		out += "    const PGSQL_DAO = 2;\n\n";
		out += "    const MYSQL_DAO = 3;\n\n";
		out += "    const INSERT_TRANSACTION = 1;\n\n";
		out += "    const UPDATE_TRANSACTION = 2;\n\n";
		out += "    private static $instances = array();\n\n";
		out += "    /**\n";
		out += "     * Return one DAO factory instance\n";
		out += "     *\n";
		out += "     * @param int $whichFactory\n";
		out += "     *\n";
		out += "     * @return " + abstractDaoClassName +  "\n";
		out += "     */\n";
		out += "    public static function getInstance( $whichFactory ) {\n\n";
		out += "        $factories = array(\n";
		out += "            self::ORACLE_DAO => \"" + 
			CodeHandler.getEntityName( configuration.getIdentifier() )  +  
			"OracleDaoFactory\",\n";
		out += "            self::PGSQL_DAO => \"" + 
			CodeHandler.getEntityName( configuration.getIdentifier() )  +  
			"PgsqlDaoFactory\",\n";
		out += "            self::MYSQL_DAO => \"" + 
			CodeHandler.getEntityName( configuration.getIdentifier() )  +  
			"MysqlDaoFactory\"\n";
		out += "        );\n\n";
		out += "        if( isset( $factories[ $whichFactory ] ) ) {\n";
		out += "            if( !isset( self::$instances[ $whichFactory ] ) ) {\n";
		out += "                require_once \"dao/\" . " + 
			"$factories[ $whichFactory ] . \".class.php\";\n";
		out += "                self::$instances[ $whichFactory ] = \n" + 
			"                    new $factories[ $whichFactory ]();\n";
		out += "    	    }\n";
		out += "            return self::$instances[ $whichFactory ];\n";
		out += "        }\n";
		out += "        return null;\n";
		out += "    }\n\n";
		
		for( Table table : loader.getTables() ){
			
			String daoName = getEntitySufix( configuration, table ) + "Dao" ;
			
			String methodName = "get" + CodeHandler.getEntityName( 
					table.getName() ) + "Dao" ;
			
			out += "    /**\n";
			out += "     * Return a new " + daoName + "\n";
			out += "     *\n";
			out += "     * @return " + daoName + "\n";
			out += "     **/\n";
			out += "    public abstract function " + methodName + "();\n\n";
		}
		
		out += "}";
		return out;
	}
	
	protected String getDaoCode( DatabaseConfiguration configuration, 
			Table table ) {
		
		String entitySufix = getEntitySufix( configuration, table );
		
		String tableSufix = CodeHandler.getEntityName( table.getName() );
		
		String pks = "";
		
		for( Field field : table.getFields() ) {
			if( field.isPk() ) {
				pks += ( pks.equals( "" ) ? "" : ", " ) + "$" + 
					CodeHandler.getAttributeName( field.getName() );
			}
		}
		
		String out = "<?php\n";
		out += "interface " + entitySufix + "Dao {\n\n";
		out += "    public function get" + tableSufix + 
			"s( $criteria = null, $fillMethod = 'fill" + tableSufix + 
			"' ); \n\n";
		
		out += "    public function get" + tableSufix + 
		"sCount( $criteria = null ); \n\n";
		
		out += "    public function get" + tableSufix + "ByPk( " + pks +  
			" ); \n\n";
		
		out += "    public function save" + tableSufix + "( " + 
			entitySufix + "Dto $" + 
			CodeHandler.getAttributeName( table.getName() ) +  
			", $transaction ); \n\n";
		
		out += "    public function delete" + tableSufix + "( " + pks +  
			" ); \n\n";
		
		out += "}";
		
		return out;
	}
	
	protected String getAbstractDtoCode( DatabaseConfiguration configuration, 
			Table table ) {
		String out = "<?php\n";
		out += "abstract class " + 
			getEntitySufix( configuration, table ) + 
			"AbstractDto {\n\n";
		for( Field field : table.getFields() ) {
			out += "    private $" + CodeHandler.getAttributeName( 
					field.getName() ) + ";\n\n";
		}
		
		for( Field field : table.getFields() ) {
			out += "    public function " + 
				CodeHandler.getGetterName( field.getName() ) + "() {\n";
			out += "        return $this->" + 
				CodeHandler.getAttributeName( field.getName() )  + ";\n";
			out += "    }\n\n";
			
			out += "    public function " + 
				CodeHandler.getSetterName( field.getName() ) + "( $" + 
					
				CodeHandler.getAttributeName( field.getName() ) + " ) {\n";
			out += "        $this->" + 
				CodeHandler.getAttributeName( field.getName() )  + " = $" +
				CodeHandler.getAttributeName( field.getName() )  + ";\n";
			out += "    }\n\n";
		}
		
		out += "}";
		return out;
	}
	
	protected String getDtoCode( DatabaseConfiguration configuration, 
			Table table ) {
		String className = getEntitySufix( configuration, table ) + "Dto";
		
		String parentName = getEntitySufix( configuration, table ) + 
			"AbstractDto";
		
		String attrName = CodeHandler.getAttributeName( table.getName() );
		
		String out = "<?php\n";
		out += "require_once \"dao/" + table.getName() + "/" + parentName + 
			".class.php\";\n\n";
		out += "class " + className + " extends " + parentName + " {\n\n";
		out += "}";
		
		return out;
	}
	
	protected String getEntitySufix( DatabaseConfiguration configuration, 
			Table table ) {
		return CodeHandler.getEntityName( configuration.getIdentifier() ) + 
			CodeHandler.getEntityName( table.getName() );
	}
	
}