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
     * Test valid and invalid conf types.
     *
     * Valid types are mysql and pgsql by now.
     *
     * Anything outside from valid types will throw an exception with the
     * message as "Invalid type".
     */
    public function testValidAndInvalidTypes()
    {
        $conf = new Conf();
        try {
            $conf->setType(Conf::MYSQL);
            $this->assertEquals("mysql", $conf->getType());
            $conf->setType(Conf::PGSQL);
            $this->assertEquals("pgsql", $conf->getType());
            $conf->setType("invalid");
            # Failing the test if invalid is correct
            $this->assertEquals("no valid", $conf->getType());
        } catch (\Exception $e) {
            $this->assertEquals("Invalid type.", $e->getMessage());
        }

    }

    /**
     * Test getDsn method.
     *
     * The dsn string returned will vary according the type set in the
     * configuration object.
     */
    public function testGetDsn()
    {
        $mysqlDsn = "mysql:host=localhost;dbname=database_name";
        $pgsqlDsn = "pgsql:host=localhost;dbname=database_name";

        $conf = new Conf();
        $conf->setType(Conf::MYSQL);
        $conf->setHost("localhost");
        $conf->setDatabase("database_name");
        $this->assertEquals($mysqlDsn, $conf->getDsn());

        $conf->setType(Conf::PGSQL);
        $this->assertEquals($pgsqlDsn, $conf->getDsn());
    }
}
