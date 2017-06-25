package com.jukusoft.rpgcreator.server.mancenter.network.vertx;

import com.jukusoft.rpgcreator.server.mancenter.Server;
import com.jukusoft.rpgcreator.server.mancenter.network.Client;
import com.jukusoft.rpgcreator.server.mancenter.network.message.ManCenterMessage;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;
import io.vertx.core.net.NetSocket;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Justin on 26.06.2017.
 */
public class VertxServer implements Server {

    /**
     * instance of vertx
     */
    protected Vertx vertx = null;

    /**
     * vertx.io options
     */
    protected VertxOptions vertxOptions = new VertxOptions();

    /**
     * options of TCP network server
     */
    protected NetServerOptions netServerOptions = new NetServerOptions();

    /**
     * instance of TCP network server
     */
    protected NetServer netServer = null;

    /**
     * map with client instances
     */
    protected Map<Long,Client> clientMap = new ConcurrentHashMap<>();

    public VertxServer () {
        //set number of threads
        this.vertxOptions.setEventLoopPoolSize(2);
        this.vertxOptions.setWorkerPoolSize(2);
    }

    @Override
    public void setPort(int port) {
        //set port
        this.netServerOptions.setPort(port);
    }

    @Override
    public void start() {
        //create new vertx.io instance
        this.vertx = Vertx.vertx(this.vertxOptions);

        //create network server
        this.netServer = this.vertx.createNetServer(this.netServerOptions);

        //set connection handler
        this.netServer.connectHandler(socket -> {
            System.out.println("new connection accepted, ip: " + socket.remoteAddress().host() + ", port: " + socket.remoteAddress().port());

            //add client
            addClient(socket);
        });

        //start network server
        this.netServer.listen(res -> {
            if (res.succeeded()) {
                System.out.println("ManCenter Server is now listening on port " + res.result().actualPort());
            } else {
                System.err.println("Couldnt start network server.");
            }
        });
    }

    /**
     * add client
     *
     * @param socket network socket
     */
    protected void addClient (NetSocket socket) {
        //create new client instance
        Client client = new ManCenterClient(socket, ((clientID, client1) -> {
            //remove client from map
            clientMap.remove(client1.getClientID());

            //TODO: call listener

            //cleanUp client
            client1.shutdown();
        }));

        //set close handler
        socket.closeHandler(v -> {
            //remove client from map
            clientMap.remove(client.getClientID());

            //TODO: call listener

            //cleanUp client
            client.shutdown();
        });

        //add exception handler
        socket.exceptionHandler(e -> {
            System.err.println("exception in client " + client.getClientID() + ": " + e.getLocalizedMessage());
            e.printStackTrace();
        });

        //put client to map
        this.clientMap.put(client.getClientID(), client);
    }

    @Override
    public void executeBlocking(Runnable runnable) {
        this.vertx.executeBlocking(future -> {
            //execute blocking code
            runnable.run();

            //task was executed
            future.complete();
        }, res -> {
            //
        });
    }

    /**
     * list all clients
     *
     * @return list with all connected clients
     */
    @Override
    public List<Client> listAllClients () {
        //create new list
        List<Client> list = new ArrayList<>();

        //iterate through all clients
        for (Map.Entry<Long,Client> entry : this.clientMap.entrySet()) {
            //add client to list
            list.add(entry.getValue());
        }

        return list;
    }

    /**
     * send broadcast message to all clients
     *
     * @param msg message
     */
    @Override
    public void broadcastMessage (ManCenterMessage msg) {
        //convert to json string
        String jsonStr = msg.toJSON().toString();

        //iterate through all clients
        for (Map.Entry<Long,Client> entry : this.clientMap.entrySet()) {
            //check first, if client is authentificated
            if (entry.getValue().isAuthentificated()) {
                //send message
                entry.getValue().write(jsonStr);
            }
        }
    }

    /**
     * send broadcast message to all clients
     *
     * @param msg chat message
     * @param withoutUser user which shouldnt receive this message to avoid that sender receives his own message
     */
    public void broadcastMessage (ManCenterMessage msg, long withoutUser) {
        //convert to json object
        JSONObject json = msg.toJSON();
        String jsonStr = json.toString();

        //iterate through all clients
        for (Map.Entry<Long,Client> entry : this.clientMap.entrySet()) {
            //check first, if client is authentificated
            if (entry.getValue().isAuthentificated() && entry.getKey() != withoutUser) {
                //send message
                entry.getValue().write(jsonStr);
            }
        }
    }


    @Override
    public void shutdown() {
        //close network server
        this.netServer.close(res -> {
            if (res.succeeded()) {
                System.out.println("Server was shutdown now.");

                //close vertx.io
                this.vertx.close();
            } else {
                System.out.println("Server couldnt be closed.");
            }
        });
    }

}
