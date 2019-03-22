<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2008-2019 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */
namespace Candango\Carcara
{

    use Candango\Carcara\Model\Database\Table;

    class Lexicon
    {
        /**
         * Returns a camel case formatted entity name.
         *
         * The entity name will be separated by.
         *
         *   - blank space " "
         *   - undescore "_"
         *
         * @param string $entity
         * @return string
         */
        public static function getEntityName($entity)
        {
            $entityName = "";
            $entityX = explode("_", str_replace(" ", "_", $entity));
            foreach ($entityX as $part) {
                $entityName .= ucfirst($part);
            }
            return $entityName;
        }

        /**
         * Returns a camel case formatted attribute name.
         *
         * It is the same as getEntityName but the first letter is changed to
         * lower case.
         *
         * @param string $attribute
         * @param boolean $entityBefore
         * @return string
         */
        public static function getAttributeName(
            $attribute,
            $entityBefore = true
        ) {
            if($entityBefore) {
                $attribute = self::getEntityName($attribute);
            }
            return lcfirst($attribute);
        }

        /**
         * Returns the table entity name.
         *
         * @param Table $table
         * @return string
         */
        public static function getTableEntityName(Table $table)
        {
            return self::getEntityName($table->getName());
        }
    }
}
