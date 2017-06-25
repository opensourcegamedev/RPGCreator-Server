package com.jukusoft.rpgcreator.server.common.database.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Justin on 26.06.2017.
 */
public interface MySQLServer {

    public void connect (String ip, int port, String user, String password, String database) throws SQLException;

    public ResultSet query(String sqlstatement) throws SQLException;

    public PreparedStatement prepare (String query) throws SQLException;

    public void disconnect ();

}
