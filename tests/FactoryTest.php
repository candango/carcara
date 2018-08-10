<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

use PHPUnit\Framework\TestCase;
use Candango\Carcara\Factory;

chdir(dirname(__FILE__));

/**
 * FactoryTest - FactoryTest.php
 *
 * Tests case covering the FactoryTest class.
 *
 * @category   tests
 * @package    tests.carcara
 * @author     Flavio Garcia <piraz at candango.org>
 * @since
 * @covers     \Candango\Carcara\Factory
 */
final class FactoryTest extends TestCase
{

    /**
     * Test get_all_commands function.
     *
     * Will return an array with all commands from Candango\Carcara\Commands
     * namespace. The array will be organized alphabetically.
     */
    public function testGetConf()
    {
        $conf = Factory::getConf("mysql");

        $this->assertEquals("mysql", $conf->getName());
        $this->assertEquals("mysql", $conf->getType());
        $this->assertEquals("localhost", $conf->getHost());
        $this->assertEquals("test", $conf->getDatabase());
        $this->assertEquals("testuser", $conf->getUser());
        $this->assertEquals("testpass", $conf->getPassword());
    }
}
