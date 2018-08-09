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

    interface DaoGenerator
    {
        public function generateDaoFactories();

        public function generateDtos();

        public function generateDaos();

        /**
         * @return DatabaseLoader
         */
        public function getLoader();

        public function setLoader(DatabaseLoader $loader);
    }

}
