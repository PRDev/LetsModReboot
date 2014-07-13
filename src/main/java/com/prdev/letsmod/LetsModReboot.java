package com.prdev.letsmod;
import com.prdev.letsmod.blocks.ModBlocks;
import com.prdev.letsmod.handler.ConfigurationHandler;
import com.prdev.letsmod.handler.GuiHandler;
import com.prdev.letsmod.handler.ModRecipes;
import com.prdev.letsmod.help.LogHelper;
import com.prdev.letsmod.reference.Reference;
import com.prdev.letsmod.items.ModItems;
import com.prdev.letsmod.proxy.IProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid= Reference.MODID, name=Reference.MOD_NAME, version=Reference.VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class LetsModReboot
{
    @Mod.Instance(Reference.MODID)
    public static LetsModReboot instance;

    // Says where the proxy and server 'proxy' code is loaded.
    @SidedProxy(clientSide= Reference.CLIENT_PROXY_CLASS, serverSide= Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    public static final int guiSoapstoneFurnace = 0;
    public static final int guiIronOven = 1;

    GuiHandler guiHandler = new GuiHandler();





//----------------------------------------------PRE-INIT----------------------------------------------------
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigurationHandler.init(event.getSuggestedConfigurationFile());
        LogHelper.info(event.getSuggestedConfigurationFile().getAbsolutePath());
        FMLCommonHandler.instance().bus().register(new ConfigurationHandler());


        NetworkRegistry.INSTANCE.registerGuiHandler(this , guiHandler);
        //Block handlers, handles all blocks
        ModBlocks.loadBlocks();

        //Item handlers, handles all items
        ModItems.loadItems();

        //Register handler, adds all the recipes
        ModRecipes.addRecipes();


        LogHelper.info("LetsMod Init complete!");


    }
//------------------------------------------------INIT-----------------------------------------------------
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {





    }
//------------------------------------------------POST-INIT---------------------------------------------------
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
