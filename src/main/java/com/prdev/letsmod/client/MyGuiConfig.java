package com.prdev.letsmod.client;

import com.prdev.letsmod.handler.ConfigurationHandler;
import com.prdev.letsmod.help.LogHelper;
import com.prdev.letsmod.reference.Reference;
import cpw.mods.fml.client.config.GuiConfig;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

/**
 * Created by Philipp on 03.07.2014.
 */
public class MyGuiConfig extends GuiConfig
{
    public MyGuiConfig(GuiScreen guiScreen)
    {
        super(guiScreen,
                new ConfigElement(ConfigurationHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                Reference.MODID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
        LogHelper.info("MyGuiConfig init");
    }
}
