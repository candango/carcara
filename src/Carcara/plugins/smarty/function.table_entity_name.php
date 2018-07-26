<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

/**
 * @param $params
 * @param $smarty Smarty
 * @return string
 */
function smarty_function_table_entity_name($params, &$smarty)
{
    $conf = $smarty->getTemplateVars("conf");
    $table = $params['table'];
    return Candango\Carcara\Lexicon::getTableEntitySuffix($conf, $table);
}
