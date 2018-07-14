<?php
/**
 * ${identifier-name-upper}PgsqlDaoFactory - ${identifier-name-upper}PgsqlDaoFactory.php
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
 * ${identifier-name-upper}PgsqlDaoFactory - ${identifier-name-upper}PgsqlDaoFactory.php
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
class ${identifier-name-upper}PgsqlDaoFactory extends ${identifier-name-upper}AbstractDaoFactory{

    private $connection;

    public function __construct() {
        $myfuses = MyFuses::getInstance();
        $conf = require $myfuses->getApplication( "${identifier-name}" )->getPath() .
            "conf/${identifier-name}_conf.php";
        $connStr = "pgsql:host=" . $conf[ 'server' ] . ";dbname=" .
            $conf[ 'database' ];
        try {
            $this->connection = new PDO( $connStr, $conf[ 'user' ],
                 $conf[ 'password' ] );
            $this->connection->setAttribute( PDO::ATTR_ERRMODE,
                 PDO::ERRMODE_WARNING );
        }
        catch ( Exception $e ) {
            echo "Failed: " . $e->getMessage();
        }
    }

    public function getConnection() {
        return $this->connection;
    }

#foreach ($table in $tables)

    /**
     * Return a new ${identifier-name-upper}${table.getEntityName()}Dao
     *
     * @return ${identifier-name-upper}${table.getEntityName()}Dao
     **/
    public function get${table.getEntityName()}Dao() {
        require_once "dao/${table.getName()}/${identifier-name-upper}${table.getEntityName()}PgsqlDao.php";
        return new ${identifier-name-upper}${table.getEntityName()}PgsqlDao( $this );
    }
#end
}