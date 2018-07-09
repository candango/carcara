<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

if (file_exists($file = __DIR__ . "/../vendor/autoload.php")) {
    $loader = require_once $file;
}

$app = new Candango\Carcara\Application;

$app->run($argv);
