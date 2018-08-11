<?php
use Candango\Carcara\Model\Conf;

/**
 * {$identifierName}MysqlDaoFactory - {$identifierName}MysqlDaoFactory.php
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
class {$identifierName}MysqlDaoFactory extends {$identifierName}AbstractDaoFactory
{
    /**
     * Dao Factory Connection
     *
     * @var PDO
     */
    private $connection;

    /**
     * {$identifierName}MysqlDaoFactory constructor.
     */
    public function __construct()
    {
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
     * Return a new {$identifierName} {table_entity_name table=$table} MysqlDao
     *
     * @return {$identifierName}{table_entity_name table=$table}MysqlDao
     **/
    public function get{table_entity_name table=$table}Dao()
    {
        require_once "dao/{table_entity_name table=$table}/{$identifierName}{table_entity_name table=$table}MysqlDao.php";
        return new {$identifierName}{table_entity_name table=$table}MysqlDao($this);
    }
{/foreach}
}
