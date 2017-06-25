package com.jukusoft.rpgcreator.server.common.database.mysql;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 26.06.2017.
 */
public class DefaultMySQLServer implements MySQLServer {

    /**
    * mysql connection
    */
    protected Connection conn = null;

    /**
    * map with cached prepared statements
    */
    protected Map<String,PreparedStatement> preparedStatementMap = new ConcurrentHashMap<>();

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

        //cache prepared statements
        props.put("cachePrepStmts", "true");
        props.put("useServerPrepStmts", "true");
        props.put("prepStmtCacheSize", "250");
        props.put("prepStmtCacheSqlLimit", 2048);

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
    public PreparedStatement prepare(String query) throws SQLException {
        PreparedStatement stmt = this.preparedStatementMap.get(query);

        if (stmt == null) {
            stmt = this.conn.prepareStatement(query);

            //cache prepared statement
            this.preparedStatementMap.put(query, stmt);
        }

        return stmt;
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
