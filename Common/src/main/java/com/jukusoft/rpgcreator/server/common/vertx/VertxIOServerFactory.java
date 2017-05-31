package com.jukusoft.rpgcreator.server.common.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

/**
 * Created by Justin on 31.05.2017.
 */
public class VertxIOServerFactory {

    public static NetServer createVertxServer (int port) {
        //create new vertx.io options
        VertxOptions vertxOptions = new VertxOptions();

        //create new instance of vertx.io
        Vertx vertx = Vertx.vertx(vertxOptions);

        //create options for TCP network server
        NetServerOptions netServerOptions = new NetServerOptions();

        //set port
        netServerOptions.setPort(port);

        //create new instance of TCP network server
        NetServer netServer = vertx.createNetServer(netServerOptions);

        return netServer;
    }

}
