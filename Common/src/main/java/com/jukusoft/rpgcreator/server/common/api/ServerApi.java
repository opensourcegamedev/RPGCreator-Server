package com.jukusoft.rpgcreator.server.common.api;

/**
 * Created by Justin on 26.06.2017.
 */
public interface ServerApi {

    public boolean checkAuthData (String username, String password) throws Exception;

    public void loginInDB (long userID);

    public void logout (long userID);

    /**
    * set last online timestamp to now and set online state
    */
    public void updateLastOnlineTimestamp (long userID);

    public void log (String message);

}
