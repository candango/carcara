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
    use Candango\Carcara\SmartyInABox;

    class Conf
    {
        const MYSQL = "mysql";

        const PGSQL = "pgsql";

        /**
         * Conf name
         *
         * @var string
         */
        private $name;

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
         * Conf database
         *
         * @var string
         */
        private $database = "";

        /**
         * Conf schema
         *
         * Some databases will not use the schema.
         *
         * @var string
         */
        private $schema = "";

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
         * Options used while connecting to the PDO driver
         *
         * @var array
         */
        private $pdoOptions = [];

        public function __construct($name="default")
        {
            $this->setName($name);
        }

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
         * @return void
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
         * @param string $type
         * @return void
         * @throws \Exception
         */
        public function setType($type)
        {
            if(in_array($type, $this->getAllowedTypes())){
                $this->type = $type;
            } else {
                throw new \Exception("Invalid type.");
            }
            if ($this->getType() == self::MYSQL) {
                $this->setPdoOptions(array(
                    \PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES UTF8",
                    \PDO::ATTR_ERRMODE,
                    \PDO::ERRMODE_EXCEPTION
                ));
            } else {
                $this->pdoOptions = [];
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
         * @param string $database
         * @return void
         */
        public function setDatabase($database)
        {
            $this->database = $database;
        }

        /**
         * Returns conf schema
         *
         * @return string
         */
        public function getSchema()
        {
            return $this->schema;
        }

        /**
         * Set conf schema
         *
         * @param string $schema
         * @return void
         */
        public function setSchema($schema)
        {
            $this->schema = $schema;
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
         * @param string $user
         * @return void
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
         * @param string $password
         * @return void
         */
        public function setPassword($password)
        {
            $this->password = $password;
        }

        /**
         * Return the pdo options array
         *
         * @return array
         */
        public function getPdoOptions()
        {
            return $this->pdoOptions;
        }

        /**
         * Set PDO options array
         *
         * @param array $options
         * @return void
         */
        public function setPdoOptions($options=[])
        {
            $this->pdoOptions = $options;
        }

        /**
         * Return the conf identifier
         *
         * #TODO: This should be handled better for PGSQL as that could be
         * changed to schema.
         *
         * @return string
         */
        public function getIdentifier()
        {
            return $this->getDatabase();
        }

        /**
         * Returns the conf directory.
         *
         * If base dir is null current dir will be use as base.
         *
         * @param null $baseDir
         * @return string
         */
        public function getConfDir($baseDir=null)
        {
            if(is_null($baseDir)){
                $baseDir = getcwd();
            }
            return $baseDir . DIRECTORY_SEPARATOR . "conf";
        }

        /**
         * Return where the conf file is located at.
         *
         * @return string
         */
        public function getFilePath()
        {
            return $this->getConfDir() . DIRECTORY_SEPARATOR . $this->getName()
                . "_conf.php";
        }

        /**
         * Returns the lib directory.
         *
         * If base dir is null current dir will be use as base.
         *
         * The lib dir is where dao and services will be generated.
         *
         * @param null $baseDir
         * @return string
         */
        public function getLibDir($baseDir=null)
        {
            if(is_null($baseDir)){
                $baseDir = getcwd();
            }
            return $baseDir . DIRECTORY_SEPARATOR . "lib";
        }

        public function getDaoDir($baseDir=null)
        {
            $libDir = $this->getLibDir($baseDir);
            return $libDir . DIRECTORY_SEPARATOR . "dao";
        }

        public function getCurrentDaoDir($baseDir=null)
        {
            return $this->getDaoDir($baseDir). DIRECTORY_SEPARATOR .
                $this->getName();
        }

        public function getDsn() {
            $dsn = sprintf("%s:host=%s;dbname=%s",
                $this->getType(), $this->getHost(), $this->getDatabase());
            return $dsn;
        }

        /**
         * Returns the conf data
         *
         * @return string
         * @throws \Exception
         */
        public function fetch()
        {
            SmartyInABox::getInstance()->assign("config", $this);
            $data = SmartyInABox::fetch("config" . DIRECTORY_SEPARATOR .
                "config.tpl");
            return $data;
        }

        public function getAllowedTypes()
        {
            return array(self::MYSQL, self::PGSQL);
        }

        /**
         * Returns a conf from a data array.
         *
         * @param array $data The conf data
         * @return void
         * @throws \Exception
         */
        public function setData($data)
        {
            $this->setName($data['name']);
            $this->setType($data['type']);
            $this->setHost($data['host']);
            $this->setDatabase($data['database']);
            if($this->getType()==self::PGSQL) {
                $this->setSchema($data['schema']);
            }
            $this->setUser($data['user']);
            $this->setPassword($data['password']);
        }

        /**
         * Returns a conf from a data array.
         *
         * @param $name The conf name
         * @param $data The conf data
         * @return Conf
         * @throws \Exception
         */
        public static function fromData($name, $data)
        {
            $config = new Conf();
            if (!array_key_exists("name", $data)) {
                $data['name'] = $name;
            }
            $config->setData($data);
            return $config;
        }

    }
}
