<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

use PHPUnit\Framework\TestCase;
use Candango\Carcara\SmartyInABox;

/**
 * SmartyInABoxTest - SmartyInABoxTest.php
 *
 * Tests case covering the SmartyInABoxTest class.
 *
 * @category   tests
 * @package    tests.carcara
 * @author     Flavio Garcia <piraz at candango.org>
 * @since
 * @covers     \Candango\Carcara\SmartyInABox
 */
final class SmartyInABoxTest extends TestCase
{

    /**
     * Test getInstance method.
     *
     * Will return a smarty instance that will be unique in the same runtime.
     */
    public function testGetInstance()
    {
        $smarty = SmartyInABox::getInstance();
        $this->assertEquals("Smarty", get_class($smarty));
        $secondSmarty = SmartyInABox::getInstance();
        $this->assertEquals($smarty, $secondSmarty);
    }

    /**
     * Test fetch method.
     *
     * Will return the result from a fetch from the template located at
     * template/tests/fetch_test.tpl.
     */
    public function testFetch()
    {
        $expectedValue = "Fetch template test: a value";
        SmartyInABox::getInstance()->assign("variable", "a value");
        $this->assertEquals($expectedValue,
            SmartyInABox::fetch("tests/fetch_test.tpl"));
    }
}
