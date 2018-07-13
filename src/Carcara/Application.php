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
    use GetOpt\ArgumentException;
    use GetOpt\ArgumentException\Missing;

    define('NAME', 'carcara');
    define('VERSION', '0.0.1');
    define("Candango\\Carcara\\ROOT_PATH", realpath(dirname(__FILE__) .
        DIRECTORY_SEPARATOR . ".." . DIRECTORY_SEPARATOR . ".."));

    define("Candango\\Carcara\\TPL_DIR", ROOT_PATH . DIRECTORY_SEPARATOR .
        "template");

    class Application
    {
        public static function run($argv) {
            if (PHP_SAPI != "cli") {
                exit(2);
            }

            $getopt = new GetOpt();

            self::addOptions($getopt);
            self::addCommands($getopt);

            // process arguments and catch user errors
            try {
                try {
                    $getopt->process();
                } catch (Missing $exception) {
                    // catch missing exceptions if help is requested
                    if (!$getopt->getOption('help')) {
                        throw $exception;
                    }
                }
            } catch (ArgumentException $exception) {
                file_put_contents('php://stderr', $exception->getMessage() . PHP_EOL);
                echo PHP_EOL . $getopt->getHelpText();
                exit;
            }

            // show version and quit
            if ($getopt->getOption('version')) {
                echo sprintf("%s version \"%s\"" . PHP_EOL, NAME, VERSION);
                echo sprintf("PHP version (%s)" . PHP_EOL, phpversion());
                exit;
            }

            $command = $getopt->getCommand();

            if (!$command || $getopt->getOption('help')) {
                echo $getopt->getHelpText();
                exit;
            }

            // do something with the command - example:
            $class = $command->getHandler();
            $command = new $class();
            $command->run($getopt);
            exit;
        }

        private static function addOptions($getopt) {
            // define common options
            $getopt->addOptions([
                Option::create(
                    "v",
                    'version',
                    GetOpt::NO_ARGUMENT)->setDescription(
                    'Show product version information and exit'
                ),
                Option::create(
                    '?',
                    'help',
                    GetOpt::NO_ARGUMENT)->setDescription(
                    'Show product help and exit'
                )
            ]);
        }


        private static function addCommands($getopt) {
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
                    $cmdsNamespace . "DaoCommand"
                )->setDescription("Switch to dao commands")
            );

            $getopt->addCommand(
                Command::create(
                    "service",
                    $cmdsNamespace . "CreateCommand"
                )->setDescription("Switch to service commands")
            );
        }

    }

}
