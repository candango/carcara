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

    use Candango\Carcara\Conf;

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
         * @return Conf
         */
        public function getConf();

        /**
         * @param Conf $conf
         * @return mixed
         */
        public function setConf(Conf $conf);

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
