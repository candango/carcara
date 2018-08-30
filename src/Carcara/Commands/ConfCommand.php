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

    use Candango\Carcara\AbstractCommand;
    use Candango\Carcara\Cli;
    use Candango\Carcara\Factory;
    use Candango\Carcara\File;
    use Candango\Carcara\Model\Conf;
    use Candango\Carcara\SmartyInABox;
    use GetOpt\GetOpt;
    use GetOpt\Operand;

    class ConfCommand extends AbstractCommand
    {
        public function brief()
        {
            return "Execute actions related to Configurations.";
        }

        function getOperands()
        {
            $actionOperandTpl = "commands/conf/action_operand.tpl";
            return [
                Operand::create("action", Operand::REQUIRED)->setDescription(
                    SmartyInABox::fetch($actionOperandTpl)),
                Operand::create("name", Operand::OPTIONAL)->setDescription(
                    "Configuration name")
            ];
        }

        public function run($getopt)
        {
            $action = $getopt->getOperand('action');

            $allowedActions = ["create", "list", "show"];

            if (!in_array($action, $allowedActions)) {
                echo sprintf("The action %s is invalid.\n\n", $action);
                echo $getopt->getHelpText();
                exit(1);
            }

            switch ($action) {
                case "create":
                    $this->createConf($getopt);
                    break;
                case "list":
                    $this->listConfs($getopt);
                    break;
                case "show":
                    $this->showConf($getopt);
                    break;
            }
            exit(0);
        }

        /**
         * Creates a new conf
         *
         * @param $getopt
         * @throws \Exception
         */
        private function createConf($getopt)
        {
            echo "Checking conf structure.\n";

            $conf = new Conf();

            $confDir = $conf->getConfDir();

            if (file_exists($confDir) && is_dir($confDir)) {
                echo "The conf directory already exits.\n";
            } else {
                echo "Creating conf directory ... ";
                if(mkdir($confDir)) {
                    echo "[ OK ].\n";
                } else {
                    echo "[ ERROR ].\n";
                    exit(1);
                }

            }

            echo sprintf("Conf name: [%s]", $conf->getName());
            $name = Cli::read();
            if ($name != "") {
                $conf->setName($name);
            }

            $confFile = $confDir . DIRECTORY_SEPARATOR . $conf->getName()
                . "_conf.php";

            echo sprintf("Creating conf file at %s ... ", $confFile);

            if (file_exists($confFile)) {
                echo sprintf("[ WARN ]\nConf file %s already exists use " .
                    "command \"edit\"", $confFile);
                exit(1);
            } else {
                if (touch($confFile)) {
                    echo "[ OK ]\n";

                    echo sprintf("Setting conf %s:\n", $conf->getName());

                    echo sprintf("Type: [%s]", $conf->getType());
                    $type = Cli::read();
                    if ($type != "") {
                        try {
                            $conf->setType($type);
                        } catch (\Exception $error) {
                            echo "[ ERROR ] " . $error->getMessage() . "\n";
                            echo sprintf("Deleting conf file at %s ... ",
                                $confFile);
                            File::delete($confFile);
                            echo "[ OK ]\n";
                            exit(3);
                        }
                    }

                    echo sprintf("Host: [%s]", $conf->getHost());
                    $host = Cli::read();
                    if ($host != "") {
                        $conf->setHost($host);
                    }

                    echo sprintf("Database: [%s]", $conf->getDatabase());
                    $conf->setDatabase(Cli::read());

                    echo sprintf("User: [%s]", $conf->getUser());
                    $conf->setUser(Cli::read());

                    echo sprintf("Password: [%s]", $conf->getPassword());
                    $conf->setPassword(Cli::read());

                    echo sprintf("Saving conf file %s ...", $confFile);

                    File::write($confFile, $conf->fetch());

                    echo "[ OK ]\n";
                    exit(0);
                } else {
                    echo "[ ERROR ]";
                    exit(2);
                }
            }
        }

        /**
         * List existing confs
         *
         * @param GetOpt $getopt
         * @throws \Exception
         * @throws \SmartyException
         */
        private function listConfs($getopt)
        {
            $conf = new Conf();
            $confs = array();

            try {
                $it = new \RecursiveDirectoryIterator($conf->getConfDir(),
                    \FilesystemIterator::SKIP_DOTS);

                foreach (new \RecursiveIteratorIterator($it, 1) as $child) {
                    $name = explode("_", $child->getBaseName())[0];
                    if ($conf = Factory::getConf($name)) {
                        $confs[] = $conf;
                    }
                }
            } catch (\UnexpectedValueException $e) {}

            SmartyInABox::getInstance()->assign("confs", $confs);

            echo SmartyInABox::fetch("commands/conf/list.tpl");
        }

        /**
         * Show a conf
         *
         * @param GetOpt $getopt
         * @throws \Exception
         * @throws \SmartyException
         */
        private function showConf($getopt)
        {

            $name = $getopt->getOperand('name');

            if ($name) {
                $conf = Factory::getConf($name);

                SmartyInABox::getInstance()->assign("conf", $conf);

                echo SmartyInABox::fetch("commands/conf/show.tpl");
            } else {
                echo "Please inform the configuration name.\n";
                echo SmartyInABox::fetch("commands/conf/show_usage.tpl");
                exit(2);
            }

        }
    }
}
