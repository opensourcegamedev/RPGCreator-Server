package com.jukusoft.rpgcreator.server.mancenter.api;

import com.jukusoft.rpgcreator.server.common.api.ServerApi;
import com.jukusoft.rpgcreator.server.common.database.mysql.MySQLServer;
import com.jukusoft.rpgcreator.server.common.user.UserManager;
import com.jukusoft.rpgcreator.server.common.user.impl.MySQLUserManager;

/**
 * Created by Justin on 26.06.2017.
 */
public class ManCenterApi implements ServerApi {

    protected MySQLServer db = null;

    //managers
    protected UserManager userManager = null;

    public ManCenterApi (MySQLServer server) {
        this.db = server;

        //create new user manager
        this.userManager = new MySQLUserManager(this.db);
    }

    @Override
    public boolean checkAuthData(String username, String password) throws Exception {
        return false;
    }

    @Override
    public void loginInDB(long userID) {

    }

    @Override
    public void logout(long userID) {

    }

    @Override
    public UserManager getUserManager() {
        return this.userManager;
    }

    @Override
    public void updateLastOnlineTimestamp(long userID) {

    }

    @Override
    public void log(String message) {

    }

}
