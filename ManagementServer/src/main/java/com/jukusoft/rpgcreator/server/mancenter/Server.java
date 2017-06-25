package com.jukusoft.rpgcreator.server.mancenter;

import com.jukusoft.rpgcreator.server.mancenter.network.Client;
import com.jukusoft.rpgcreator.server.mancenter.network.message.ManCenterMessage;

import java.util.List;

/**
 * Created by Justin on 26.06.2017.
 */
public interface Server {

    /**
    * set port on which server should listen
     *
     * @param port port number
    */
    public void setPort (int port);

    /**
    * start server
    */
    public void start ();

    /**
    * execute an blocking task
    */
    public void executeBlocking(Runnable runnable);

    /**
     * list all clients
     *
     * @return list with all connected clients
     */
    public List<Client> listAllClients ();

    /**
    * send message to every client connected to this server
     *
     * @param msg message
    */
    public void broadcastMessage (ManCenterMessage msg);

    /**
     * send broadcast message to all clients expecting one user
     *
     * @param msg chat message
     * @param withoutUser user which shouldnt receive this message to avoid that sender receives his own message
     */
    public void broadcastMessage (ManCenterMessage msg, long withoutUser);

    /**
    * shutdown server
    */
    public void shutdown ();

}
