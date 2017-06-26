package com.jukusoft.rpgcreator.server.mancenter;

import com.jukusoft.rpgcreator.server.common.database.config.MySQLConfig;
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

    public static final String RPGCREATOR_SERVER_VERSION = "0.0.1 pre-alpha";

    public static void main (String[] args) {
        int port = 1234;

        //create vertx.io server
        //NetServer netServer = VertxIOServerFactory.createVertxServer(port);

        //welcome message
        System.out.println("################################################################");
        System.out.println("# RPGCreator Management Server - Version " + RPGCREATOR_SERVER_VERSION + "");
        System.out.println("################################################################\n\n");

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

        System.out.println("connection to mysql server successful.");

        System.out.println("try to start management server on port " + port + "now.");

        //create new ManCenter server and set port
        ManCenterServer server = new ManCenterServer();
        server.setPort(port);

        //start server
        server.start();
    }

}
