package com.jukusoft.rpgcreator.server.common.database;

import java.io.File;

/**
 * Created by Justin on 31.05.2017.
 */
public interface Database {

    /**
    * connect to database
     *
     * @param configFile path to configuration file
     *
     * @return true, if client could connect to database
    */
    public boolean connect (File configFile);

    /**
    * disconnect from database
    */
    public void disconnect ();

}
