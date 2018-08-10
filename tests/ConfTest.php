<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

use PHPUnit\Framework\TestCase;
use Candango\Carcara\Model\Conf;

chdir(dirname(__FILE__));

/**
 * ConfTest - ConfTest.php
 *
 * Tests case covering the ConfTest class.
 *
 * @category   tests
 * @package    tests.carcara
 * @author     Flavio Garcia <piraz at candango.org>
 * @since
 * @covers     \Candango\Carcara\Factory
 */
final class ConfTest extends TestCase
{

    public function testGettersAndSetters()
    {
        $expectedValue = "A Value";
        $expectedType = "pgsql";

        $conf = new Conf();

        // Testing defaults
        $this->assertEquals("default", $conf->getName());
        $this->assertEquals("mysql", $conf->getType());
        $this->assertEquals("localhost", $conf->getHost());

        $conf->setName($expectedValue);
        $conf->setType($expectedType);
        $conf->setHost($expectedValue);
        $conf->setDatabase($expectedValue);
        $conf->setSchema($expectedValue);
        $conf->setUser($expectedValue);
        $conf->setPassword($expectedValue);

        $this->assertEquals($expectedValue, $conf->getName());
        $this->assertEquals($expectedType, $conf->getType());
        $this->assertEquals($expectedValue, $conf->getHost());
        $this->assertEquals($expectedValue, $conf->getDatabase());
        $this->assertEquals($expectedValue, $conf->getSchema());
        $this->assertEquals($expectedValue, $conf->getUser());
        $this->assertEquals($expectedValue, $conf->getPassword());
    }

    /**
     *
     */
    public function testValidAndInvalidTypes()
    {
        $conf = new Conf();
        try {
            $conf->setType("mysql");
            $this->assertEquals("mysql", $conf->getType());
            $conf->setType("pgsql");
            $this->assertEquals("pgsql", $conf->getType());
            $conf->setType("invalid");
            # Failing the test if invalid is correct
            $this->assertEquals("no valid", $conf->getType());
        } catch (\Exception $e) {
            $this->assertEquals("Invalid type.", $e->getMessage());
        }

    }
}
