<?php
/**
 * ${identifier-name-upper}MysqlDaoFactory - ${identifier-name-upper}MysqlDaoFactory.php
 * 
 * Abstract dao factory class. This class returns all dao factories found
 * by the engine.
 * 
 * PHP version 5
 * 
 * Put your license here.
 * 
 * Contributor(s): 
 *
 * @category dao
 * @package lib.dao
 * @author Carcara Modeller Tool Engine
 * @copyright Put your copyright here.
 * @license Put your copyright here.
 * @version    
 */

/**
 * ${identifier-name-upper}MysqlDaoFactory - ${identifier-name-upper}MysqlDaoFactory.php
 * 
 * Abstract dao factory class. This class returns all dao factories found
 * by the engine.
 * 
 * PHP version 5 
 * 
 * @category dao
 * @package lib.dao
 * @author Carcara Modeller Tool Engine
 * @copyright Put your copyright here.
 * @license Put your copyright here.
 * @version
 */
class ${identifier-name-upper}MysqlDaoFactory extends ${identifier-name-upper}AbstractDaoFactory{

    /**
     * Configuration database name
     *
     * @var string
     */
    private $dbName;

    /**
     * Dao Factory Connection
     *
     * @var PDO
     */
    private $connection;

    public function __construct() {
        $myfuses = MyFuses::getInstance();
        $conf = require $myfuses->getApplication( "${identifier-name}" )->getPath() .
            "conf/${identifier-name}_conf.php";
        $connStr = "mysql:host=" . $conf[ 'server' ] . ";dbname=" .
            $conf[ 'database' ];
        $this->dbName = $conf[ 'database' ];
        try {
            $this->connection = new PDO( $connStr, $conf[ 'user' ],
                 $conf[ 'password' ] );
        }
        catch ( Exception $e ) {
            echo "Failed: " . $e->getMessage();
        }
    }

    /**
     * Returns the configuration database name
     *
     * @returns string
     */
    public function getDbName() {
        return $this->dbName;
    }

    /**
     * Returns the PDO connection object
     *
     * @returns PDO
     */
    public function getConnection() {
        return $this->connection;
    }
#foreach ($table in $tables)

    /**
     * Return a new ${identifier-name-upper}${table.getEntityName()}MysqlDao
     *
     * @return ${identifier-name-upper}${table.getEntityName()}MysqlDao
     **/
    public function get${table.getEntityName()}Dao() {
        require_once "dao/${table.getName()}/${identifier-name-upper}${table.getEntityName()}MysqlDao.php";
        return new ${identifier-name-upper}${table.getEntityName()}MysqlDao( $this );
    }
#end
}
