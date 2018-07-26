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

    class SmartyInABox
    {
        /**
         * The smarty instance boxed for the execution.
         *
         * @var \Smarty
         */
        private static $smarty;

        private function __construct()
        {
        }

        /**
         * Return a unique smarty instance for the current execution.
         *
         * The smarty template directory will be set in the instance.
         *
         * @return \Smarty
         */
        public static function getInstance()
        {
            if (is_null(self::$smarty)) {
                self::$smarty = new \Smarty();
                self::$smarty->setTemplateDir(realpath(
                    str_replace("#", DIRECTORY_SEPARATOR, sprintf(
                    "%s#..#..#template", dirname(__FILE__)))));
                $pluginsDir = self::$smarty->getPluginsDir();
                $pluginsDir[] = str_replace("#", DIRECTORY_SEPARATOR,
                    sprintf("%s#plugins#smarty#", dirname(__FILE__)));
                self::$smarty->setPluginsDir($pluginsDir);
            }
            return self::$smarty;
        }

        /**
         * Fetch the template result with the smarty instance boxed for the
         * execution.
         *
         * @param $template
         * @return string
         * @throws \Exception
         * @throws \SmartyException
         */
        public static function fetch($template)
        {
            $result = self::getInstance()->fetch($template);
            if (file_exists(self::getInstance()->getCompileDir())) {
                File::delete(self::getInstance()->getCompileDir());
            }
            return $result;
        }
    }
}
