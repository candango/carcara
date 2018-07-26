<?php
/**
 * {entity_name entity=$conf->getIdentifier()}AbstractDaoFactory - {entity_name entity=$conf->getIdentifier()}AbstractDaoFactory.php
 * 
 * Abstract dao factory class. This class returns all dao factories found
 * by the engine.
 *
 * Contributor(s): 
 *
 * @category dao
 * @package lib.dao
 * @author Carcara DAO Generator
 * @copyright Put your copyright here.
 * @license Put your copyright here.
 * @version    
 */

abstract class {entity_name entity=$conf->getIdentifier()}AbstractDaoFactory
{
    /**
     * Mysql Dao constant
     *
     * @var string
     */
    const MYSQL = "mysql";

    /**
     * Pgsql Dao constant
     *
     * @var string
     */
    const PGSQL_DAO = "pgsql";

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
     * @var array An array of {entity_name entity=$conf->getIdentifier()}AbstractDaoFactory
     */
    private static $instances = array();

    /**
     * Return one DAO factory instance
     *
     * @param int $whichFactory
     * @return {entity_name entity=$conf->getIdentifier()}AbstractDaoFactory
     */
    public static function getInstance($whichFactory)
    {
        $factories = array(
            self::MYSQL_DAO => "{entity_name entity=$conf->getIdentifier()}MysqlDaoFactory",
            self::PGSQL_DAO => "{entity_name entity=$conf->getIdentifier()}PgsqlDaoFactory"
        );

        if (isset($factories[ $whichFactory])) {
            if (!isset(self::$instances[$whichFactory])) {
                require_once "dao/" . $factories[ $whichFactory ] . ".php";
                self::$instances[ $whichFactory ] =
                new $factories[ $whichFactory ]();
            }
            return self::$instances[ $whichFactory ];
        }
        return null;
    }

{foreach $tables as $table}
    /**
    * Return a new {entity_name entity=$conf->getIdentifier()} {table_entity_name table=$table} Dao
    *
    * @return {entity_name entity=$conf->getIdentifier()}{table_entity_name table=$table}Dao
    **/
    public abstract function get{table_entity_name table=$table}Dao();

{/foreach}
}
