package org.candango.carcara;

import org.candango.carcara.engine.AbstractDatabaseLoader;
import org.candango.carcara.engine.DaoBuilder;
import org.candango.carcara.engine.DatabaseLoader;
import org.candango.carcara.engine.mysql.MysqlDaoBuilder;
import org.candango.carcara.engine.mysql.MysqlDatabaseConfiguration;

public class TestApp {


    public static void main(String[] args) {

        //MainFrame frame = new MainFrame();

        DatabaseLoader loader = AbstractDatabaseLoader.getLoader(

                AbstractDatabaseLoader.MYSQL_DATABASE);

        MysqlDatabaseConfiguration configuration = (MysqlDatabaseConfiguration) loader.getConfiguration();

        //configuration.setSchema( "intranet" );

        configuration.setHost("localhost");

        configuration.setDatabase("acertofacil");

        configuration.setUser("root");

        configuration.setPassword("");

        loader.connect(configuration);

        loader.doLoad(configuration);

        loader.disconnect();

        DaoBuilder builder = new MysqlDaoBuilder();

        builder.setPath("/Users/Charline/java/classes/");

        builder.build(configuration, loader);

    }

}
