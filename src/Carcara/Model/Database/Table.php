<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Model\Database
{
    class Table
    {
        /**
         * Table name
         *
         * @var string
         */
        private $name;

        /**
         * Table fields
         *
         * @var array
         */
        private $fields = array();

        /**
         * Table name getter
         *
         * @return string
         */
        public function getName()
        {
            return $this->name;
        }

        /**
         * Table name setter
         *
         * @param string $name
         */
        public function setName($name)
        {
            $this->name = $name;
        }

        /**
         * Add a new field to the table
         *
         * @param $field
         */
        public function addField($field)
        {
            $this->fields[] = $field;
        }

        /**
         * Table fields getter
         *
         * @return array
         */
        public function getField()
        {
            return $this->fields;
        }

        /**
         * Table fields setter
         *
         * @param string $fields
         */
        public function setFields($fields)
        {
            $this->fields = $fields;
        }
    }
}
