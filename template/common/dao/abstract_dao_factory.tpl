<?php
use Candango\Carcara\Conf;

/**
 * {$identifierName}DaoFactory - {$identifierName}DaoFactory.php
 * 
 * Abstract dao factory class. This class returns all dao factories found
 * by the engine.
 *
 * Contributor(s): 
 *
 * @category dao
 * @package lib.dao
 * @author Carcara DAO Generator
 * @copyright Put your copyright here.
 * @license Put your copyright here.
 * @version
 */
abstract class {$identifierName}DaoFactory
{
    /**
     * Insert Transaction constant
     *
     * @var integer
     */
    const INSERT = "insert";

    /**
     * Update Transaction constant
     *
     * @var integer
     */
    const UPDATE = "update";

    /**
     * Factory configuration
     *
     * @var Conf
     */
    private $conf;

    /**
     * Dao Factory Connection
     *
     * @var PDO
     */
    private $connection;

    /**
     * {$identifierName}DaoFactory constructor.
     */
    public function __construct(Conf $conf)
    {
        $this->setConf($conf);
        try {
            $this->connection = new PDO($conf->getDsn(), $conf->getUser(),
                $conf->getPassword(), $conf->getPdoOptions());
        } catch (Exception $e) {
            echo "Failed: " . $e->getMessage();
        }
    }

    /**
     * Collection of instances that this factory can hold
     *
     * @var array An array of {{$identifierName}}DaoFactory
     */
    private static $instances = array();

    /**
     * Return one DAO factory instance
     *
     * @param int $whichFactory
     * @param Conf $conf
     * @return {$identifierName}DaoFactory
     */
    public static function getInstance($whichFactory, Conf $conf)
    {
        $factories = array(
            Conf::MYSQL => "{$identifierName}MysqlDaoFactory",
            Conf::PGSQL => "{$identifierName}PgsqlDaoFactory"
        );

        if (isset($factories[ $whichFactory])) {
            if (!isset(self::$instances[$whichFactory])) {
                require_once $conf->getCurrentDaoDir() . DIRECTORY_SEPARATOR .
                    $factories[$whichFactory] . ".php";
                self::$instances[ $whichFactory ] =
                    new $factories[ $whichFactory ]($conf);
            }
            return self::$instances[$whichFactory];
        }
        return null;
    }

    /**
     * Return the factory configuration
     *
     * @returns Conf
     */
    public function getConf()
    {
        return $this->conf;
    }

    /**
     * Set the factory configuration
     *
     * @returns Conf
     */
    public function setConf(Conf $conf)
    {
        return $this->conf = $conf;
    }

    /**
     * Returns the PDO connection object
     *
     * @returns \PDO
     */
    public function getConnection()
    {
        return $this->connection;
    }
{foreach $tables as $table}

    /**
     * Return a new {$identifierName} {$table->getEntityName()} Dao
     *
     * @return {$identifierName}{$table->getEntityName()}Dao
     **/
    public abstract function get{$table->getEntityName()}Dao();
{/foreach}
}
