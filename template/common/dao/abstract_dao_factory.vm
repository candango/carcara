<?php
/**
 * ${identifier-name}AbstractDaoFactory - ${identifier-name}AbstractDaoFactory.php
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
 * ${identifier-name}AbstractDaoFactory - ${identifier-name}AbstractDaoFactory.php
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
abstract class ${identifier-name}AbstractDaoFactory{

    /**
     * Oracle Dao constant
     * 
     * @var integer
     */
    const ORACLE_DAO = 1;

    /**
     * Pgsql Dao constant
     * 
     * @var integer
     */
    const PGSQL_DAO = 2;

    /**
     * Mysql Dao constant
     * 
     * @var integer
     */
    const MYSQL_DAO = 3;

    /**
     * Insert Transaction constant
     * 
     * @var integer
     */
    const INSERT_TRANSACTION = 1;

    /**
     * Update Transaction constant
     * 
     * @var integer
     */
    const UPDATE_TRANSACTION = 2;

    /**
     * Collection of instances that this factory can hold
     *
     * @var array An array of ${identifier-name}AbstractDaoFactory's
     */
    private static $instances = array();

    /**
     * Return one DAO factory instance
     *
     * @param int $whichFactory
     * @return ${identifier-name}AbstractDaoFactory
     */
    public static function getInstance( $whichFactory ) {

        $factories = array(
            self::ORACLE_DAO => "${identifier-name}OracleDaoFactory",
            self::PGSQL_DAO => "${identifier-name}PgsqlDaoFactory",
            self::MYSQL_DAO => "${identifier-name}MysqlDaoFactory"
        );

        if( isset( $factories[ $whichFactory ] ) ) {
            if( !isset( self::$instances[ $whichFactory ] ) ) {
                require_once "dao/" . $factories[ $whichFactory ] . ".php";
                self::$instances[ $whichFactory ] = 
                    new $factories[ $whichFactory ]();
            }
            return self::$instances[ $whichFactory ];
        }
        return null;
    }
#foreach ($table in $tables)

    /**
     * Return a new ${identifier-name} ${table.getEntityName()} Dao
     *
     * @return ${identifier-name}${table.getEntityName()}Dao
     **/
    public abstract function get${table.getEntityName()}Dao();
#end
}
