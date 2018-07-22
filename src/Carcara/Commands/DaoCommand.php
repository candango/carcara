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
    use Candango\Carcara\Engine\AbstractDatabaseLoader;
    use Candango\Carcara\File;
    use Candango\Carcara\Model\Conf;
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
                Operand::create('action', Operand::REQUIRED)->setDescription(
                    "DAO action to executed. Allowed actions: \n" .
                    "    - gen(erate) Generate the DAO structure"),
            ];
        }

        public function run($getopt)
        {
            $config = new Conf();

            $name = $getopt->getOperand('config');

            $action = $getopt->getOperand('action');

            $allowedActions = ["gen", "generate"];

            if (!in_array($action, $allowedActions)) {
                echo "[ FAIL ].\n";
                echo sprintf("The action %s isn't allowed.\n", $action);
                exit(1);
            }

            $configFile  = $config->getConfigDir() . DIRECTORY_SEPARATOR .
                $name . "_conf.php";

            switch ($action){
                case "gen":
                case "generate":
                    echo sprintf("Generating DAO for Data Source %s\n",
                        $name);
                    echo "Checking if the Data Source config exists ... ";
                    if (file_exists($configFile)) {
                        echo "[ OK ].\n";
                        $data = include($configFile);
                        $config = Conf::fromData($name, $data);
                        echo sprintf("Connecting to the database %s ... ",
                            $config->getDatabase());
                        $loader = AbstractDatabaseLoader::getLoader($config);
                        try {
                            $loader->connect();
                        } catch (\PDOException $e) {
                            echo "[ FAIL ].\n";
                            echo "ERROR: " . $e->getMessage() .
                                ".\nCheck your configuration.\n";
                            exit(3);
                        }
                        echo "[ OK ].\n";
                        echo "Loading tables ... ";
                        $loader->doLoad();
                        echo "[ OK ].\n";
                        echo sprintf("%s tables loaded.\n",
                            count($loader->getTables()));
                        $loader->disconnect();
                        echo sprintf("Disconnected from the database %s.\n",
                            $config->getDatabase());
                    } else {
                        echo "[ FAIL ].\n";
                        echo sprintf("File %s doesn't exists.\n", $configFile);
                        exit(1);
                    }
                    break;
            }
            exit(0);
        }

        private function prepareDaoPath(Conf $conf) {

        }
    }
}
