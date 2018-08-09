<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara
{

    abstract class AbstractCommand implements Command
    {

        public function getName()
        {
            $className = str_replace("Command", "", str_replace(
                "Candango\\Carcara\\Commands\\", "", get_class($this)));
            return Lexicon::getAttributeName($className);
        }

        public function getOperands()
        {
            return [];
        }
    }

}
