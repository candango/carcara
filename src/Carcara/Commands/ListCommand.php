<?php
namespace Candango\Carcara\Commands
{

    use function Candango\Carcara\get_all_commands;

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
            get_all_commands();
        }
    }
}
