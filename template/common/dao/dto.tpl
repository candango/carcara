<?php
require_once "dao/{table_entity_name table=$table}/{entity_name entity=$conf->getIdentifier()}{table_entity_name table=$table}AbstractDto.php";

/**
 * This class extends {entity_name entity=$conf->getIdentifier()}{table_entity_name table=$table}AbstractDto
 * that will be generated by the carcara engine everytime.
 *
 * Here is the right place to customize the dto's properies, getters and
 * setters. Once the carcara engine finds this file in the dao strcure the
 * engine will keep it untouched.
 *
 * @package lib.dao.{table_entity_name table=$table}
 * @author Carcara Modeller Tool Engine
 */
class {entity_name entity=$conf->getIdentifier()}{table_entity_name table=$table}Dto extends {entity_name entity=$conf->getIdentifier()}{table_entity_name table=$table}AbstractDto
{

}
