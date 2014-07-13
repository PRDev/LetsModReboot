package com.prdev.letsmod.handler;

import com.prdev.letsmod.reference.Reference;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Philipp on 30.06.2014.
 */
public class ConfigurationHandler
{
    public static Configuration config;
    public static boolean testValue = false;

    public static void init(File configFile)
    {

        if (config == null)
        {
            config = new Configuration(configFile);
            loadConfiguration();
        }



    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.MODID))
        {
            loadConfiguration();
        }

    }

    private static void loadConfiguration()
    {


        testValue = config.getBoolean("configvalue", Configuration.CATEGORY_GENERAL, false, "This is an example value");

        if (config.hasChanged()) {
            config.save();
        }

    }
}