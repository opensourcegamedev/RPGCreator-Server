package com.jukusoft.rpgcreator.server.common.database.mysql;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Justin on 26.06.2017.
 */
public class DefaultMySQLServerTest {

    @Test
    public void testBelongsToRCE () {
        DefaultMySQLServer server = new DefaultMySQLServer();

        //set an test prefix
        server.setPrefix("rce_");

        //check, if prefix is equals
        assertEquals("prefix isnt equals", true, server.getPrefix().equals("rce_"));

        assertEquals(true, server.belongsToRCE("rce_table"));
        assertEquals(false, server.belongsToRCE("table"));
        assertEquals(false, server.belongsToRCE("rpg_table"));
        assertEquals(true, server.belongsToRCE("rce_plugin_table"));
    }

    @Test
    public void testIsPluginTable () {
        DefaultMySQLServer server = new DefaultMySQLServer();

        //set an test prefix
        server.setPrefix("rce_");

        assertEquals(false, server.isPluginTable("rce_table"));
        assertEquals(true, server.isPluginTable("rce_plugin_table"));
        assertEquals(false, server.isPluginTable("rce_plugins_table"));
        assertEquals(true, server.isPluginTable("rce_plugin_testplugin_table"));
    }

}
