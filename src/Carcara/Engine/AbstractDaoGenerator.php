<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Engine
{

    use Candango\Carcara\Engine\Mysql\MysqlDaoGenerator;
    use Candango\Carcara\Lexicon;
    use Candango\Carcara\Model\Conf;
    use Candango\Carcara\SmartyInABox;

    abstract class AbstractDaoGenerator implements DaoGenerator
    {
        /**
         * @var DatabaseLoader
         */
        private $loader;

        public function __construct(DatabaseLoader $loader)
        {
            $this->setLoader($loader);
            SmartyInABox::getInstance()->assign("tables",
                $this->loader->getTables());
        }

        public function generateDaoFactories()
        {
            $factories = array();

            $abstractDaoFactoryPath = DIRECTORY_SEPARATOR . Lexicon::getEntityName(
                $this->loader->getConf()->getIdentifier()) .
                "AbstractDaoFactory.php";
            $factories['abstract'] = array(
                'path' => $abstractDaoFactoryPath,
                'code' => SmartyInABox::fetch(
                    "common/dao/abstract_dao_factory.tpl"
                )
            );

            return $factories;
        }

        public function generateDtos()
        {
            $dtos = array();
            foreach ($this->getLoader()->getTables() as $table) {
                $identifierName = SmartyInABox::getInstance()->getTemplateVars(
                    "identifierName");
                $tableEntityName = Lexicon::getTableEntityName($table);
                SmartyInABox::getInstance()->assign("table", $table);
                $concreteDtoPath = DIRECTORY_SEPARATOR . $tableEntityName .
                    DIRECTORY_SEPARATOR . $identifierName .  $tableEntityName .
                    "Dto.php";
                $abstractDtoPath = DIRECTORY_SEPARATOR . $tableEntityName .
                    DIRECTORY_SEPARATOR . $identifierName .  $tableEntityName .
                    "AbstractDto.php";
                $dtos[$table->getName()]['concrete'] = array(
                    "path" => $concreteDtoPath,
                    "code" => SmartyInABox::fetch("common/dao/dto.tpl")
                );
                $dtos[$table->getName()]['abstract'] = array(
                    "path" => $abstractDtoPath,
                    "code" => SmartyInABox::fetch("common/dao/abstract_dto.tpl")
                );
            }
            SmartyInABox::getInstance()->clearAssign("table");
            return $dtos;
        }

        public static function getGenerator(DatabaseLoader $loader)
        {
            switch ($loader->getConf()->getType()) {
                case Conf::MYSQL;
                    return new MysqlDaoGenerator($loader);
                    break;
            }
        }

        /**
         * @return DatabaseLoader
         */
        public function getLoader()
        {
            return $this->loader;
        }

        public function setLoader(DatabaseLoader $loader)
        {
            $this->loader = $loader;
        }
    }

}
