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
    class File
    {
        /**
         * Delete the path and all children if path is a directory
         *
         * @param string $path Path to be deleted
         */
        public static function delete($path)
        {
            if (is_dir($path)) {
                $it = new \RecursiveDirectoryIterator($path);
                foreach (new \RecursiveIteratorIterator($it, 1) as $child) {
                    $pName = "" . $child;
                    if ($child->getBaseName() == "." ||
                        $child->getBaseName() == "..") {
                        continue;
                    }
                    if ($child->isDir() && !$child->isLink() &&
                        file_exists($child)) {

                        rmdir($pName);
                    } else {
                        unlink($pName);
                    }
                }
                rmdir($path);
            } else {
                unlink($path);
            }
        }

        /**
         * Write a string in a given file
         *
         * @param string $file The file name
         * @param string $string The string to be writed
         * @throws FileOperationException
         */
        public static function write($file, $string)
        {
            $fp = fopen($file,"w");

            if (!flock($fp,LOCK_EX)) {
                throw new FileOperationException($file,
                    FileOperationException::LOCK_EX_FILE);
            }

            if (!fwrite($fp, $string)) {
                throw new FileOperationException($file,
                    FileOperationException::WRITE_FILE);
            }

            flock($fp,LOCK_UN);
            fclose($fp);
        }

        /**
         * Reads the content of given file
         *
         * @param string $file The file name
         * @return string The file content
         * @throws FileOperationException
         */
        public static function read($file)
        {
            if (@!$fp = fopen($file ,"r")) {
                throw new FileOperationException($file,
                    FileOperationException::OPEN_FILE);
            }

            if (!flock( $fp, LOCK_SH)) {
                throw new FileOperationException($file,
                    FileOperationException::LOCK_FILE);
            }

            $fileCode = fread($fp, filesize($file));

            flock($fp,LOCK_UN);
            fclose($fp);

            return $fileCode;
        }
    }
}
