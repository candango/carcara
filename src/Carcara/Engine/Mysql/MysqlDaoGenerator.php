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

        public function generateDaos()
        {
            $daos = parent::generateDaos();
            foreach ($this->getLoader()->getTables() as $table) {
                $identifierName = SmartyInABox::getInstance()->getTemplateVars(
                    "identifierName");
                $tableEntityName = Lexicon::getTableEntityName($table);
                SmartyInABox::getInstance()->assign("table", $table);
                $mysqlDtoPath = DIRECTORY_SEPARATOR . $tableEntityName .
                    DIRECTORY_SEPARATOR . $identifierName .  $tableEntityName .
                    "AbstractMysqlDao.php";
                $daos[$table->getName()]['abstract mysql DAO'] = array(
                    "path" => $mysqlDtoPath,
                    "code" => SmartyInABox::fetch(
                        "mysql/dao/abstract_mysql_dao.tpl"),
                    "always" => true
                );
            }
            SmartyInABox::getInstance()->clearAssign("table");
            return $daos;
        }
    }
}
