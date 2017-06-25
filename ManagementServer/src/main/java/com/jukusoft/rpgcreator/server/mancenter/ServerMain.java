package com.jukusoft.rpgcreator.server.mancenter;

import com.jukusoft.rpgcreator.server.common.vertx.VertxIOServerFactory;
import com.jukusoft.rpgcreator.server.mancenter.network.impl.ManCenterServer;
import io.vertx.core.net.NetServer;

/**
 * Created by Justin on 31.05.2017.
 */
public class ServerMain {

    public static final String RPGCREATOR_SERVER_VERSION = "0.0.1 pre-alpha";

    public static void main (String[] args) {
        int port = 1234;

        //create vertx.io server
        //NetServer netServer = VertxIOServerFactory.createVertxServer(port);

        //welcome message
        System.out.println("################################################################");
        System.out.println("# RPGCreator Management Server - Version " + RPGCREATOR_SERVER_VERSION + "");
        System.out.println("################################################################\n");

        System.out.println("try to start management server on port " + port + "now.");

        //create new ManCenter server and set port
        ManCenterServer server = new ManCenterServer();
        server.setPort(port);

        //start server
        server.start();
    }

}
