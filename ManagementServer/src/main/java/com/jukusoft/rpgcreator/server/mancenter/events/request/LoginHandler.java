package com.jukusoft.rpgcreator.server.mancenter.events.request;

import com.jukusoft.rpgcreator.server.common.user.User;
import com.jukusoft.rpgcreator.server.common.user.UserManager;
import com.jukusoft.rpgcreator.server.mancenter.network.AsyncResult;
import com.jukusoft.rpgcreator.server.mancenter.network.Client;
import com.jukusoft.rpgcreator.server.mancenter.network.Handler;
import com.jukusoft.rpgcreator.server.mancenter.network.handler.EventHandler;
import com.jukusoft.rpgcreator.server.mancenter.network.message.ManCenterMessage;
import org.json.JSONObject;

/**
 * Created by Justin on 26.06.2017.
 */
public class LoginHandler implements EventHandler<ManCenterMessage> {

    /**
    * handler which will be executed after every login try
    */
    protected Handler<AsyncResult<User>> handler = null;

    /**
    * user manager instance
    */
    protected UserManager userManager = null;

    public LoginHandler (UserManager userManager, Handler<AsyncResult<User>> handler) {
        this.userManager = userManager;
        this.handler = handler;
    }

    @Override
    public void messageReceived(Client<ManCenterMessage> client, ManCenterMessage message) {
        System.out.println("login request received.");

        //convert data string to json object
        JSONObject json = new JSONObject(message.getPlainData());

        //get username and password
        String username = json.getString("username");
        String password = json.getString("password");

        System.out.println("try to login user '" + username + "'.");

        //find user
        User IUser = this.userManager.findUserByName(username);

        //TODO: check login data

        //TODO: call handler
    }

}
