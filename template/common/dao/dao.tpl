<?php
/**
 * {$identifierName}{$table->getEntityName()}Dao - {$identifierName}{$table->getEntityName()}Dao.php
 * 
 * Interface that defines one {$identifierName}{$table->getEntityName()}Dao.
 * This interfaces have basic methods to handle CRUD operations for 
 * {$identifierName}{$table->getEntityName()}Dto's.
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

/**
 * {$identifierName}{$table->getEntityName()}Dao - {$identifierName}{$table->getEntityName()}Dao.php
 * 
 * Interface that defines one {$identifierName}{$table->getEntityName()}Dao.
 * This interfaces have basic methods to handle CRUD operations for 
 * {$identifierName}{$table->getEntityName()}Dto's.
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
interface {$identifierName}{$table->getEntityName()}Dao
{

    public function getByCriteria(
        $criteria = null,
        $fillMethod = "fillEntity",
        $buildArrayMethod = "buildArray"
    );

    public function getCount($criteria = null);

    public function getByPk({foreach $table->getPkFields() as $field}${$field->getAttributeName()}{if !$field@last}, {/if}{/foreach});

    /**
     * Update the {$table->getName()} table based on a given criteria.
     *
     * @param array|null $criteria
     * @return bool
     */
    public function update($criteria = null);

    /**
     * Save (insert or update) the {$identifierName}{$table->getEntityName()}Dto
     * into the {$table->getName()} table based on the transaction type
     * informed.
     *
     * @param {$identifierName}{$table->getEntityName()}Dto ${$table->getAttributeName()}
     * @param string $transaction
     * @return bool
     */
    public function save({$identifierName}{$table->getEntityName()}Dto ${$table->getAttributeName()}, $transaction);

    public function delete({foreach $table->getPkFields() as $field}${$field->getAttributeName()}{if !$field@last}, {/if}{/foreach});

    /**
     * Return the latest statement executed.
     *
     * @return PDOStatement
     */
    public function getLatestStatement();
}
