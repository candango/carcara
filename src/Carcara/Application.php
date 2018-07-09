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
    use CLIFramework\Application as CLIApplication;

    class Application extends CLIApplication
    {

        const NAME = "Carcara";

        const VERSION = "0.0.1";

        public function option($opts)
        {
            /*$opts->add('v|verbose', 'verbose message');
            $opts->add('path:', 'required option with a value.');
            $opts->add('path?', 'optional option with a value');
            $opts->add('path+', 'multiple value option.');*/
        }

        public function init()
        {
            parent::init();
            $this->command( "list", "Candango\Carcara\Commands\ListCommand");
            //$this->command( 'foo', '\YourApp\Command\FooCommand' );
            //$this->command( 'bar' );    // initialize with \YourApp\Command\BarCommand
        }

    }
}
