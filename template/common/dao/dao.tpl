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

    public function get{$table->getEntityName()}s($criteria = null, $fillMethod = 'fill{$table->getEntityName()}');

    public function get{$table->getEntityName()}sCount( $criteria = null);

    public function get{$table->getEntityName()}ByPk({foreach $table->getPkFields() as $field}${$field->getAttributeName()}{if !$field@last}, {/if}{/foreach});

    public function update{$table->getEntityName()}($criteria = null);

    public function save{$table->getEntityName()}({$identifierName}{$table->getEntityName()}Dto ${$table->getAttributeName()}, $transaction);

    public function delete{$table->getEntityName()}({foreach $table->getPkFields() as $field}${$field->getAttributeName()}{if !$field@last}, {/if}{/foreach});

}
