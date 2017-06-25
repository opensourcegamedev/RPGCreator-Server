package com.jukusoft.rpgcreator.server.mancenter.network.vertx;

import com.jukusoft.rpgcreator.server.mancenter.User;
import com.jukusoft.rpgcreator.server.mancenter.network.Client;
import com.jukusoft.rpgcreator.server.mancenter.network.NetworkReceiveEvents;
import com.jukusoft.rpgcreator.server.mancenter.network.handler.CloseHandler;
import com.jukusoft.rpgcreator.server.mancenter.network.handler.impl.DistributedMessageHandler;
import com.jukusoft.rpgcreator.server.mancenter.network.handler.request.LoginHandler;
import com.jukusoft.rpgcreator.server.mancenter.network.message.ManCenterMessage;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Justin on 26.06.2017.
 */
public class ManCenterClient implements Client<ManCenterMessage> {

    /**
     * last client ID
     */
    protected static AtomicLong lastID = new AtomicLong(0);

    /**
     * network socket to client
     */
    protected NetSocket socket = null;

    /**
     * clientID
     */
    protected long clientID = 0;

    /**
     * flag, if user is authentificated
     */
    protected AtomicBoolean isAuthentificated = new AtomicBoolean(false);

    protected boolean connected = false;

    protected DistributedMessageHandler distributedMessageHandler = null;

    protected User user = null;

    public ManCenterClient (NetSocket socket, CloseHandler closeHandler) {
        this.socket = socket;

        //generate new client id
        this.clientID = generateClientID();

        //add message handler
        this.socket.handler(buffer -> {
            try {
                //call message received listener
                messageReceived(buffer);
            } catch (Exception e) {
                //print exception
                System.err.println("exception while reading message from client " + this.clientID + ":");
                e.printStackTrace();
            }
        });

        this.socket.closeHandler(event -> {
            this.socket = null;

            closeHandler.onClientClosed(this.clientID, this);
        });

        //add exception handler
        socket.exceptionHandler(e -> {
            System.err.println("exception in client " + this.getClientID() + ": " + e.getLocalizedMessage());
            e.printStackTrace();
        });

        //create new distributed message handler
        this.distributedMessageHandler = new DistributedMessageHandler(this);

        //add login handler
        this.distributedMessageHandler.addHandler(NetworkReceiveEvents.LOGIN, new LoginHandler(res -> {
            if (!res.succeeded()) {
                System.out.println("login failed for client " + getClientID() + ": " + res.cause().getLocalizedMessage());
            } else {
                //login successful

                //get user
                this.user = res.result();

                //check, if user has permission to use ManCenter
                if (!user.hasPermissionForManCenter()) {
                    //close connection
                    this.shutdown();

                    return;
                }

                //set authentificated flag
                this.isAuthentificated.set(true);
            }
        }));
    }

    /**
    * receive message
     *
     * @param buffer message buffer
    */
    protected void messageReceived (Buffer buffer) {
        //convert to string and json object
        String str = buffer.toString(StandardCharsets.UTF_8);

        //remove whitespaces at begin and end
        str = str.trim();

        //check, if message is an json message
        if (!str.startsWith("{") || !str.endsWith("}")) {
            //no json message
            System.err.println("drop invalid json message: " + str);

            return;
        }

        //convert string to json
        JSONObject json = new JSONObject(str);

        System.out.println("message received: " + str);

        //convert to chat message
        ManCenterMessage msg = ManCenterMessage.create(json);

        //call message handler
        this.distributedMessageHandler.messageReceived(this, msg);
    }

    @Override
    public void write(ManCenterMessage msg) {
        if (!isConnected()) {
            throw new IllegalStateException("Client isnt connected, so VertxClient cannot send message.");
        }

        if (this.socket == null) {
            throw new IllegalStateException("Socket is null, so vertx client cannot send message to server.");
        }

        //get message string
        String msgStr = msg.toJSON().toString();

        //log message
        System.err.println("send message to client " + this.clientID + ": " + msgStr);

        //write json string of message to server
        this.socket.write(msgStr);
    }

    @Override
    public long getClientID() {
        return this.clientID;
    }

    @Override
    public boolean isConnected() {
        return this.connected;
    }

    @Override
    public boolean isAuthentificated() {
        return this.isAuthentificated.get();
    }

    @Override
    public DistributedMessageHandler getDistributedMessageHandler() {
        return this.distributedMessageHandler;
    }

    @Override
    public void shutdown() {
        if (this.socket != null) {
            this.socket.close();
        }

        //reset connected flag
        this.connected = false;

        System.out.println("close connection with ID " + this.clientID);

        //TODO: call listeners
    }

    protected long generateClientID () {
        return this.lastID.incrementAndGet();
    }

}
