package com.jukusoft.rpgcreator.server.mancenter.network.message;

import com.jukusoft.rpgcreator.server.mancenter.network.Client;

/**
 * Created by Justin on 25.06.2017.
 */
public interface MessageReceiver<T> {

    /**
     * message received
     *
     * @param client mancenter client
     * @param message chat message
     */
    public void messageReceived(Client<ManCenterMessage> client, T message);

}
