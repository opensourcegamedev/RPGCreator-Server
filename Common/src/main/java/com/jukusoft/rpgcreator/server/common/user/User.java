package com.jukusoft.rpgcreator.server.common.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Justin on 26.06.2017.
 */
public class User {

    protected int userID = 0;
    protected String username = "";
    protected String passwordHash = "";
    protected String mail = "";
    protected Timestamp registered = null;
    protected Timestamp lastLogin = null;
    protected Timestamp lastOnline = null;
    protected boolean online = false;
    protected String lastIP = "";
    protected boolean activated = false;

    protected User () {
        //
    }

    public static User createFromDB (ResultSet rs) throws SQLException {
        //create new user
        User user = new User();

        //fill data from database row
        user.userID = rs.getInt("userID");
        user.username = rs.getString("username");
        user.passwordHash = rs.getString("password");
        user.mail = rs.getString("mail");
        user.registered = rs.getTimestamp("registered");
        user.lastLogin = rs.getTimestamp("last_login");
        user.lastOnline = rs.getTimestamp("lastOnline");
        user.online = rs.getInt("online") == 1 ? true : false;
        user.lastIP = rs.getString("last_ip");
        user.activated = rs.getInt("activated") == 1 ? true : false;

        return user;
    }

}
