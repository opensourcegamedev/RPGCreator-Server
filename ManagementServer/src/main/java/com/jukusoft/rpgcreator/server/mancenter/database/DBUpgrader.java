package com.jukusoft.rpgcreator.server.mancenter.database;

import com.jukusoft.rpgcreator.server.common.database.manager.DatabaseUpgrader;

/**
 * Created by Justin on 26.06.2017.
 */
public class DBUpgrader implements DatabaseUpgrader {

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public boolean onUpgrade(int oldVersion, int newVersion) {
        return false;
    }

}
