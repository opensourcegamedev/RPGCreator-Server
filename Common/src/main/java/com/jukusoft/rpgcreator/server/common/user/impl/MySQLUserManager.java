package com.jukusoft.rpgcreator.server.common.user.impl;

import com.jukusoft.rpgcreator.server.common.database.mysql.MySQLServer;
import com.jukusoft.rpgcreator.server.common.user.User;
import com.jukusoft.rpgcreator.server.common.user.UserManager;

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
    public User findUserByName(String username) {
        try {
            ResultSet rs = db.getRow("SELECT * FROM `" + db.getPrefix() + "user` WHERE `username` = ?; ", username);

            if (rs == null) {
                //user doesnt exists
                return null;
            }

            return User.createFromDB(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    @Override
    public User findUserByMail(String mail) {
        try {
            ResultSet rs = db.getRow("SELECT * FROM `" + db.getPrefix() + "user` WHERE `mail` = ?; ", mail);

            if (rs == null) {
                //user doesnt exists
                return null;
            }

            return User.createFromDB(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            return null;
        }
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
