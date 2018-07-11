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
        private static $name = null;

        public static function run($getopt) {
            echo "Checking config structure.\n";
            $currentDir = getcwd();

            $configDir = $currentDir . DIRECTORY_SEPARATOR . "config";

            echo "Checking config structure.\n";

            if(file_exists($configDir) && is_dir($configDir)) {
                echo "The config directory already exits.\n";
            }
            else {
                echo "Creating config directory ... ";
                mkdir($configDir);
                echo "[OK].\n";
            }

            if(is_null(self::$name)){
                self::$name = "default";
                echo "Inform the DAO name [default]:";
                $handle = fopen("php://stdin", "r");
                $name = fgets($handle);
                if (trim($name) != "") {
                    self::$name = $name;
                }
                echo(self::$name);
            }

        }
    }
}
