<?php
/**
 * ${identifier-name-upper}${table.getEntityName()}AbstractPgsqlDao - ${identifier-name-upper}${table.getEntityName()}AbstractPgsqlDao.php
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
 * @package lib.dao.${table.getName()}
 * @author Carcara Modeller Tool Engine
 * @copyright Put your copyright here.
 * @license Put your copyright here.
 * @version    
 */

require_once "dao/${table.getName()}/${identifier-name-upper}${table.getEntityName()}Dto.php";

require_once "dao/${table.getName()}/${identifier-name-upper}${table.getEntityName()}Dao.php";

/**
 * ${identifier-name-upper}${table.getEntityName()}AbstractPgsqlDao - ${identifier-name-upper}${table.getEntityName()}AbstractPgsqlDao.php
 * 
 * Abstract dao factory class. This class returns all dao factories found
 * by the engine.
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
abstract class ${identifier-name-upper}${table.getEntityName()}AbstractPgsqlDao implements ${identifier-name-upper}${table.getEntityName()}Dao {

    /**
     *
     * @var ShopPgsqlDaoFactory
     */
    protected $factory;

    /**
     *
     * @var PDOStatement
     */
    protected $latestStatement;

    public function __construct( ShopPgsqlDaoFactory $factory = null ) {
        $this->factory = $factory;
    }

    public function get${table.getEntityName()}s( $criteria = null, $fillMethod = 'fill${table.getEntityName()}s' ) { 
        if( is_null( $criteria ) ) {
            $criteria[ 'fields' ] = "*";
            $criteria[ 'from' ] = "${identifier-name}.${table.getName()}"; 
            $criteria[ 'where' ] = null;
            $criteria[ 'order_by' ] = null;
            $criteria[ 'limit' ] = null;
        }
        else {
            if( !isset( $criteria[ 'fields' ] ) ) {
                $criteria[ 'fields' ] = "*";
            }
            if( !isset( $criteria[ 'from' ] ) ) {
                $criteria[ 'from' ] = "${identifier-name}.${table.getName()}";
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

        $sql = "SELECT " . $criteria[ 'fields' ] .
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

        $this->latestStatement = $this->factory->getConnection()->prepare( $sql );

        $this->latestStatement->execute( isset( $criteria[ 'bind' ] ) ? $criteria[ 'bind' ] :
            null );

        $${table.getAttributeName()}s = array();

        while( $row = $this->latestStatement->fetch( PDO::FETCH_ASSOC ) ) {
            $${table.getAttributeName()}s[] = $this->$fillMethod( $row );
        }

        return $${table.getAttributeName()}s;
    }
    
    public function get${table.getEntityName()}sCount( $criteria = null ) { 
        if( is_null( $criteria ) ) {
            $criteria[ 'from' ] = "${identifier-name}.${table.getName()}"; 
            $criteria[ 'where' ] = null;
            $criteria[ 'order_by' ] = null;
            $criteria[ 'limit' ] = null;
        }
        else {
            if( !isset( $criteria[ 'from' ] ) ) {
                $criteria[ 'from' ] = "${identifier-name}.${table.getName()}";
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

        $this->latestStatement = $this->getFactory()->getConnection()->prepare( $sql );

        $this->latestStatement->execute( isset( $criteria[ 'bind' ] ) ? $criteria[ 'bind' ] :
            null );

        $row = $this->latestStatement->fetch( PDO::FETCH_ASSOC );

        if( count( $row ) ) {
            return $row[ 'count' ];
        }

        return 0;
    }
    
    public function get${table.getEntityName()}ByPk( #foreach( $field in $table.getPks())$$field.getAttributeName()#if( $velocityHasNext ),#end #end){
        $criteria = null;

        $criteria[ 'where' ] = #foreach( $field in $table.getPks())" $field.getName() = :$field.getName()#if( $velocityHasNext ) AND" .
                               #else"#end#end;

        $values = array(
            #foreach( $field in $table.getPks())":$field.getName()" => $$field.getAttributeName()#if( $velocityHasNext ),
            #end#end

        );

        $criteria[ 'bind' ] = $values;

        $${table.getAttributeName()}s = $this->get${table.getEntityName()}s( $criteria );

        return $${table.getAttributeName()}s[ 0 ];
    }

    /**
     * Return one ${identifier-name-upper}${table.getEntityName()}Dto with properties filled with the row values.
     *
     * @param array $row
     * @return ${identifier-name-upper}${table.getEntityName()}Dto
     */
    public function fill${table.getEntityName()}( $row, $fillClass = '${identifier-name-upper}${table.getEntityName()}Dto' ){
        $${table.getAttributeName()} = new $fillClass();

#foreach( $field in $table.getFields())
        $${table.getAttributeName()}->set${field.getEntityName()}( $row[ '${field.getName()}' ] );
#end

        return $${table.getAttributeName()};
    }

    public function save${table.getEntityName()}( ${identifier-name-upper}${table.getEntityName()}Dto $${table.getAttributeName()},
         $transaction ){
        $sql = "";
        $this->latestStatement = null;
        $values = array(
#foreach( $field in $table.getFields())
            ":${field.getName()}" => $${table.getAttributeName()}->get${field.getEntityName()}()#if( $velocityHasNext ),
#end
#end
        
        );

        if( $transaction == Conf::INSERT ) {
#foreach( $field in $table.getSerials())
            unset( $values[ ':${field.getName()}' ] );
#end

            $sql = "INSERT INTO  ${identifier-name}.${table.getName()}  ( " .
#foreach( $field in $table.getNonSerials())
                       "${field.getName()}#if( $velocityHasNext ), " .
#end
#end )" .
                   "VALUES ( " .
#foreach( $field in $table.getNonSerials())
                       ":${field.getName()}#if( $velocityHasNext ), " .
#end
#end )";
        }
        else{
            $sql = "UPDATE ${identifier-name}.${table.getName()} SET " .
#foreach( $field in $table.getNonPks())
                       "${field.getName()} = :${field.getName()}#if( $velocityHasNext ), " .
#end
#end )" .
                   "WHERE " .
#foreach( $field in $table.getPks())
                       "${field.getName()} = :${field.getName()}#if( $velocityHasNext ), " .
#end
#end )";
        }

        $this->latestStatement = $this->factory->getConnection()->prepare( $sql );

        $this->latestStatement->execute( $values );
    }

    public function delete${table.getEntityName()}( #foreach( $field in $table.getPks())$$field.getAttributeName()#if( $velocityHasNext ),#end #end) { 
        $sql = "DELETE FROM " .
                   "${identifier-name}.${table.getName()} " .
               "WHERE " .
#foreach( $field in $table.getPks())
                   "${field.getName()} = :${field.getName()}#if( $velocityHasNext ), " .
#end
#end )";

        $values = array(
#foreach( $field in $table.getPks())
            ":${field.getName()}" => $${field.getAttributeName()}#if( $velocityHasNext ),
#end
#end
        
        );

        $this->latestStatement = $this->factory->getConnection()->prepare( $sql );

        $this->latestStatement->execute( $values );
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
