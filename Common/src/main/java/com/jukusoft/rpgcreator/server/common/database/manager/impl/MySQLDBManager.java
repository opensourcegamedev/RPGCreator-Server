package com.jukusoft.rpgcreator.server.common.database.manager.impl;

import com.jukusoft.rpgcreator.server.common.database.manager.DBManager;
import com.jukusoft.rpgcreator.server.common.database.mysql.MySQLServer;
import com.jukusoft.rpgcreator.server.common.utils.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Justin on 26.06.2017.
 */
public class MySQLDBManager implements DBManager {

    protected MySQLServer server = null;

    public MySQLDBManager (MySQLServer server) {
        this.server = server;
    }

    @Override
    public void init() {
        System.out.println("check versions table.");

        //create versions table, if neccessary
        try {
            server.execute("CREATE TABLE IF NOT EXISTS `" + this.server.getPrefix() + "version` (\n" +
                    "  `name` varchar(255) NOT NULL,\n" +
                    "  `build_number` int(10) NOT NULL DEFAULT '1',\n" +
                    "  PRIMARY KEY (`name`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1;");

            server.execute("INSERT INTO `" + this.server.getPrefix() + "version` (" +
                    "`name`, `build_number`" +
                    ") VALUES (\n" +
                    "'DATABASE_VERSION', 1" +
                    ") ON DUPLICATE KEY UPDATE `name` = 'DATABASE_VERSION';");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public boolean check() {
        //list all tables in database
        List<String> tables = this.server.listTables();

        //first check all tables with mysql CHECK TABLES, if they are corrupt
        for (String tableName : tables) {
            if (!this.server.checkTable(tableName)) {
                System.err.println("mysql check of table '" + tableName + "' failed! MySQL message: " + this.server.getLastCheckResult());
                return false;
            }
        }

        //check, if all required tables exists
        List<String> lines = null;
        try {
            lines = FileUtils.readLines("./data/mysql/versions/ver_" + getCurrentDBVersion() + "/requiredTables.lst", StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        //iterate through all required tables
        for (String tableName : lines) {
            System.out.println("check, if required table exists: " + tableName);

            //add prefix to table name
            tableName = this.server.getPrefix() + tableName;

            //check, if table is in list
            if (!tables.contains(tableName)) {
                System.err.println("Could not found table '" + tableName + "' in database.");
                return false;
            }
        }

        return true;
    }

    @Override
    public int getCurrentDBVersion() {
        ResultSet rs = null;

        try {
            rs = this.server.getRow("SELECT * FROM `" + this.server.getPrefix() + "version` WHERE `name` = 'DATABASE_VERSION'; ");

            return rs.getInt("build_number");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        throw new IllegalStateException("cannot get current database version.");
    }

    @Override
    public int getNewestVersion() {
        try {
            String line = FileUtils.readFile("./data/mysql/newestVersion.db", StandardCharsets.UTF_8);

            int newestVersion = Integer.parseInt(line);

            return newestVersion;
        } catch (IOException e) {
            System.err.println("./data/mysql/newestVersion.db is corrupt!");

            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean repair() {
        return false;
    }

    @Override
    public void optimizeAllTables() {
        //list all tables first
        List<String> tables = this.server.listTables();

        for (String tableName : tables) {
            System.out.println("optimize database table '" + tableName + "'...");

            //optimize table
            this.server.optimize(tableName);
        }
    }

    @Override
    public boolean isUpgradeRequired() {
        return false;
    }

    @Override
    public boolean upgrade() {
        return false;
    }

}
