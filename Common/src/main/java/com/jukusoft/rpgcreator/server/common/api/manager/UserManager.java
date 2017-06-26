package com.jukusoft.rpgcreator.server.common.api.manager;

/**
 * Created by Justin on 26.06.2017.
 */
public interface UserManager {

    /**
    * count all registered users
    */
    public int countRegisteredUsers ();

    /**
    * count all online users
    */
    public int countOnlineUsers ();

}
