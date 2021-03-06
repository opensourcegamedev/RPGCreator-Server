package com.jukusoft.rpgcreator.server.common.api;

import com.jukusoft.rpgcreator.server.common.user.UserManager;

/**
 * Created by Justin on 26.06.2017.
 */
public interface ServerApi {

    public boolean checkAuthData (String username, String password) throws Exception;

    public void loginInDB (long userID);

    public void logout (long userID);

    /**
    * get instance of user manager
    */
    public UserManager getUserManager ();

    /**
    * set last online timestamp to now and set online state
    */
    public void updateLastOnlineTimestamp (long userID);

    public void log (String message);

}
