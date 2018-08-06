<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

require_once dirname(__FILE__) . DIRECTORY_SEPARATOR .
    "../../src/Carcara/Lexicon.php";

use PHPUnit\Framework\TestCase;
use Candango\Carcara\Lexicon;

/**
 * LexiconTest - LexiconTest.php
 *
 * Tests case covering the Lexicon class.
 *
 * @category   tests
 * @package    tests.carcara
 * @author     Flavio Garcia <piraz at candango.org>
 * @since
 * @covers     \Candango\Carcara\Lexicon
 */
final class LexiconTest extends TestCase
{

    /**
     * The entity name is a camel case name where the parts of name are any
     * string separated by underscore or space.
     *
     * The space isn't something we should be concerned here but as table and
     * field names won't be named with spaces but that could be useful as the
     * project grows.
     *
     * A table name as "a_table_name" or "a_table name" will be returned as
     * ATableName.
     */
    public function testGetEntityName()
    {
        $tableName = "a_table_name";
        $entityName = "ATableName";
        $this->assertEquals($entityName, Lexicon::getEntityName($tableName));

        $tableNameWithSpace = "a_table name";
        $entityNameWithSpace = "ATableName";
        $this->assertEquals($entityNameWithSpace,
            Lexicon::getEntityName($tableNameWithSpace));
    }

}
