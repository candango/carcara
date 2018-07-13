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
    use Candango\Carcara\File;
    use Candango\Carcara\Model\DataSource\Config;

    class InitCommand
    {

        public function run($getopt)
        {
            echo "Checking config structure.\n";

            $config = new Config();

            $configDir = $config->getConfigDir();

            if (file_exists($configDir) && is_dir($configDir)) {
                echo "The config directory already exits.\n";
            } else {
                echo "Creating config directory ... ";
                mkdir($configDir);
                echo "[ OK ].\n";
            }

            echo sprintf("Data Source name: [%s]", $config->getName());
            $name = Cli::read();
            if ($name != "") {
                $config->setName($name);
            }

            $configFile = $configDir . DIRECTORY_SEPARATOR . $config->getName()
                . "_conf.php";

            echo sprintf("Creating Data Source config file at %s ... ",
                $configFile);

            if (file_exists($configFile)) {
                echo sprintf("[ WARN ]\nData Source config file %s already " .
                    "exists use command \"edit\"", $configFile);
                exit(1);
            } else {
                if (touch($configFile)) {
                    echo "[ OK ]\n";

                    echo sprintf("Setting Data Source %s:\n",
                        $config->getName());


                    echo sprintf("Type: [%s]", $config->getType());
                    $type = Cli::read();
                    if ($type != "") {
                        $config->setType($type);
                    }

                    echo sprintf("Host: [%s]", $config->getHost());
                    $host = Cli::read();
                    if ($host != "") {
                        $config->setHost($host);
                    }

                    echo sprintf("Database: [%s]", $config->getDatabase());
                    $config->setDatabase(Cli::read());

                    echo sprintf("User: [%s]", $config->getUser());
                    $config->setUser(Cli::read());

                    echo sprintf("Password: [%s]", $config->getPassword());
                    $config->setPassword(Cli::read());

                    echo sprintf("Saving file %s ...", $configFile);

                    File::write($configFile, $config->fetch());

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