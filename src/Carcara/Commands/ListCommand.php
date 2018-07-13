<?php
namespace Candango\Carcara\Commands
{

    class ListCommand {

        public function brief()
        {
            return 'List all commands';
        }

        function init()
        {
            // register your subcommand here ..
        }

        function options($opts)
        {
            // command options
        }

        function execute()
        {
            $logger = $this->logger;

            $logger->info('execute');
            $logger->error('error');

            $input = $this->ask('Please type something');
        }
    }
}
