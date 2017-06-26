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

    public PreparedStatement prepare (String query) throws SQLException;

    /**
    * list all tables in database
     *
     * @return list with all tables in database
    */
    public List<String> listTables ();

    public String getPrefix ();

    public void disconnect ();

}
