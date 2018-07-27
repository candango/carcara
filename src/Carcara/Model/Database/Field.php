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

    use Candango\Carcara\Lexicon;

    class Field
    {
        /**
         * Field name
         *
         * @var string
         */
        private $name;

        /**
         * Field type
         *
         * @var string
         */
        private $type;

        /**
         * Field primary key flag
         *
         * @var bool
         */
        private $pk = false;

        /**
         * Field serial flag
         *
         * @var bool
         */
        private $serial = false;

        /**
         * Field name getter
         *
         * @return string
         */
        public function getName()
        {
            return $this->name;
        }

        /**
         * Field name setter
         *
         * @param string $name
         */
        public function setName($name)
        {
            $this->name = $name;
        }

        /**
         * Field type getter
         *
         * @return string
         */
        public function getType()
        {
            return $this->type;
        }

        /**
         * Field type setter
         *
         * @param string $type
         */
        public function setType($type)
        {
            $this->type = $type;
        }

        /**
         * Returns if the field is primary key
         *
         * @return bool
         */
        public function isPk()
        {
            return $this->pk;
        }

        /**
         * Field pk setter
         *
         * @param bool $pk
         */
        public function setPk($pk)
        {
            $this->pk = $pk;
        }

        /**
         * Returns if the field is serial
         *
         * @return bool
         */
        public function isSerial()
        {
            return $this->serial;
        }

        /**
         * Field serial setter
         *
         * @param bool $serial
         */
        public function setSerial($serial)
        {
            $this->serial = $serial;
        }

        public function getEntityName()
        {
            return Lexicon::getEntityName($this->getName());
        }

        public function getAttributeName()
        {
            return Lexicon::getAttributeName($this->getName());
        }
    }
}
