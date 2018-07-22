<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Model
{
    use Candango\Carcara\File;

    class Configuration
    {
        const MYSQL = "mysql";

        const PGSQL = "pgsql";

        /**
         * Conf name
         *
         * The default value is: default
         *
         * @var string
         */
        private $name = "default";


        /**
         * Conf type
         *
         * Could be mysql or pgsql.
         *
         * The default value is: msyql
         *
         * @var string
         */
        private $type = self::MYSQL;


        /**
         * Conf type
         *
         * The default value is: localhost
         *
         * @var string
         */
        private $host = "localhost";


        /**
         * Config database
         *
         * @var string
         */
        private $database = "";

        /**
         * Config user
         *
         * @var string
         */
        private $user = "";

        /**
         * Config password
         *
         * @var string
         */
        private $password = "";

        /**
         * Returns conf name
         *
         * @return string
         */
        public function getName()
        {
            return $this->name;
        }

        /**
         * Set conf name
         *
         * @param string $name
         */
        public function setName($name)
        {
            $this->name = $name;
        }

        /**
         * Returns conf type
         *
         * @return string
         */
        public function getType()
        {
            return $this->type;
        }

        /**
         * Set conf type
         *
         * @param $type
         * @throws \Error
         */
        public function setType($type)
        {
            if(in_array($type, $this->getAllowedTypes())){
                $this->type = $type;
            } else {
                throw new \Error("Invalid type.");
            }
        }

        /**
         * Returns conf host
         *
         * @return string
         */
        public function getHost()
        {
            return $this->host;
        }

        /**
         * Set conf host
         * @param $host
         * @return void
         */
        public function setHost($host)
        {
            $this->host = $host;
        }

        /**
         * Returns conf database
         *
         * @return string
         */
        public function getDatabase()
        {
            return $this->database;
        }

        /**
         * Set conf database
         *
         * @return string
         */
        public function setDatabase($database)
        {
            $this->database = $database;
        }

        /**
         * Returns conf user
         *
         * @return string
         */
        public function getUser()
        {
            return $this->user;
        }

        /**
         * Set conf user
         *
         * @return string
         */
        public function setUser($user)
        {
            $this->user = $user;
        }

        /**
         * Returns conf password
         *
         * @return string
         */
        public function getPassword()
        {
            return $this->password;
        }

        /**
         * Set conf password
         *
         * @return string
         */
        public function setPassword($password)
        {
            $this->password = $password;
        }

        /**
         * Returns the conf directory.
         *
         * If base dir is null current dir will be use as base.
         *
         * @param null $baseDir
         * @return string
         */
        public function getConfigDir($baseDir=null)
        {
            if(is_null($baseDir)){
                $baseDir = getcwd();
            }
            return $baseDir . DIRECTORY_SEPARATOR . "config";
        }

        /**
         * Returns the conf data
         *
         * @return string
         * @throws \Exception
         * @throws \SmartyException
         */
        public function fetch()
        {
            $smarty = new \Smarty();
            $smarty->assign("config", $this);
            $configTplDir =  \Candango\Carcara\TPL_DIR . DIRECTORY_SEPARATOR .
                "config";
            $data = $smarty->fetch($configTplDir . DIRECTORY_SEPARATOR .
                "config.tpl");

            File::delete($smarty->getCompileDir());

            return $data;
        }

        public function getAllowedTypes() {
            return array(self::MYSQL, self::PGSQL);
        }

        /**
         * Returns a conf from a data array.
         *
         * @param $name The conf name
         * @param $data The conf data
         * @return Configuration
         */
        public static function fromData($name, $data)
        {
            $config = new Configuration();

            $config->setName($name);
            $config->setType($data['type']);
            $config->setHost($data['host']);
            $config->setDatabase($data['database']);
            $config->setUser($data['user']);
            $config->setPassword($data['password']);

            return $config;
        }

    }
}
