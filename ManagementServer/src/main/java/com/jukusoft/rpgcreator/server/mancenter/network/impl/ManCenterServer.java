package com.jukusoft.rpgcreator.server.mancenter.network.impl;

import com.jukusoft.rpgcreator.server.mancenter.events.NetworkReceiveEvents;
import com.jukusoft.rpgcreator.server.mancenter.events.request.VersionRequest;
import com.jukusoft.rpgcreator.server.mancenter.network.Client;
import com.jukusoft.rpgcreator.server.mancenter.network.handler.impl.DistributedMessageHandler;
import com.jukusoft.rpgcreator.server.mancenter.network.message.ManCenterMessage;
import com.jukusoft.rpgcreator.server.mancenter.network.vertx.VertxServer;

/**
 * Created by Justin on 26.06.2017.
 */
public class ManCenterServer extends VertxServer {

    @Override
    protected void initClient(Client<ManCenterMessage> client) {
        //initialize event handler
        DistributedMessageHandler handlers = client.getDistributedMessageHandler();

        //add handlers
        handlers.addHandler(NetworkReceiveEvents.VERSION, new VersionRequest());
    }

}
