package com.jukusoft.rpgcreator.server.mancenter.network.handler;

import com.jukusoft.rpgcreator.server.mancenter.network.Client;
import com.jukusoft.rpgcreator.server.mancenter.network.message.ManCenterMessage;

/**
 * Created by Justin on 25.06.2017.
 */
public interface EventHandler<T> {

    /**
    * message received
     *
     * @param client mancenter client
     * @param message message
    */
    public void messageReceived(Client<ManCenterMessage> client, T message);

}
