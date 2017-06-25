package com.jukusoft.rpgcreator.server.mancenter.events.factory;

import com.jukusoft.rpgcreator.server.common.Engine;
import com.jukusoft.rpgcreator.server.mancenter.events.NetworkSendEvents;
import com.jukusoft.rpgcreator.server.mancenter.network.message.ManCenterMessage;
import org.json.JSONObject;

/**
 * Created by Justin on 26.06.2017.
 */
public class VersionMessageFactory {

    public static ManCenterMessage createVersionMessage () {
        ManCenterMessage msg = new ManCenterMessage(NetworkSendEvents.VERSION);

        //add version information to message
        JSONObject json = new JSONObject();
        json.put("major_version", Engine.MAJOR_VERSION);
        json.put("minor_version", Engine.MINOR_VERSION);
        json.put("patch_level", Engine.PATCH_LEVEL);
        json.put("build_number", Engine.BUILD_NUMBER);
        json.put("version_string", Engine.getVersionString());

        msg.setPlainData(json.toString());

        return msg;
    }

}
