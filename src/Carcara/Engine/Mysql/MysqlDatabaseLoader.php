<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Engine\Mysql {

    use Candango\Carcara\Engine\AbstractDatabaseLoader;
    use Candango\Carcara\Model\Database\Field;
    use Candango\Carcara\Model\Database\Table;

    class MysqlDatabaseLoader extends AbstractDatabaseLoader {

        /**
         * @throws \PDOException
         * @return void
         */
        public function connect()
        {
            $conf = $this->getConf();
            try {
                $conn = new \PDO($conf->getDsn(), $conf->getUser(),
                    $conf->getPassword(), $conf->getPdoOptions());
            }
            catch (\PDOException $e)
            {
                throw $e;
            }
            $this->setConnection($conn);
        }

        protected function loadTables()
        {
            $sth = $this->getConnection()->prepare("SHOW TABLES;");
            $sth->execute();
            while ($row = $sth->fetch(\PDO::FETCH_NUM)) {
                $table = new Table();
                $table->setName($row[0]);
                $this->addTable($table);
            }
        }

        protected function loadFields(Table $table)
        {
            $sql = sprintf("SHOW FIELDS FROM %s;", $table->getName());
            $sth = $this->getConnection()->prepare($sql);
            $sth->execute();
            while ($row = $sth->fetch(\PDO::FETCH_ASSOC)) {
                $field = new Field();
                $field->setName($row['Field']);
                $field->setType($row['Type']);
                if ($row['Key'] == "PRI") {
                    $field->setPk(true);
                }
                $table->addField($field);
            }
        }
    }
}
