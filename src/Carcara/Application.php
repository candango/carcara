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
    use GetOpt\GetOpt;
    use GetOpt\Command;
    use GetOpt\Option;

    define('NAME', 'carcara');
    define('VERSION', '0.0.1');

    class Application
    {
        public static function run($argv) {
            if (PHP_SAPI != "cli") {
                exit(2);
            }
            $getopt = new GetOpt();

            // define common options
            $getopt->addOptions([
                Option::create(
                    null,
                    'version',
                    GetOpt::NO_ARGUMENT)->setDescription(
                        'Show product version information and exit'
                ),
                Option::create(
                    'p',
                    'path',
                    GetOpt::NO_ARGUMENT)->setDescription(
                    'Set path to where the application will run'
                ),
                Option::create(
                    '?',
                    'help',
                    GetOpt::NO_ARGUMENT)->setDescription(
                        'Show product help and exit'
                )
            ]);

            $cmdsNamespace = "Candango\\Carcara\\Commands\\";

            $getopt->addCommand(
                Command::create(
                    "init",
                    $cmdsNamespace . "InitCommand"
                )->setDescription("Create Carcara structure.")
            );

            $getopt->addCommand(
                Command::create(
                    "dao",
                    "Candango\Carcara\Commands\DaoCommand"
                )->setDescription("Switch to dao commands")
            );

            $getopt->addCommand(
                Command::create(
                    "service",
                    "Candango\Carcara\Commands:CreateCommand"
                )->setDescription("Switch to service commands")
            );

            try {
                $getopt->process();
            } catch (\GetOpt\ArgumentException $exception) {
                // do something with this exception
                var_dump($exception);
            }

            // show version and quit
            if ($getopt->getOption('path')) {
                echo sprintf("%s version \"%s\"" . PHP_EOL, NAME, VERSION);
                echo sprintf("PHP version (%s)" . PHP_EOL, phpversion());
                exit;
            }

            $command = $getopt->getCommand();
            if (!$command) {
                echo $getopt->getHelpText();
                exit;
            } else {
                // do something with the command - example:
                $class = $command->getHandler();
                $command = new $class();
                $command->run($getopt);
                exit;
            }

            // show version and quit
            if ($getopt->getOption('version')) {
                echo sprintf("%s version \"%s\"" . PHP_EOL, NAME, VERSION);
                echo sprintf("PHP version (%s)" . PHP_EOL, phpversion());
                exit;
            }

            // show help and quit
            $command = $getopt->getCommand();
            if (!$command || $getopt->getOption('help')) {
                echo $getopt->getHelpText();
                exit;
            }
        }
    }

}
