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
    use Candango\Carcara\Model\Configuration;

    interface DatabaseLoader
    {


        /**
         * @throws \PDOException
         * @return void
         */
        public function connect();

        public function getConnection();

        public function setConnection($conn);

        public function disconnect();

        /**
         * @return Configuration
         */
        public function getConf();

        /**
         * @param Configuration $conf
         * @return mixed
         */
        public function setConf(Configuration $conf);

        /**
         * @return void
         */
        public function doLoad();

        /**
         * @return array of Table
         */
        public function getTables();
    }
}
