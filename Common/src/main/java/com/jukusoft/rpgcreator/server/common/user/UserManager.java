package com.jukusoft.rpgcreator.server.common.user;

import com.jukusoft.rpgcreator.server.common.user.User;

/**
 * Created by Justin on 26.06.2017.
 */
public interface UserManager {

    /**
    * find user by username
     *
     * @return instance of user or null, if user doesnt exists
    */
    public User findUserByName (String username);

    /**
     * find user by username
     *
     * @return instance of user or null, if user doesnt exists
     */
    public User findUserByMail (String mail);

    /**
    * count all registered users
    */
    public int countRegisteredUsers ();

    /**
    * count all online users
    */
    public int countOnlineUsers ();

}
