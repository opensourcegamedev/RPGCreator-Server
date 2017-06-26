package com.jukusoft.rpgcreator.server.common.database;

/**
 * Created by Justin on 26.06.2017.
 */
public interface DBManager {

    /**
    * initialize database manager and check, if versions table exists
    */
    public void init ();

    /**
    * check database structure and database health
     *
     * @return true, if database structure is ok
    */
    public boolean check ();

    /**
    * get version of current database version
     *
     * @return build number of current database structure
    */
    public int getCurrentDBVersion ();

    /**
    * if check() returns false, this method can try to repair database structure
    */
    public boolean repair ();

    /**
    * optimize all tables
    */
    public void optimizeAllTables ();

    /**
    * check, if database structure can be upgraded
     *
     * @return true, if an database upgrade is required
    */
    public boolean isUpgradeRequired ();

    /**
    * upgrade database structure
     *
     * @return true, if database upgrade was successful
    */
    public boolean upgrade ();

}
