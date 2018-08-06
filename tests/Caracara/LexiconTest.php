<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */


require_once dirname(__FILE__) . DIRECTORY_SEPARATOR .
    "../../src/Carcara/Model/Database/Table.php";
require_once dirname(__FILE__) . DIRECTORY_SEPARATOR .
    "../../src/Carcara/Lexicon.php";

use PHPUnit\Framework\TestCase;
use Candango\Carcara\Lexicon;
use Candango\Carcara\Model\Database\Table;

/**
 * LexiconTest - LexiconTest.php
 *
 * Tests case covering the Lexicon class.
 *
 * @category   tests
 * @package    tests.carcara
 * @author     Flavio Garcia <piraz at candango.org>
 * @since      7723d9f7387f84ba21d64a8d6cba7412cbf8989e
 * @covers     \Candango\Carcara\Lexicon
 */
final class LexiconTest extends TestCase
{

    /**
     * Tests the lexicon get entity name method.
     *
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
        $this->assertEquals($entityName,
            Lexicon::getEntityName($tableNameWithSpace));
    }

    /**
     * Tests the lexicon get attribute name method.
     *
     * Same as get entity name but the first letter or the first part from the
     * table or field will be returned with a lower case.
     *
     * A table name as "a_table_name" or "a_table name" will be returned as
     * aTableName.
     */
    public function testGetAttributeName()
    {
        $tableName = "a_table_name";
        $attributeName = "aTableName";
        $this->assertEquals($attributeName,
            Lexicon::getAttributeName($tableName));

        $tableNameWithSpace = "a_table_name";
        $this->assertEquals($attributeName,
            Lexicon::getAttributeName($tableNameWithSpace));
    }

    public function testGetTableEntityName()
    {
        $table = new Table();
        $table->setName("a_table_name");

        $entityName = "ATableName";

        $this->assertEquals($entityName, Lexicon::getTableEntityName($table));
    }
}
