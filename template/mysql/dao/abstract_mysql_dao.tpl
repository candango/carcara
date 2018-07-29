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

require_once "dao/{$table->getName()}/{$identifierName}{$table->getEntityName()}Dto.php";

require_once "dao/{$table->getName()}/{$identifierName}{$table->getEntityName()}Dao.php";

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

    public function __construct({$identifierName}MysqlDaoFactory $factory = null)
    {
        $this->factory = $factory;
    }

    public function get{$table->getEntityName()}s(
        $criteria = null,
        $fillMethod = 'fill{$table->getEntityName()}',
        $buildArrayMethod = 'buildArray{$table->getEntityName()}s'
    ) {
        if (is_null($criteria)) {
            $criteria['fields'] = "*";
            $criteria['from'] = $this->factory->getDbName() .
                ".{$table->getName()}";
            $criteria['where'] = null;
            $criteria['order_by'] = null;
            $criteria['limit'] = null;
        } else {
            if (!isset($criteria['fields'])) {
                $criteria['fields'] = "*";
            }
            if (!isset($criteria['from'])) {
                $criteria['from'] = $this->factory->getDbName() .
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

        $sth = $this->factory->getConnection()->prepare($sql);

        $sth->execute(isset($criteria['bind']) ? $criteria['bind'] : null);

        ${$table->getAttributeName()}s = array();

        while ($row = $sth->fetch(PDO::FETCH_ASSOC)) {
            $this->$buildArrayMethod(${$table->getAttributeName()}s, $this->$fillMethod($row));
        }

        return ${$table->getAttributeName()}s;
    }

    public function get{$table->getEntityName()}sCount($criteria = null)
    {
        if (is_null($criteria)) {
            $criteria[ 'from' ] = $this->factory->getDbName() . ".{$table->getName()}";
            $criteria[ 'where' ] = null;
            $criteria[ 'order_by' ] = null;
            $criteria[ 'limit' ] = null;
        } else {
            if( !isset( $criteria[ 'from' ] ) ) {
                $criteria[ 'from' ] = $this->factory->getDbName() . ".{$table->getName()}";
            }
            if( !isset( $criteria[ 'where' ] ) ) {
                $criteria[ 'where' ] = null;
            }
            if( !isset( $criteria[ 'order_by' ] ) ) {
                $criteria[ 'order_by' ] = null;
            }
            if( !isset( $criteria[ 'limit' ] ) ) {
                $criteria[ 'limit' ] = null;
            }
        }

        $sql = "SELECT count(*) AS count " .
            " FROM " . $criteria[ 'from' ];

        if( !is_null( $criteria[ 'where' ] ) ) {
            $sql .= " WHERE " . $criteria[ 'where' ];
        }

        if( !is_null( $criteria[ 'order_by' ] ) ) {
            $sql .= " ORDER BY " . $criteria[ 'order_by' ];
        }

        if( !is_null( $criteria[ 'limit' ] ) ) {
            $sql .= " LIMIT " . $criteria[ 'limit' ];
        }

        $sth = $this->factory->getConnection()->prepare( $sql );

        $sth->execute( isset( $criteria[ 'bind' ] ) ? $criteria[ 'bind' ] :
            null );

        $row = $sth->fetch( PDO::FETCH_ASSOC );

        if( count( $row ) ) {
            return $row[ 'count' ];
        }

        return 0;
    }
    
    public function get{$table->getEntityName()}ByPk({foreach $table->getPkFields() as $field}${$field->getAttributeName()}{if !$field@last}, {/if}{/foreach})
    {
        $criteria = null;
        $criteria[ 'where' ] = {foreach $table->getPkFields() as $field}" {$field->getName()} = :{$field->getName()}{if !$field@last} AND" .
                               {else}"{/if}{/foreach};
        $values = array(
            {foreach $table->getPkFields() as $field}":{$field->getName()}" =>  ${$field->getAttributeName()}{if !$field@last},
            {/if}{/foreach}

        );
        $criteria[ 'bind' ] = $values;
        ${$table->getAttributeName()}s = $this->get{$table->getEntityName()}s($criteria);
        return ${$table->getAttributeName()}s[ 0 ];
    }

    /**
     * Add the filled row to the array of {$table->getAttributeName()}s to be returned by the
     * get{$table->getEntityName()}s method
     *
     * @param array ${$table->getAttributeName()}s
     * @param {$identifierName}{$table->getEntityName()}Dto $item
     */
    public function buildArray{$table->getEntityName()}s( &${$table->getAttributeName()}s, $item ){
        ${$table->getAttributeName()}s[] = $item;
    }

    /**
     * Return one {$identifierName}{$table->getEntityName()}Dto with filled by row values.
     *
     * @param array $row
     * @return {$identifierName}{$table->getEntityName()}Dto
     */
    public function fill{$table->getEntityName()}( $row, $fillClass = '{$identifierName}{$table->getEntityName()}Dto' ){
        ${$table->getAttributeName()} = new $fillClass();

{foreach $table->getFields() as $field}
        ${$table->getAttributeName()}->set{$field->getEntityName()}($row[ '{$field->getName()}']);
{/foreach}

        return ${$table->getAttributeName()};
    }

    public function update{$table->getEntityName()}($criteria = null) {
        if( is_null( $criteria ) ) {
            $criteria[ 'fields' ] = null;
            $criteria[ 'where' ] = null;
        }
        else {
            if( !isset( $criteria[ 'fields' ] ) ) {
                $criteria[ 'fields' ] = null;
            }
            if( !isset( $criteria[ 'where' ] ) ) {
                $criteria[ 'where' ] = null;
            }
        }

        if($criteria[ 'fields' ] != null) {
            $sql = "UPDATE " . $this->factory->getDbName() .
                ".{$table->getName()} SET " . $criteria[ 'fields' ];
            if($criteria[ 'where' ] != null) {
                $sql .= " WHERE " . $criteria[ 'where' ];
            }
            $sth = $this->factory->getConnection()->prepare( $sql );
            return $sth->execute( isset( $criteria[ 'bind' ] ) ? $criteria[ 'bind' ] : null );
        }
    }

    public function save{$table->getEntityName()}(
        {$identifierName}{$table->getEntityName()}Dto ${$table->getAttributeName()},
        $transaction
    ) {
        $sth = null;
        $values = array(
{foreach $table->getFields() as $field}
            ":{$field->getName()}" => ${$table->getAttributeName()}->get{$field->getEntityName()}(){if !$field@last},
{/if}
{/foreach}
        
        );

        if( $transaction == {$identifierName}AbstractDaoFactory::INSERT_TRANSACTION ) {
{foreach $table->getSerialFields() as $field}
            unset( $values[ ':{$field->getName()}' ] );
{/foreach}

            $sql = "INSERT INTO " . $this->factory->getDbName() . ".{$table->getName()}( " .
{foreach $table->getNonSerialFields() as $field}
                       "{$field->getName()}{if !$field@last}, " .
{/if}
{/foreach} )" .
                   "VALUES ( " .
{foreach $table->getNonSerialFields() as $field}
                       ":{$field->getName()}{if !$field@last}, " .
{/if}
{/foreach} )";
        }
        else{
            $sql = "UPDATE " . $this->factory->getDbName() . ".{$table->getName()} SET " .
{foreach $table->getNonPkFields() as $field}
                       "{$field->getName()} = :{$field->getName()}{if !$field@last}, " .
{/if}
{/foreach} )" .
                   "WHERE " .
{foreach $table->getPkFields() as $field}
                       "{$field->getName()} = :{$field->getName()}{if !$field@last}, " .
{/if}
{/foreach} )";
        }
        $sth = $this->factory->getConnection()->prepare( $sql );
        $result = $sth->execute( $values );
{if count($table->getPkFields()) eq  1}
        if ($transaction == {$identifierName}AbstractDaoFactory::INSERT_TRANSACTION) {
            if ($result) {
                ${$table->getAttributeName()}->set{$table->getFirstPkField()->getEntityName()}($this->factory->getConnection()->lastInsertId());
            }
        }
{/if}
        return $result;
    }

    public function delete{$table->getEntityName()}({foreach $table->getPkFields() as $field}${$field->getAttributeName()}{if !$field@last}, {/if}{/foreach})
    {
        $sql = "DELETE FROM " .
                   $this->factory->getDbName() . ".{$table->getName()} " .
               "WHERE " .
{foreach $table->getPkFields() as $field}
                   "{$field->getName()} = :{$field->getName()}{if !$field@last}, " .
{/if}
{/foreach} )";

        $values = array(
{foreach $table->getPkFields() as $field}
            ":{$field->getName()}" => ${$field->getAttributeName()}{if !$field@last},
{/if}
{/foreach}
        );
        $sth = $this->factory->getConnection()->prepare($sql);
        return $sth->execute($values);
    }

}
