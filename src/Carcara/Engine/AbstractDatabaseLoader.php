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
    use Candango\Carcara\Model\DataSource\Configuration;

    class AbstractDatabaseLoader implements DatabaseLoader
    {
        const MYSQL = "mysql";

        const PGSQL = "pgsql";


        /**
         * @param Configuration $config
         * @return void
         */
        public function connect(Configuration $config){

        }

        public function getConnection(){

        }

        public function setConnection($conn){

        }

        public function disconnect(){

        }

        /**
         * @return Configuration
         */
        public function getConfiguration(){

        }


        /**
         * @param Configuration $config
         * @return void
         */
        public function doLoad(Configuration $config){

        }

        /**
         * @return array of Table
         */
        public function getTables(){

        }
    }
}
