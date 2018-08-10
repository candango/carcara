<?php
/**
 * Carcara (http://carcara.candango.org)
 *
 * @link      http://github.com/candango/carcara
 * @copyright Copyright (c) 2018 Flavio Garcia
 * @license   https://www.apache.org/licenses/LICENSE-2.0  Apache-2.0
 */

namespace Candango\Carcara\Engine\Pgsql
{

    use Candango\Carcara\Engine\AbstractDatabaseLoader;
    use Candango\Carcara\Model\Database\Field;
    use Candango\Carcara\Model\Database\Table;

    class PgsqlDatabaseLoader extends AbstractDatabaseLoader {

        /**
         * @throws \PDOException
         * @return void
         */
        public function connect()
        {
            $conf = $this->getConf();
            $dsn = "pgsql:host=" . $conf->getHost() . ";dbname=" .
                $conf->getDatabase();
            try {
                $options = [];
                $conn = new \PDO($dsn, $conf->getUser(), $conf->getPassword(),
                    $options);
            }
            catch (\PDOException $e)
            {
                throw $e;
            }
            $this->setConnection($conn);
        }

        protected function loadTables() {
            $sth = $this->getConnection()->prepare(
                "SELECT table_schema, table_name " .
                "FROM information_schema.tables" .
                " WHERE table_type='BASE TABLE' and not table_schema in " .
                "('pg_catalog', 'information_schema');");
            $sth->execute();
            while ($row = $sth->fetch(\PDO::FETCH_NUM)) {
                $table = new Table();
                $table->setSchema($row[0]);
                $table->setName($row[1]);
                $this->addTable($table);
            }
        }

        protected function loadFields(Table $table)
        {
            $sql = sprintf("SELECT c.column_name, c.data_type, " .
                "c.column_default  " .
                " FROM information_schema.columns c " .
                " WHERE c.table_schema='%s' and  c.table_name='%s';",
                $table->getSchema(), $table->getName());
            $sth = $this->getConnection()->prepare($sql);
            $sth->execute();
            while ($row = $sth->fetch(\PDO::FETCH_ASSOC)) {
                $field = new Field();
                $field->setName($row['column_name']);
                $field->setType($row['data_type']);

                if($row['column_default']) {
                    if (substr($row['column_default'], 0, 7) == "nextval") {
                        $field->setSerial(false);
                    }
                }

                $sql1 = sprintf("SELECT tc.constraint_name," .
                    " tc.constraint_type" .
                    " FROM information_schema.table_constraints tc" .
                    " INNER JOIN information_schema.constraint_column_usage" .
                    " ccu ON tc.constraint_name = ccu.constraint_name" .
                    " WHERE  tc.table_schema='%s' AND" .
                    " tc.table_name='%s' AND" .
                    " ccu.column_name = '%s' AND" .
                    " tc.constraint_type='PRIMARY KEY';", $table->getSchema(),
                    $table->getName(), $field->getName());

                $sth1 = $this->getConnection()->prepare($sql1);
                $sth1->execute();

                while ($row = $sth1->fetch(\PDO::FETCH_ASSOC)) {
                    $field->setPk(true);
                }
                $table->addField($field);
            }
        }
    }
}
