package com.jukusoft.rpgcreator.server.mancenter.network;

/**
 * Created by Justin on 26.06.2017.
 */
public interface Client<T> {

    /**
    * send message to editor
     *
     * @param msg message
    */
    public void write (T msg);

    /**
    * get unique id of client
     *
     * @return unique id of client
    */
    public long getClientID ();

    /**
     * check, if client is authentificated
     */
    public boolean isAuthentificated ();

    public void shutdown ();

}
