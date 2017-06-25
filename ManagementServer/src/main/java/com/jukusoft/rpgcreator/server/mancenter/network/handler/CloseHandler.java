package com.jukusoft.rpgcreator.server.mancenter.network.handler;

import com.jukusoft.rpgcreator.server.mancenter.network.Client;
import com.jukusoft.rpgcreator.server.mancenter.network.message.ManCenterMessage;

/**
 * Created by Justin on 26.06.2017.
 */
public interface CloseHandler {

    /**
    * method, which is executed if client connection was closed
     *
     * @param clientID unique id of client
     * @param client client
    */
    public void onClientClosed (long clientID, Client<ManCenterMessage> client);

}
