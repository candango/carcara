<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

$devPath = "/../vendor/autoload.php";
$fromVendorPath = "/../../../autoload.php";

if (file_exists($file = __DIR__ . $devPath)) {
    $loader = require_once $file;
}
if (file_exists($file = __DIR__ . $fromVendorPath)) {
    $loader = require_once $file;
}

Candango\Carcara\Application::run($argv);
