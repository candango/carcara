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


    use Candango\Carcara\Model\Database\Table;
    use Candango\Carcara\Model\DataSource\Config;

    class AbstractDatabaseLoader implements DatabaseLoader
    {
        const MYSQL = "mysql";

        const PGSQL = "pgsql";


        /**
         * @param Config $config
         * @return void
         */
        public function connect(Config $config){

        }

        public function getConnection(){

        }

        public function setConnection($conn){

        }

        public function disconnect(){

        }

        /**
         * @return Config
         */
        public function getConfiguration(){

        }


        /**
         * @param Config $config
         * @return void
         */
        public function doLoad(Config $config){

        }

        /**
         * @return array of Table
         */
        public function getTables(){

        }
    }
}
