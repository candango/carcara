<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Engine\Mysql {

    use Candango\Carcara\Engine\AbstractDaoGenerator;
    use Candango\Carcara\Lexicon;
    use Candango\Carcara\SmartyInABox;

    class MysqlDaoGenerator extends AbstractDaoGenerator
    {

        public function generateDaoFactories()
        {
            $factories = parent::generateDaoFactories();

            $abstractDaoFactoryPath = DIRECTORY_SEPARATOR .
                Lexicon::getEntityName(
                    $this->getLoader()->getConf()->getIdentifier()
                ) . "MysqlDaoFactory.php";
            $factories["mysql"] = array(
                "path" => $abstractDaoFactoryPath,
                "code" => SmartyInABox::fetch(
                    "mysql/dao/mysql_dao_factory.tpl"
                )
            );

            return $factories;
        }
    }
}
