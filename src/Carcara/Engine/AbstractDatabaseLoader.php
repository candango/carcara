<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Engine
{


    use Candango\Carcara\Engine\Mysql\MysqlDatabaseLoader;
    use Candango\Carcara\Model\Database\Table;
    use Candango\Carcara\Model\Configuration;

    abstract class AbstractDatabaseLoader implements DatabaseLoader
    {
        const MYSQL = "mysql";

        const PGSQL = "pgsql";

        /**
         * Dao Factory Connection
         *
         * @var \PDO
         */
        private $conn;


        /**
         * @var Configuration
         */
        private $conf;

        private $tables = array();

        public function __construct($conf)
        {
            $this->setConf($conf);
        }

        /**
         * @param Configuration $conf
         * @return MysqlDatabaseLoader|null
         */
        public static function getLoader(Configuration $conf)
        {
            switch ($conf->getType()) {
                case(self::MYSQL):
                    return new MysqlDatabaseLoader($conf);
                    break;
                case(self::PGSQL):
                    //return new PgsqlDatabaseLoader();
                    break;
            }

            return null;
        }

        public function getConnection()
        {
            return $this->conn;
        }

        public function setConnection($conn)
        {
            $this->conn = $conn;
        }

        /**
         * Set the pdo connection to null in order to get the connection closed
         */
        public function disconnect()
        {
            $this->conn = null;
        }

        /**
         * @return Configuration
         */
        public function getConf()
        {
            return $this->conf;
        }

        /**
         * @param Configuration $conf
         */
        public function setConf(Configuration $conf)
        {
            $this->conf = $conf;
        }

        /**
         *
         * @return void
         */
        public function doLoad()
        {
            $this->loadTables();

            foreach ($this->getTables() as $table){
                $this->loadFields($table);
            }
        }

        protected abstract function loadTables();

        protected abstract function loadFields(Table $table);

        /**
         *
         * @param Table $table
         */
        protected function addTable(Table $table)
        {
            $this->tables[] = $table;
        }

        /**
         * @return array of Table
         */
        public function getTables()
        {
            return $this->tables;
        }
    }
}
