<?php
/**
 * {$identifierName}{$table->getEntityName()}AbstractMysqlDao - {$identifierName}{$table->getEntityName()}AbstractMysqlDao.php
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
 * @package lib.dao.{$table->getName()}
 * @author Carcara Modeller Tool Engine
 * @copyright Put your copyright here.
 * @license Put your copyright here.
 * @version    
 */

require_once dirname(__FILE__) . DIRECTORY_SEPARATOR .
    "{$identifierName}{$table->getEntityName()}Dto.php";
require_once dirname(__FILE__) . DIRECTORY_SEPARATOR .
    "{$identifierName}{$table->getEntityName()}Dao.php";

use Candango\Carcara\Conf;

/**
 * {$identifierName}{$table->getEntityName()}AbstractMysqlDao - {$identifierName}{$table->getEntityName()}AbstractMysqlDao.php
 * 
 * Abstract dao factory class. This class returns all dao factories found
 * by the engine.
 * 
 * PHP version 5 
 * 
 * @category dao
 * @package lib.dao.{$table->getName()}
 * @author Carcara Modeller Tool Engine
 * @copyright Put your copyright here.
 * @license Put your copyright here.
 * @version
 */
abstract class {$identifierName}{$table->getEntityName()}AbstractMysqlDao implements {$identifierName}{$table->getEntityName()}Dao
{

    /**
     *
     * @var {$identifierName}MysqlDaoFactory
     */
    protected $factory;

    /**
     *
     * @var PDOStatement
     */
    protected $latestStatement;

    public function __construct({$identifierName}MysqlDaoFactory $factory = null)
    {
        $this->factory = $factory;
    }

    /**
     * Return the factory connection
     *
     * @return PDO
     */
    protected function getConnection()
    {
        return $this->factory->getConnection();
    }

    /**
     * Return the factory configuration
     *
     * @return Conf
     */
    protected function getConf()
    {
        return $this->factory->getConf();
    }

    /**
     * Get an array of {$identifierName}{$table->getEntityName()}Dto based on a given
     * criteria.
     *
     * If you want to implement a custom logic to fill the entity to be
     * returned pass it in the fillMethod variable.
     *
     * If a custom logic to build the array pass it in the buildArrayMethod.
     *
     * Implement custom login at the {$identifierName}{$table->getEntityName()}MysqlDao instead
     * of this class.
     *
     * @param array|null $criteria
     * @param string $fillMethod
     * @param string $buildArrayMethod
     * @return array of {$identifierName}{$table->getEntityName()}Dto
     */
    public function getByCriteria(
        $criteria = null,
        $fillMethod = 'fillEntity',
        $buildArrayMethod = 'buildArray'
    ) {
        if (is_null($criteria)) {
            $criteria['fields'] = "*";
            $criteria['from'] = $this->getConf()->getDatabase() .
                ".{$table->getName()}";
            $criteria['where'] = null;
            $criteria['order_by'] = null;
            $criteria['limit'] = null;
        } else {
            if (!isset($criteria['fields'])) {
                $criteria['fields'] = "*";
            }
            if (!isset($criteria['from'])) {
                $criteria['from'] = $this->getConf()->getDatabase() .
                    ".{$table->getName()}";
            }
            if (!isset($criteria['where'])) {
                $criteria['where'] = null;
            }
            if (!isset($criteria['order_by'])) {
                $criteria['order_by'] = null;
            }
            if (!isset($criteria['limit'])) {
                $criteria['limit'] = null;
            }
        }

        $sql = "SELECT " . $criteria['fields'] . " FROM " . $criteria['from'];
        if (!is_null($criteria['where'])) {
            $sql .= " WHERE " . $criteria['where'];
        }
        if (!is_null($criteria['order_by'])) {
            $sql .= " ORDER BY " . $criteria['order_by'];
        }
        if (!is_null($criteria['limit'])) {
            $sql .= " LIMIT " . $criteria['limit'];
        }

        $this->latestStatement = $this->getConnection()->prepare($sql);
        $this->latestStatement->execute(isset($criteria['bind']) ? $criteria['bind'] : null);

        ${$table->getAttributeName()}Array = array();
        while ($row = $this->latestStatement->fetch(PDO::FETCH_ASSOC)) {
            $this->$buildArrayMethod(${$table->getAttributeName()}Array, $this->$fillMethod($row));
        }
        return ${$table->getAttributeName()}Array;
    }

