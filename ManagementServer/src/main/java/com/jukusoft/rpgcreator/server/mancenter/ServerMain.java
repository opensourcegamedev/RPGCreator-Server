package com.jukusoft.rpgcreator.server.mancenter;

import com.jukusoft.rpgcreator.server.common.Engine;
import com.jukusoft.rpgcreator.server.common.database.manager.DBManager;
import com.jukusoft.rpgcreator.server.common.database.config.MySQLConfig;
import com.jukusoft.rpgcreator.server.common.database.manager.impl.MySQLDBManager;
import com.jukusoft.rpgcreator.server.common.database.mysql.DefaultMySQLServer;
import com.jukusoft.rpgcreator.server.common.database.mysql.MySQLServer;
import com.jukusoft.rpgcreator.server.mancenter.network.impl.ManCenterServer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Justin on 31.05.2017.
 */
public class ServerMain {

    public static final String RPGCREATOR_SERVER_VERSION = Engine.getVersionString();//"0.0.1 pre-alpha";

    public static void main (String[] args) {
        int port = 1234;

        //create vertx.io server
        //NetServer netServer = VertxIOServerFactory.createVertxServer(port);

        //welcome message
        System.out.println("################################################################");
        System.out.println("# RPGCreator Management Server - Version " + RPGCREATOR_SERVER_VERSION + "");
        System.out.println("################################################################\n");

        System.out.println("load mysql configuration: ./data/config/mysql.cfg\n");

        MySQLConfig mySQLConfig = null;

        try {
            //read database configuration
            mySQLConfig = MySQLConfig.readFromFile(new File("./data/config/mysql.cfg"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("try to connect to mysql server " + mySQLConfig.getDBUrl() + ".");

        //create database connection
        MySQLServer mySQLServer = new DefaultMySQLServer();

        try {
            mySQLServer.connect(mySQLConfig);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("connection to mysql server successful.\n");
        System.out.println("#### DB Manager ####");
        System.out.println("DB Manager checks database now.");

        //create new database manager and initialize database manager
        DBManager dbManager = new MySQLDBManager(mySQLServer);
        dbManager.init();

        System.out.println("current RPG Creator Editor database version: " + dbManager.getCurrentDBVersion());

        //check, if all required tables exists
        if (dbManager.check()) {
            System.out.println("all required database tables exists.");
        } else {
            System.err.println("Not all required tables exists in database, try to repair database now.");

            if (dbManager.repair()) {
                System.err.println("database structure repaired successfully. Proceed now.");
            } else {
                System.err.println("Couldnt repair database structure, shutdown ManCenter server now. Maybe you can find more information in log files.");
                System.exit(1);
            }
        }

        System.out.println("newest available database version: " + dbManager.getNewestVersion());

        if (dbManager.isUpgradeRequired()) {
            //
        }

        //optimize database tables
        dbManager.optimizeAllTables();

        System.out.println("#### END of DB Manager ####\n");

        System.out.println("try to start management server on port " + port + "now.");

        //create new ManCenter server and set port
        ManCenterServer server = new ManCenterServer();
        server.setPort(port);

        //start server
        server.start();
    }

}
