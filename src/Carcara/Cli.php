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
    class Cli
    {
        /**
         * Reads input from the user.
         *
         * From the project https://github.com/travist/cliphp
         *
         * @return string - The user entered input.
         */
        public static function read() {
            $fp = fopen("php://stdin", "r");
            $in = fgets($fp, 4094);
            fclose($fp);
            return trim($in);
        }

        /**
         * Reads silent input from the user.  Good for passwords, etc.
         *
         * Should work on UNIX/DOS
         * http://blogs.sitepoint.com/interactive-cli-password-prompt-in-php/
         *
         * From the project: https://github.com/travist/cliphp
         *
         * @param string - The prompt to give to the user to enter in their data.
         * @return string - The user entered input.
         */
        public static function readSilent($prompt) {
            if (preg_match('/^win/i', PHP_OS)) {
                $vbscript = sys_get_temp_dir() . 'prompt_password.vbs';
                file_put_contents(
                    $vbscript, 'wscript.echo(InputBox("'
                    . addslashes($prompt)
                    . '", "", "password here"))');
                $command = "cscript //nologo " . escapeshellarg($vbscript);
                $in = rtrim(shell_exec($command));
                unlink($vbscript);
            } else {
                $command = "/usr/bin/env bash -c 'echo OK'";
                if (rtrim(shell_exec($command)) !== 'OK') {
                    trigger_error("Can't invoke bash");
                    return;
                }
                $command = "/usr/bin/env bash -c 'read -s -p \""
                    . addslashes($prompt)
                    . "\" mypassword && echo \$mypassword'";
                $in = rtrim(shell_exec($command));
            }
            return $in;
        }
    }
}