    public function getCount($criteria = null)
    {
        if (is_null($criteria)) {
            $criteria[ 'from' ] = $this->getConf()->getDatabase() .
                ".{$table->getName()}";
            $criteria[ 'where' ] = null;
            $criteria[ 'order_by' ] = null;
            $criteria[ 'limit' ] = null;
        } else {
            if (!isset($criteria['from'])) {
                $criteria['from'] = $this->getConf()->getDatabase() .
                    ".{$table->getName()}";
            }
            if (!isset($criteria['where'])) {
                $criteria['where'] = null;
            }
            if (!isset($criteria['order_by'])) {
                $criteria['order_by'] = null;
            }
            if (!isset($criteria['limit'])) {
                $criteria['limit'] = null;
            }
        }

        $sql = "SELECT count(*) AS count FROM " . $criteria['from'];
        if (!is_null($criteria['where'])) {
            $sql .= " WHERE " . $criteria['where'];
        }
        if (!is_null($criteria['order_by'])) {
            $sql .= " ORDER BY " . $criteria['order_by'];
        }
        if (!is_null($criteria['limit'])) {
            $sql .= " LIMIT " . $criteria['limit'];
        }

        $this->latestStatement = $this->getConnection()->prepare($sql);
        $this->latestStatement->execute(isset($criteria['bind']) ? $criteria['bind'] : null);
        $row = $this->latestStatement->fetch(PDO::FETCH_ASSOC);
        if (count($row)) {
            return $row['count'];
        }
        return 0;
    }
    
    public function getByPk({foreach $table->getPkFields() as $field}${$field->getAttributeName()}{if !$field@last}, {/if}{/foreach})
    {
        $criteria = null;
        $criteria[ 'where' ] = {foreach $table->getPkFields() as $field}" {$field->getName()} = :{$field->getName()}{if !$field@last} AND" .
                               {else}"{/if}{/foreach};
        $values = array(
            {foreach $table->getPkFields() as $field}":{$field->getName()}" =>  ${$field->getAttributeName()}{if !$field@last},
            {/if}{/foreach}

        );
        $criteria[ 'bind' ] = $values;
        ${$table->getAttributeName()}Array = $this->getByCriteria($criteria);
        return count(${$table->getAttributeName()}Array) ? ${$table->getAttributeName()}Array[0] : null;
    }

    /**
     * Add the filled row to the array of {$table->getAttributeName()}s returned by the
     * getByCriteria method
     *
     * @param array ${$table->getAttributeName()}Array
     * @param {$identifierName}{$table->getEntityName()}Dto $item
     */
    public function buildArray(&${$table->getAttributeName()}Array, $item)
    {
        ${$table->getAttributeName()}Array[] = $item;
    }

    /**
     * Return one {$identifierName}{$table->getEntityName()}Dto with filled by row values.
     *
     * @param array $row
     * @param string $fillClass
     * @return {$identifierName}{$table->getEntityName()}Dto
     */
    public function fillEntity($row, $fillClass = "{$identifierName}{$table->getEntityName()}Dto")
    {
        ${$table->getAttributeName()} = new $fillClass();
{foreach $table->getFields() as $field}
        if (array_key_exists("{$field->getName()}", $row)) {
            ${$table->getAttributeName()}->set{$field->getEntityName()}($row['{$field->getName()}']);
        }
{/foreach}
        return ${$table->getAttributeName()};
    }

