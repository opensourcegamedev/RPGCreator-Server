package com.jukusoft.rpgcreator.server.common.database.manager;

/**
 * Created by Justin on 26.06.2017.
 */
public interface DatabaseUpgrader {

    /**
    * create full database structure
    */
    public void onCreate ();

    /**
    * upgrade database
     *
     * @param newVersion old version number of database
     * @param oldVersion new version number of database
     *
     * @return true, if datbase upgrade was successful
    */
    public boolean onUpgrade (int oldVersion, int newVersion);

}
