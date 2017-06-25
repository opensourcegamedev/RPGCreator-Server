package com.jukusoft.rpgcreator.server.mancenter.network;

import com.jukusoft.rpgcreator.server.mancenter.network.handler.impl.DistributedMessageHandler;

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
    * check, if client is connected
     *
     * @return true, if client is connected
    */
    public boolean isConnected ();

    /**
     * check, if client is authentificated
     */
    public boolean isAuthentificated ();

    /**
    * get distributed message handler
     *
     * @return distributed message handler
    */
    public DistributedMessageHandler getDistributedMessageHandler ();

    /**
    * close connection and shutdown client
    */
    public void shutdown ();

}
