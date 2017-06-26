package com.jukusoft.rpgcreator.server.common.database.manager;

import com.jukusoft.rpgcreator.server.common.database.DBManager;
import com.jukusoft.rpgcreator.server.common.database.mysql.MySQLServer;

import java.sql.SQLException;

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
        //create versions table, if neccessary
        try {
            server.query("CREATE TABLE IF NOT EXISTS `" + this.server.getPrefix() + "version` (\n" +
                    "  `name` varchar(255) NOT NULL,\n" +
                    "  `build_number` int(10) NOT NULL DEFAULT '1',\n" +
                    "  PRIMARY KEY (`name`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1;");

            server.query("INSERT INTO `" + this.server.getPrefix() + "version` (" +
                    "`name`, `build_number`" +
                    ") VALUES (\n" +
                    "'DATABASE_VERSION', 1" +
                    ") ON DUPLICATE KEY IGNORE;");
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public boolean check() {
        return false;
    }

    @Override
    public int getCurrentDBVersion() {
        return 0;
    }

    @Override
    public boolean repair() {
        return false;
    }

    @Override
    public void optimizeAllTables() {

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
