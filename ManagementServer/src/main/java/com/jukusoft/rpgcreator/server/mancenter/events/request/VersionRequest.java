package com.jukusoft.rpgcreator.server.mancenter.events.request;

import com.jukusoft.rpgcreator.server.mancenter.events.factory.VersionMessageFactory;
import com.jukusoft.rpgcreator.server.mancenter.network.Client;
import com.jukusoft.rpgcreator.server.mancenter.network.handler.EventHandler;
import com.jukusoft.rpgcreator.server.mancenter.network.message.ManCenterMessage;

/**
 * Created by Justin on 26.06.2017.
 */
public class VersionRequest implements EventHandler<ManCenterMessage> {

    @Override
    public void messageReceived(Client<ManCenterMessage> client, ManCenterMessage message) {
        if (!client.isAuthentificated()) {
            throw new IllegalStateException("drop version message, because client " + client.getClientID() + " isnt authentificated.");
        }

        //send version information
        client.write(VersionMessageFactory.createVersionMessage());
    }

}
