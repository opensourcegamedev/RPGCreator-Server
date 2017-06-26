package com.jukusoft.rpgcreator.server.common.api.manager.impl;

import com.jukusoft.rpgcreator.server.common.api.manager.UserManager;
import com.jukusoft.rpgcreator.server.common.database.mysql.MySQLServer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Justin on 26.06.2017.
 */
public class MySQLUserManager implements UserManager {

    /**
    * database connection instance
    */
    protected MySQLServer db = null;

    public MySQLUserManager (MySQLServer server) {
        this.db = server;
    }

    @Override
    public int countRegisteredUsers() {
        try {
            ResultSet rs = db.getRow("SELECT COUNT(*) as `count_users` FROM `" + db.getPrefix() + "user` WHERE `activated` = '1'; ");

            return rs.getInt("count_users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException("Couldnt count users.");
    }

    @Override
    public int countOnlineUsers() {
        try {
            ResultSet rs = db.getRow("SELECT COUNT(*) as `count_users` FROM `" + db.getPrefix() + "user` WHERE `online` = '1' AND `activated` = '1'; ");

            return rs.getInt("count_users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException("Couldnt count users.");
    }

}
