<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Commands {

    class InitCommand
    {
        public static function run($getopt) {
            echo sprintf("Creating config structure..." .
                PHP_EOL, phpversion());
        }
    }
}
