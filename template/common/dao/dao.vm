<?php
/**
 * ${identifier-name}${table.getEntityName()}Dao - ${identifier-name}${table.getEntityName()}Dao.php
 * 
 * Interface that defines one ${identifier-name}${table.getEntityName()}Dao.
 * This interfaces have basic methods to handle CRUD operations for 
 * ${identifier-name}${table.getEntityName()}Dto's.
 *
 * PHP version 5
 * 
 * Put your license here.
 * 
 * Contributor(s): 
 *
 * @category dao
 * @package lib.dao.${table.getName()}
 * @author Carcara Modeller Tool Engine
 * @copyright Put your copyright here.
 * @license Put your copyright here.
 * @version    
 */

/**
 * ${identifier-name}${table.getEntityName()}Dao - ${identifier-name}${table.getEntityName()}Dao.php
 * 
 * Interface that defines one ${identifier-name}${table.getEntityName()}Dao.
 * This interfaces have basic methods to handle CRUD operations for 
 * ${identifier-name}${table.getEntityName()}Dto's.
 *
 * PHP version 5
 * 
 * @category dao
 * @package lib.dao.${table.getName()}
 * @author Carcara Modeller Tool Engine
 * @copyright Put your copyright here.
 * @license Put your copyright here.
 * @version
 */
interface ${identifier-name}${table.getEntityName()}Dao {
    
    public function get${table.getEntityName()}s( 
        $criteria = null, $fillMethod = 'fill${table.getEntityName()}' ); 

    public function get${table.getEntityName()}sCount( $criteria = null ); 

    public function get${table.getEntityName()}ByPk( #foreach( $field in $table.getPks())$$field.getAttributeName()#if( $velocityHasNext ),#end #end); 

    public function update${table.getEntityName()}($criteria = null);

    public function save${table.getEntityName()}( 
        ${identifier-name}${table.getEntityName()}Dto $${table.getAttributeName()}, $transaction );

    public function delete${table.getEntityName()}( #foreach( $field in $table.getPks())$$field.getAttributeName()#if( $velocityHasNext ),#end #end); 

}
