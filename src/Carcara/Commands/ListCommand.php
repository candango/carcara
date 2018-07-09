<?php
namespace Candango\Carcara\Commands
{
    use CLIFramework\Command;

    class ListCommand extends Command {

        public function brief()
        {
            return 'List something';
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
