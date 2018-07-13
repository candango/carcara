<?php
namespace Candango\Carcara\Commands
{

    use Candango\Carcara\Model\DataSource\Config;

    class ListCommand {

        public function brief()
        {
            return 'List all commands';
        }

        public function name()
        {
            return "list";
        }

        function options($opts)
        {
            // command options
        }

        function run()
        {
            $config = new Config();
            $it = new \RecursiveDirectoryIterator($config->getConfigDir(),
                \FilesystemIterator::SKIP_DOTS);
            $configs = array();
            foreach (new \RecursiveIteratorIterator($it, 1) as $child) {
                $name = explode("_", $child->getBaseName())[0];
                $filePath = "" . $child;
                $data = include($filePath);
                $configs[] = Config::fromData($name, $data);
            }

            foreach($configs as $item) {
                print_r($item);
            }
        }
    }
}
