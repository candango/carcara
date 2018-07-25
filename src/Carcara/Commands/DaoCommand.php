<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Commands
{

    use Candango\Carcara\Command;
    use Candango\Carcara\Engine\AbstractDaoGenerator;
    use Candango\Carcara\Engine\AbstractDatabaseLoader;
    use Candango\Carcara\Model\Conf;
    use GetOpt\GetOpt;
    use GetOpt\Operand;

    class DaoCommand implements Command
    {
        public function brief()
        {
            return "Execute actions related to DAO.";
        }

        public function getName()
        {
            return "dao";
        }

        function getOperands()
        {
            return [
                Operand::create('action', Operand::REQUIRED)->setDescription(
                    "DAO action to executed. Allowed actions: \n" .
                    "    - gen(erate) Generate the DAO structure"),
                Operand::create('conf', Operand::OPTIONAL)->setDescription(
                    "Carcara conf name")
            ];
        }

        /**
         * @param $getopt GetOpt
         */
        public function run($getopt)
        {
            $conf = new Conf();

            $action = $getopt->getOperand('action');

            $name = $getopt->getOperand('conf') ?
                $getopt->getOperand('conf') : "default";

            $allowedActions = ["gen", "generate"];

            if (!in_array($action, $allowedActions)) {
                echo sprintf("The action %s is invalid.\n\n", $action);
                echo $getopt->getHelpText();
                exit(1);
            }

            switch ($action){
                case "gen":
                case "generate":
                    $this->generateDaoAction($conf);
                    break;
            }
            exit(0);
        }

        private function generateDaoAction(Conf $conf)
        {
            echo sprintf("Generating DAO for Data Source %s\n",
                $conf->getName());
            echo "Checking if the conf file exists ... ";
            if (file_exists($this->getConfFile($conf))) {
                echo "[ OK ].\n";
                $data = include($this->getConfFile($conf));
                $conf = Conf::fromData($conf->getName(), $data);
                echo sprintf("Connecting to the database %s ... ",
                    $conf->getDatabase());
                $loader = AbstractDatabaseLoader::getLoader($conf);
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
                    $conf->getDatabase());
                $this->prepareDaoPath($conf);

                $generator = AbstractDaoGenerator::getGenerator($loader);
                $generator->generateDaoFactories();
            } else {
                echo "[ FAIL ].\n";
                echo sprintf("File %s doesn't exists.\n",
                    $this->getConfFile($conf));
                exit(1);
            }
        }

        private function prepareDaoPath(Conf $conf)
        {
            $libDir = $conf->getLibDir();
            echo sprintf("Checking if lib dir exists at %s ... ", $libDir);
            if (!file_exists($libDir)) {
                echo "[ NOT FOUND ]\n";
                echo "Creating lib directory ... ";
                if(mkdir($libDir)) {
                    echo "[ OK ].\n";
                } else {
                    echo "[ ERROR ].\n";
                    exit(1);
                }
            } else {
                echo "[ OK ]\n";
            }

            $daoDir = $conf->getDaoDir();

            echo sprintf("Checking if DAO dir exists at %s ... ", $daoDir);
            if (!file_exists($daoDir)) {
                echo "[ NOT FOUND ]\n";
                echo "Creating DAO directory ... ";
                if(mkdir($daoDir)) {
                    echo "[ OK ].\n";
                } else {
                    echo "[ ERROR ].\n";
                    exit(1);
                }
            } else {
                echo "[ OK ]\n";
            }

        }

        private function generateDaos(Conf $conf)
        {

        }


        private function getConfFile(Conf $conf)
        {
            return $conf->getConfDir() . DIRECTORY_SEPARATOR . $conf->getName()
                . "_conf.php";
        }
    }
}
