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
    use Candango\Carcara\File;
    use Candango\Carcara\Model\Conf;
    use Candango\Carcara\SmartyInABox;
    use GetOpt\Operand;

    class ConfCommand extends AbstractCommand
    {
        public function brief()
        {
            return "Execute actions related to Conf.";
        }

        function getOperands()
        {
            $actionOperandTpl = "commands/dao/action_operand.tpl";
            return [
                Operand::create("action", Operand::REQUIRED)->setDescription(
                    SmartyInABox::fetch($actionOperandTpl))
            ];
        }

        public function run($getopt)
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
                        } catch (\Error $error) {
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
    }
}
