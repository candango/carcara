<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Commands {

    use Candango\Carcara\Cli;
    use Candango\Carcara\Command;
    use Candango\Carcara\File;
    use Candango\Carcara\Model\DataSource\Configuration;
    use GetOpt\Operand;

    class DaoCommand implements Command
    {
        public function brief()
        {
            return "Create a new Data Source config.";
        }

        public function getName()
        {
            return "dao";
        }

        function getOperands()
        {
            return [
                Operand::create('config', Operand::REQUIRED)->setDescription(
                    "Data Source configuration name"),
            ];
        }

        public function run($getopt)
        {
            $config = new Configuration();

            echo "Checking if the Data Source config exists ... ";

            $name = $getopt->getOperand('config');

            $configFile  = $config->getConfigDir() . DIRECTORY_SEPARATOR .
                $name . "_conf.php";

            if (file_exists($configFile)) {
                echo "[ OK ].\n";
                $data = include($configFile);
                $config = Configuration::fromData($name, $data);
                echo sprintf("Creating DAO for Data Source %s:\n", $name);
            } else {
                echo "[ FAIL ].\n";
                echo sprintf("File %s doesn't exists.\n", $configFile);
                exit(1);
            }
            exit(0);
        }
    }
}
