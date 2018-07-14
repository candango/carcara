<?php
/**
 * Abstract class that defines the ${identifier-name}${table.getEntityName()}Dto's.
 * This class is extended by ${identifier-name}${table.getEntityName()}Dto that is the right
 * place to customize the dto's properies and getters and setters methods.
 * 
 * @package lib.dao.${table.getName()}
 * @author Carcara Modeller Tool Engine
 */
abstract class ${identifier-name}${table.getEntityName()}AbstractDto {
#foreach ($field in $table.getFields())

    /**
     * ${identifier-name}${table.getEntityName()}'s ${field.getAttributeName()} attribute
     **/
    private $${field.getAttributeName()};
#end
#foreach ($field in $table.getFields())

    /**
     * Return ${identifier-name}${table.getEntityName()}AbstractDto's ${field.getAttributeName()} attribute
     *
     * @return mixed
     **/
    public function get${field.getEntityName()}() {
        return $this->${field.getAttributeName()};
    }

    /**
     * Set ${identifier-name}${table.getEntityName()}AbstractDto's ${field.getAttributeName()} attribute
     *
     * @param mixed $${field.getAttributeName()}
     **/
    public function set${field.getEntityName()}( $${field.getAttributeName()} ) {
        $this->${field.getAttributeName()} = $${field.getAttributeName()};
    }
#end
}