    /**
     * Update the {$table->getName()} table based on a given criteria.
     *
     * @param array|null $criteria
     * @return bool
     */
    public function update($criteria = null)
    {
        if (is_null($criteria)) {
            $criteria['fields'] = null;
            $criteria['where'] = null;
        } else {
            if (!isset($criteria['fields'])) {
                $criteria['fields'] = null;
            }
            if (!isset($criteria['where'])) {
                $criteria['where'] = null;
            }
        }

        if ($criteria['fields'] != null) {
            $sql = "UPDATE " . $this->getConf()->getDatabase() .
                ".{$table->getName()} SET " . $criteria['fields'];
            if ($criteria['where'] != null) {
                $sql .= " WHERE " . $criteria['where'];
            }
            $this->latestStatement = $this->getConnection()->prepare($sql);
            return $this->latestStatement->execute(
                isset($criteria['bind']) ? $criteria['bind'] : null);
        }
        return false;
    }

    /**
     * Save (insert or update) the {$identifierName}{$table->getEntityName()}Dto
     * into the {$table->getName()} table based on the transaction type
     * informed.
     *
     * @param {$identifierName}{$table->getEntityName()}Dto ${$table->getAttributeName()}
     * @param string $transaction
     * @return bool
     */
    public function save(
        {$identifierName}{$table->getEntityName()}Dto ${$table->getAttributeName()},
        $transaction
    ) {
        $sql = null;
        $this->latestStatement = null;
        $values = array(
{foreach $table->getFields() as $field}
            ':{$field->getName()}' => ${$table->getAttributeName()}->get{$field->getEntityName()}(){if !$field@last},
{/if}
{/foreach}
        
        );

        if ($transaction == Conf::INSERT) {
{foreach $table->getSerialFields() as $field}
            unset($values[':{$field->getName()}']);
{/foreach}
            $sql = "INSERT INTO " . $this->getConf()->getDatabase() .
                   ".{$table->getName()}( " .
{foreach $table->getNonSerialFields() as $field}
                       "{$field->getName()}{if !$field@last}, " .
{/if}
{/foreach} )" .
                   "VALUES ( " .
{foreach $table->getNonSerialFields() as $field}
                       ":{$field->getName()}{if !$field@last}, " .
{/if}
{/foreach} )";
        }{if count($table->getNonPkFields()) gt  0} elseif ($transaction == Conf::UPDATE) {
            $sql = "UPDATE " . $this->getConf()->getDatabase() .
                   ".{$table->getName()} SET " .
{foreach $table->getNonPkFields() as $field}
                       "{$field->getName()} = :{$field->getName()}{if !$field@last}, " .
{/if}
{/foreach} " .
                   "WHERE " .
{foreach $table->getPkFields() as $field}
                       "{$field->getName()} = :{$field->getName()}{if !$field@last}, " .
{/if}
{/foreach} ";
        }{/if} else {
            return false;
        }

        $this->latestStatement = $this->getConnection()->prepare($sql);
        $result = $this->latestStatement->execute($values);
{if count($table->getPkFields()) eq  1}
        if ($transaction == Conf::INSERT) {
            if ($result) {
                ${$table->getAttributeName()}->set{$table->getFirstPkField()->getEntityName()}($this->getConnection()->lastInsertId());
            }
        }
{/if}
        return $result;
    }

    public function delete({foreach $table->getPkFields() as $field}${$field->getAttributeName()}{if !$field@last}, {/if}{/foreach})
    {
        $sql = "DELETE FROM " .
                   $this->getConf()->getDatabase() . ".{$table->getName()} " .
               "WHERE " .
{foreach $table->getPkFields() as $field}
                   "{$field->getName()} = :{$field->getName()}{if !$field@last}, " .
{/if}
{/foreach} ";
        $values = array(
{foreach $table->getPkFields() as $field}
            ":{$field->getName()}" => ${$field->getAttributeName()}{if !$field@last},
{/if}
{/foreach}

        );
        $this->latestStatement = $this->getConnection()->prepare($sql);
        return $this->latestStatement->execute($values);
    }

    /**
    * Return the latest statement executed.
    *
    * @return PDOStatement
    */
    public function getLatestStatement() {
        return $this->latestStatement;
    }
}
