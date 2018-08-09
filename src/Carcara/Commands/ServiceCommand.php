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

    use Candango\Carcara\AbstractCommand;
    use Candango\Carcara\Model\Conf;
    use Candango\Carcara\SmartyInABox;

    class ServiceCommand extends AbstractCommand
    {

        public function brief()
        {
            return 'List all commands.';
        }

        function options($opts)
        {
            // command options
        }

        public function run($getopt)
        {
            $conf = new Conf();
            $confs = array();

            try {
                $it = new \RecursiveDirectoryIterator($conf->getConfDir(),
                    \FilesystemIterator::SKIP_DOTS);

                foreach (new \RecursiveIteratorIterator($it, 1) as $child) {
                    $name = explode("_", $child->getBaseName())[0];
                    $filePath = "" . $child;
                    $data = include($filePath);
                    $confs[] = Conf::fromData($name, $data);
                }
            } catch (\UnexpectedValueException $e) {}

            SmartyInABox::getInstance()->assign("confs", $confs);

            echo SmartyInABox::fetch("list.tpl");

        }
    }
}
