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
 * @return string string
 */
function smarty_function_entity_name($params, &$smarty) {
    $entity = $params['entity'];
    return Candango\Carcara\Lexicon::getEntityName($entity);
}
