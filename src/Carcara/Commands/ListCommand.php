<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Commands
{

    use Candango\Carcara\Command;
    use Candango\Carcara\File;
    use Candango\Carcara\Model\Configuration;

    class ListCommand implements Command
    {

        public function brief()
        {
            return 'List all commands';
        }

        public function getName()
        {
            return "list";
        }

        function options($opts)
        {
            // command options
        }

        function getOperands()
        {
            return [];
        }

        public function run($getopt)
        {
            $config = new Configuration();
            $configs = array();

            try {
                $it = new \RecursiveDirectoryIterator($config->getConfigDir(),
                    \FilesystemIterator::SKIP_DOTS);

                foreach (new \RecursiveIteratorIterator($it, 1) as $child) {
                    $name = explode("_", $child->getBaseName())[0];
                    $filePath = "" . $child;
                    $data = include($filePath);
                    $configs[] = Configuration::fromData($name, $data);
                }
            } catch (\UnexpectedValueException $e) {}

            $smarty = new \Smarty();
            $smarty->assign("configs", $configs);
            $configTplDir =  \Candango\Carcara\TPL_DIR;
            echo $smarty->fetch($configTplDir . DIRECTORY_SEPARATOR .
                "list.tpl");

            File::delete($smarty->getCompileDir());
        }
    }
}
