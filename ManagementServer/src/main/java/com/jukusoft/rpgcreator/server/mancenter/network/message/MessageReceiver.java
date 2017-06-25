package com.jukusoft.rpgcreator.server.mancenter.network.message;

/**
 * Created by Justin on 25.06.2017.
 */
public interface MessageReceiver<T> {

    /**
     * message received
     *
     * @param message chat message
     */
    public void messageReceived(T message);

}
