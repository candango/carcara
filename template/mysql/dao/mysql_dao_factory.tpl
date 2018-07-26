<?php
/**
 * {entity_name entity=$conf->getIdentifier()}MysqlDaoFactory - {entity_name entity=$conf->getIdentifier()}MysqlDaoFactory.php
 * 
 * Mysql dao factory class. This class returns all dao factories found
 * by the engine.
 * 
 * Put your license here.
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

class {entity_name entity=$conf->getIdentifier()}MysqlDaoFactory extends {entity_name entity=$conf->getIdentifier()}AbstractDaoFactory
{

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

    public function __construct()
    {
        $myfuses = MyFuses::getInstance();
        $conf = require $myfuses->getApplication("{entity_name entity=$conf->getIdentifier()}")->getPath() .
            "conf/{entity_name entity=$conf->getIdentifier()}_conf.php";
        $connStr = "mysql:host=" . $conf['server'] . ";dbname=" .
            $conf[ 'database' ];
        $this->dbName = $conf['database'];
        try {
            $this->connection = new PDO($connStr, $conf['user'],
                 $conf['password']);
        } catch (Exception $e) {
            echo "Failed: " . $e->getMessage();
        }
    }

    /**
     * Returns the configuration database name
     *
     * @returns string
     */
    public function getDbName()
    {
        return $this->dbName;
    }

    /**
     * Returns the PDO connection object
     *
     * @returns PDO
     */
    public function getConnection()
    {
        return $this->connection;
    }
{foreach $tables as $table}

    /**
     * Return a new {entity_name entity=$conf->getIdentifier()} {table_entity_name table=$table} MysqlDao
     *
     * @return {entity_name entity=$conf->getIdentifier()}{table_entity_name table=$table}MysqlDao
     **/
    public function get{table_entity_name table=$table}Dao()
    {
        require_once "dao/{table_entity_name table=$table}/{entity_name entity=$conf->getIdentifier()}{table_entity_name table=$table}MysqlDao.php";
        return new {entity_name entity=$conf->getIdentifier()}{table_entity_name table=$table}MysqlDao($this);
    }
{/foreach}
}
