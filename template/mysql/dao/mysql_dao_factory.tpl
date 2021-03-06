<?php
use Candango\Carcara\Conf;

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
class {$identifierName}MysqlDaoFactory extends {$identifierName}DaoFactory
{
{foreach $tables as $table}

    /**
     * Return a new {$identifierName} {$table->getEntityName()} MysqlDao
     *
     * @return {$identifierName}{$table->getEntityName()}MysqlDao
     **/
    public function get{$table->getEntityName()}Dao()
    {
        require_once $this->getConf()->getCurrentDaoDir() . DIRECTORY_SEPARATOR
            . "{$table->getEntityName()}/{$identifierName}{$table->getEntityName()}MysqlDao.php";
        return new {$identifierName}{$table->getEntityName()}MysqlDao($this);
    }
{/foreach}
}
