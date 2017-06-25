package com.jukusoft.rpgcreator.server.mancenter;

/**
 * Created by Justin on 26.06.2017.
 */
public interface User {

    public long getUserID ();

    public String getUsername ();

    /**
    * check, if user has permission to use ManCenter
     *
     * @return true, if user has permission to use ManCenter
    */
    public boolean hasPermissionForManCenter ();

}
