<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Engine {


    use Candango\Carcara\Engine\Mysql\MysqlDaoBuilder;
    use Candango\Carcara\Model\Conf;

    abstract class AbstractDaoBuilder implements DaoBuilder
    {
        /**
         * @var DatabaseLoader
         */
        private $loader;

        public function __construct(DatabaseLoader $loader)
        {
            $this->setLoader($loader);
        }

        public function build()
        {
        }

        public static function getBuilder(DatabaseLoader $loader) {
            switch ($loader->getConf()->getType()) {
                case Conf::MYSQL;
                    return new MysqlDaoBuilder($loader);
                    break;
            }
        }

        public function getLoader()
        {
            // TODO: Implement getLoader() method.
        }

        public function setLoader(DatabaseLoader $loader)
        {
            $this->loader = $loader;
        }
    }

}
