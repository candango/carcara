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

    class InitCommand
    {
        private $name = null;

        private $type = null;

        private $host = null;

        private $database = null;

        private $user = null;

        private $password = null;


        public function getName()
        {
            return $this->name;
        }

        public function getType()
        {
            return $this->type;
        }

        public function getHost()
        {
            return $this->host;
        }

        public function getDatabase()
        {
            return $this->database;
        }

        public function getUser()
        {
            return $this->user;
        }

        public function getPassword()
        {
            return $this->password;
        }

        public function run($getopt)
        {
            $configTplDir =  \Candango\Carcara\ROOT_PATH . DIRECTORY_SEPARATOR .
                "template" . DIRECTORY_SEPARATOR . "config";

            echo "Checking config structure.\n";
            $currentDir = getcwd();

            $configDir = $currentDir . DIRECTORY_SEPARATOR . "config";

            if (file_exists($configDir) && is_dir($configDir)) {
                echo "The config directory already exits.\n";
            } else {
                echo "Creating config directory ... ";
                mkdir($configDir);
                echo "[ OK ].\n";
            }

            if(is_null($this->name)){
                $this->name = "default";
                echo sprintf("Data Source name: [%s]", $this->name);
                $name = Cli::read();
                if ($name != "") {
                    $this->name = $name;
                }
            }

            $configFile = $configDir . DIRECTORY_SEPARATOR . $this->name .
                "_conf.php";

            echo sprintf("Creating Data Source config file at %s ... ",
                $configFile);

            if (file_exists($configFile)) {
                echo sprintf("[ WARN ]\nData Source config file %s already " .
                    "exists use command \"edit\"", $configFile);
                exit(1);
            } else {
                if (touch($configFile)) {
                    echo "[ OK ]\n";

                    echo sprintf("Setting Data Source %s:\n", $this->name);

                    if(is_null($this->type)){
                        $this->type = "mysql";
                        echo sprintf("Type: [%s]", $this->type);
                        $type = Cli::read();
                        if ($type != "") {
                            $this->type = $type;
                        }
                    }

                    if(is_null($this->host)){
                        $this->host = "localhost";
                        echo sprintf("Host: [%s]", $this->host);
                        $host = Cli::read();
                        if ($host != "") {
                            $this->host = $host;
                        }
                    }

                    if(is_null($this->database)){
                        $this->database = "";
                        echo sprintf("Database: [%s]", $this->database);
                        $this->database = Cli::read();
                    }

                    if(is_null($this->user)){
                        $this->user = "";
                        echo sprintf("User: [%s]", $this->user);
                        $this->user = Cli::read();
                    }

                    if(is_null($this->password)){
                        $this->password = "";
                        echo sprintf("Password: [%s]", $this->password);
                        $this->password = Cli::read();
                    }

                    $smarty = new \Smarty();

                    $smarty->assign("config", $this);

                    $config = $smarty->fetch($configTplDir .
                        DIRECTORY_SEPARATOR . "config.tpl");

                    echo sprintf("Saving file %s ...", $configFile);

                    File::write($configFile, $config);

                    File::delete($smarty->getCompileDir());

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
