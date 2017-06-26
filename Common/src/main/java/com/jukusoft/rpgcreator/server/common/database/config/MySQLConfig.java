package com.jukusoft.rpgcreator.server.common.database.config;

import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Justin on 26.06.2017.
 */
public class MySQLConfig {

    protected static final String SECTION_NAME = "MySQL";
    protected static final String INI_IP = "host";
    protected static final String INI_PORT = "port";
    protected static final String INI_USER = "user";
    protected static final String INI_PASSWORD = "password";
    protected static final String INI_DATABASE = "dbName";
    protected static final String INI_PREFIX = "prefix";

    protected String ip = "";
    protected int port = 3306;
    protected String username = "";
    protected String password = "";
    protected String database = "";
    protected String prefix = "";

    public MySQLConfig(String ip, int port, String username, String password, String database, String prefix) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
        this.prefix = prefix;
    }

    public String getIp () {
        return this.ip;
    }

    public int getPort () {
        return this.port;
    }

    public String getUsername () {
        return this.username;
    }

    public String getPassword () {
        return this.password;
    }

    public String getDatabase () {
        return this.database;
    }

    public String getPrefix () {
        return this.prefix;
    }

    public String getDBUrl () {
        return "jdbc:mysql://" + this.ip + ":" + this.port + "/" + this.database;
    }

    public static MySQLConfig readFromFile (File configFile) throws IOException {
        if (!configFile.exists()) {
            throw new FileNotFoundException("cannot found mysql config file: " + configFile.getAbsolutePath());
        }

        //parse ini configuration file
        Ini ini = new Ini(configFile);
        Profile.Section section = ini.get(SECTION_NAME);

        String ip = section.get(INI_IP);
        int port = Integer.parseInt(section.get(INI_PORT));
        String user = section.get(INI_USER);
        String password = section.get(INI_PASSWORD);
        String database = section.get(INI_DATABASE);
        String prefix = section.get(INI_PREFIX);

        //create MySQLConfig
        MySQLConfig config = new MySQLConfig(ip, port, user, password, database, prefix);

        return config;
    }

}
