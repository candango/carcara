<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara
{
    use Candango\Carcara\Model\Conf;

    abstract class Factory {

        public static function fetchDao($name)
        {
            $conf = Conf::fromData();
        }

        public static function getConf($name)
        {
            $conf = new Conf($name);
            $data = include $conf->getFilePath();
            $conf->setData($data);
            return $conf;
        }

        /**
         * @param $conf Conf
         */
        public static function getFromConf($conf)
        {

        }

    }
}
