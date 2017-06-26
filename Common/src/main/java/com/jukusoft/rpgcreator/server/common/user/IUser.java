package com.jukusoft.rpgcreator.server.common.user;

/**
 * Created by Justin on 26.06.2017.
 */
public interface IUser {

    public long getUserID ();

    public String getUsername ();

    /**
    * check, if user has permission to use ManCenter
     *
     * @return true, if user has permission to use ManCenter
    */
    public boolean hasPermissionForManCenter ();

}
