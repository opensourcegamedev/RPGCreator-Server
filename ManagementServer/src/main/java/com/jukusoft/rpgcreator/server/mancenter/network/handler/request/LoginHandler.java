package com.jukusoft.rpgcreator.server.mancenter.network.handler.request;

import com.jukusoft.rpgcreator.server.mancenter.User;
import com.jukusoft.rpgcreator.server.mancenter.network.AsyncResult;
import com.jukusoft.rpgcreator.server.mancenter.network.Client;
import com.jukusoft.rpgcreator.server.mancenter.network.Handler;
import com.jukusoft.rpgcreator.server.mancenter.network.handler.EventHandler;
import com.jukusoft.rpgcreator.server.mancenter.network.message.ManCenterMessage;

/**
 * Created by Justin on 26.06.2017.
 */
public class LoginHandler implements EventHandler<ManCenterMessage> {

    /**
    * handler which will be executed after every login try
    */
    protected Handler<AsyncResult<User>> handler = null;

    public LoginHandler (Handler<AsyncResult<User>> handler) {
        this.handler = handler;
    }

    @Override
    public void messageReceived(Client<ManCenterMessage> client, ManCenterMessage message) {
        System.out.println("login request received.");

        //TODO: call handler
    }

}
