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
        private $name = null;

        public function run($getopt)
        {


            echo "Checking config structure.\n";
            $currentDir = getcwd();

            $configDir = $currentDir . DIRECTORY_SEPARATOR . "config";

            echo "Checking config structure.\n";

            if (file_exists($configDir) && is_dir($configDir)) {
                echo "The config directory already exits.\n";
            } else {
                echo "Creating config directory ... ";
                mkdir($configDir);
                echo "[ OK ].\n";
            }

            if(is_null($this->name)){
                $this->name = "default";
                echo "Inform Data Source name: [default]";
                $handle = fopen("php://stdin", "r");
                $name = fgets($handle);
                if (trim($name) != "") {
                    $this->name = trim($name);
                }
            }

            $configFile = $configDir . DIRECTORY_SEPARATOR . $this->name .
                "_conf.php";

            echo sprintf("Creating Data Source config file at %s ... ",
                $configFile);

            if (file_exists($configFile)) {
                echo sprintf("Data Source config file %s already exists " .
                    "use command \"edit\"", $configFile);
                exit(1);
            } else {
                if (touch($configFile)) {
                    echo "[ OK ]";
                } else {
                    echo "[ ERROR ]";
                    exit(2);
                }
            }
        }
    }
}
