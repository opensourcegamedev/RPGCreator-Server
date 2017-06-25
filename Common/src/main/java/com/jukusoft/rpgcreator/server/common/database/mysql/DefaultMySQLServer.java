package com.jukusoft.rpgcreator.server.common.database.mysql;

import java.sql.*;
import java.util.Properties;

/**
 * Created by Justin on 26.06.2017.
 */
public class DefaultMySQLServer implements MySQLServer {

    /**
    * mysql connection
    */
    protected Connection conn = null;

    public DefaultMySQLServer () {
        //try to load the mysql driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public void connect (String ip, int port, String user, String password, String database) throws SQLException {
        //create the properties list with user, password and autoreconnect
        Properties props = new Properties();
        props.put("user", user);
        props.put("password", password);
        props.put("autoReconnect", "true");

        //try to connect to the database
        this.conn = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database, props);
    }

    @Override
    public ResultSet query(String sqlstatement) throws SQLException {
        //create a statement to execute
        Statement stmt = this.conn.createStatement();
        ResultSet rs = stmt.executeQuery(sqlstatement);

        stmt.close();

        return rs;
    }

    @Override
    public void disconnect() {
        if (this.conn != null) {
            try {
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            this.conn = null;
        }
    }

}
