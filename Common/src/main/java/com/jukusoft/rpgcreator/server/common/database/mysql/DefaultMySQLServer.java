package com.jukusoft.rpgcreator.server.common.database.mysql;

import com.jukusoft.rpgcreator.server.common.database.config.MySQLConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    * table prefix
    */
    protected String prefix = "";

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

    @Override
    public void connect (String ip, int port, String user, String password, String database, String prefix) throws SQLException {
        this.prefix = prefix;

        //create the properties list with user, password and autoreconnect
        Properties props = this.createConnectionProperties(user, password);

        //try to connect to the database
        this.conn = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + database, props);
    }

    @Override
    public void connect(MySQLConfig config) throws SQLException {
        this.prefix = config.getPrefix();

        //create the properties list with user, password and autoreconnect
        Properties props = this.createConnectionProperties(config.getUsername(), config.getPassword());

        if (props == null) {
            throw new NullPointerException("props is null.");
        }

        String dbUrl = config.getDBUrl();
        System.out.println("[DEBUG] DB Url: " + dbUrl);

        //try to connect to the database
        this.conn = DriverManager.getConnection(dbUrl, props);

        if (this.conn == null) {
            throw new NullPointerException("conn is null.");
        }
    }

    private Properties createConnectionProperties (String user, String password) {
        //create the properties list with user, password and autoreconnect
        Properties props = new Properties();
        props.put("user", user);
        props.put("password", password);
        props.put("autoReconnect", "true");

        //cache prepared statements
        //props.put("cachePrepStmts", "true");
        //props.put("useServerPrepStmts", "true");
        //props.put("prepStmtCacheSize", "250");
        //props.put("prepStmtCacheSqlLimit", 2048);

        return props;
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
    public PreparedStatement prepare(String query) {
        PreparedStatement stmt = null;//this.preparedStatementMap.get(query);

        if (stmt == null) {
            try {
                stmt = this.conn.prepareStatement(query);
            } catch (SQLException e) {
                e.printStackTrace();

                throw new IllegalStateException("SQLException: " + e.getLocalizedMessage(), e);
            }

            //cache prepared statement
            this.preparedStatementMap.put(query, stmt);
        }

        return stmt;
    }

    @Override
    public List<String> listTables() {
        String sql = "SHOW TABLES; ";

        //prepare statement
        PreparedStatement stmt = this.prepare(sql);

        ResultSet rs = null;

        try {
            rs = stmt.executeQuery();

            //create new list
            List<String> list = new ArrayList<>();

            //iterate through all rows
            while (rs.next()) {
                //get first coloum, because their is table name stored, index here begins with 1, not with 0
                String tableName = rs.getString(1);

                //add table to list
                list.add(tableName);
            }

            //close result set
            rs.close();

            //close statement
            stmt.close();

            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("Cannot list tables, caused by SQLException: " + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean belongsToRCE(String tableName) {
        return tableName.startsWith(this.prefix);
    }

    @Override
    public boolean isPluginTable(String tableName) {
        return tableName.startsWith(this.prefix + "plugin_");
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    /**
    * set table prefix (for JUnit tests)
     *
     * @param prefix table prefix
    */
    protected void setPrefix (String prefix) {
        this.prefix = prefix;
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
