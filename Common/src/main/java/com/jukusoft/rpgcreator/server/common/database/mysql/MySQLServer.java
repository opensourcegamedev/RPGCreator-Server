package com.jukusoft.rpgcreator.server.common.database.mysql;

import com.jukusoft.rpgcreator.server.common.database.config.MySQLConfig;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Justin on 26.06.2017.
 */
public interface MySQLServer {

    public void connect (String ip, int port, String user, String password, String database, String prefix) throws SQLException;

    public void connect (MySQLConfig config) throws SQLException;

    public ResultSet query(String sqlstatement) throws SQLException;

    public boolean execute (String sql) throws SQLException;

    public PreparedStatement prepare (String query) throws SQLException;

    /**
     * get first row of result set
     */
    public ResultSet getRow (String sql) throws SQLException;

    /**
     * get first row of result set
     */
    public ResultSet getRow (String sql, String... params) throws SQLException;

    /**
    * list all tables in database
     *
     * @return list with all tables in database
    */
    public List<String> listTables ();

    /**
     * list all tables in database
     *
     * @param fullName use full table name
     *
     * @return list with all tables in database
     */
    public List<String> listTables (boolean fullName);

    /**
    * check table
     *
     * SQL: CHECK TABLE `table name`
     *
     * @param tableName table name
     *
     * @return true, if table is ok
    */
    public boolean checkTable (String tableName);

    public String getLastCheckResult ();

    /**
    * check, if an table belongs to RPG Creator Editor
     *
     * @param tableName table name
     *
     * @return true, if table belongs to this engine
    */
    public boolean belongsToRCE (String tableName);

    /**
    * check, if this table belongs to an plugin
     *
     * @param tableName table name
     *
     * @return true, if table belongs to an plugin
    */
    public boolean isPluginTable (String tableName);

    /**
    * start an new transaction
    */
    public void startTransaction () throws SQLException;

    /**
    * commit and end transaction (transaction was successful)
    */
    public void commit () throws SQLException;

    /**
    * rollback transaction, if operation failed
    */
    public void rollback () throws SQLException;

    /**
    * optimize table
     *
     * @param tableName table name
    */
    public void optimize (String tableName);

    public String getPrefix ();

    public void disconnect ();

}
