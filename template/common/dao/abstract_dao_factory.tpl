<?php
use Candango\Carcara\Model\Conf;

/**
 * {$identifierName}AbstractDaoFactory - {$identifierName}AbstractDaoFactory.php
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
abstract class {$identifierName}AbstractDaoFactory
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
    const PGSQL = "pgsql";

    /**
     * Insert Transaction constant
     *
     * @var integer
     */
    const INSERT = "insert";

    /**
     * Update Transaction constant
     *
     * @var integer
     */
    const UPDATE = "update";

    /**
     * Factory configuration
     *
     * @var Conf
     */
    private $conf;

    /**
     * Collection of instances that this factory can hold
     *
     * @var array An array of {{$identifierName}}AbstractDaoFactory
     */
    private static $instances = array();

    /**
     * Return one DAO factory instance
     *
     * @param int $whichFactory
     * @param Conf $conf
     * @return {$identifierName}AbstractDaoFactory
     */
    public static function getInstance($whichFactory, Conf $conf)
    {
        $factories = array(
            self::MYSQL_DAO => "{$identifierName}MysqlDaoFactory",
            self::PGSQL_DAO => "{$identifierName}PgsqlDaoFactory"
        );

        if (isset($factories[ $whichFactory])) {
            if (!isset(self::$instances[$whichFactory])) {
                require_once "dao/" . $factories[$whichFactory] . ".php";
                self::$instances[ $whichFactory ] =
                new $factories[ $whichFactory ]();
                self::$instances[$whichFactory]->setConf($conf);
            }
            return self::$instances[$whichFactory];
        }
        return null;
    }

    /**
     * Return the factory configuration
     *
     * @returns Conf
     */
    public function getConf()
    {
        return $this->conf;
    }

    /**
     * Set the factory configuration
     *
     * @returns Conf
     */
    public function getConnection(Conf $conf)
    {
        return $this->conf = $conf;
    }
{foreach $tables as $table}

    /**
     * Return a new {$identifierName} {table_entity_name table=$table} Dao
     *
     * @return {$identifierName}{table_entity_name table=$table}Dao
     **/
    public abstract function get{table_entity_name table=$table}Dao();
{/foreach}
}
