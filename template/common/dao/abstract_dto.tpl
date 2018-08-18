<?php
/**
 * Abstract class that defines the {$identifierName}{$table->getEntityName()}Dto.
 * This class is extended by {$identifierName}{$table->getEntityName()}Dto that
 * is used to customized your dto structure.
 * 
 * @package lib.dao.{$table->getName()}
 * @author Carcara Modeller Tool Engine
 */
abstract class {$identifierName}{$table->getEntityName()}AbstractDto
{
{foreach $table->getFields() as $field}

    /**
     * {$identifierName}{$table->getEntityName()}'s {$field->getAttributeName()} attribute
     */
    private ${$field->getAttributeName()};
{/foreach}
{foreach $table->getFields() as $field}

    /**
     * Return {$identifierName}{$table->getEntityName()}AbstractDto's {$field->getAttributeName()} attribute
     *
     * @return mixed
     */
    public function get{$field->getEntityName()}()
    {
        return $this->{$field->getAttributeName()};
    }

    /**
     * Set {$identifierName}{$table->getEntityName()}AbstractDto's {$field->getAttributeName()} attribute
     *
     * @param mixed ${$field->getAttributeName()}
     */
    public function set{$field->getEntityName()}(${$field->getAttributeName()})
    {
        $this->{$field->getAttributeName()} = ${$field->getAttributeName()};
    }
{/foreach}
}
