<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

use PHPUnit\Framework\TestCase;
use Candango\Carcara\Application;

/**
 * SmartyInABoxTest - SmartyInABoxTest.php
 *
 * Tests case covering the SmartyInABoxTest class.
 *
 * @category   tests
 * @package    tests.carcara
 * @author     Flavio Garcia <piraz at candango.org>
 * @since
 * @covers     \Candango\Carcara\Application
 */
final class ApplicationTest extends TestCase
{

    /**
     * Test get_all_commands function.
     *
     * Will return an array with all commands from Candango\Carcara\Commands
     * namespace. The array will be organized alphabetically.
     */
    public function testGetAllCommands()
    {
        new Application();
        $baseNS = "Candango\\Carcara\\Commands\\";
        $commands = Candango\Carcara\get_all_commands();

        $this->assertEquals(3, count($commands));

        $this->assertEquals($baseNS . "ConfCommand",get_class($commands[0]));
        $this->assertEquals($baseNS . "DaoCommand",get_class($commands[1]));
        $this->assertEquals($baseNS . "ListCommand",get_class($commands[2]));
    }
}
