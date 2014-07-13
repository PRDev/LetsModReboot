package com.prdev.letsmod.blocks;

import com.prdev.letsmod.tileEntity.TileEntityIronOven;
import com.prdev.letsmod.tileEntity.TileEntitySoapstoneFurnace;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

import com.prdev.letsmod.help.RegisterHelper;
import net.minecraft.creativetab.CreativeTabs;

public class ModBlocks
{
    public static Block liveOre;
    public static Block soapstone;
    public static Block soapstoneFurnace;
    public static Block soapstoneFurnaceActive;
    public static Block ironOven;
    public static Block ironOvenActive;

    public static void loadBlocks()
    {
        liveOre = new BlockLiveOre();
        soapstone = new Soapstone();
        soapstoneFurnace = new SoapstoneFurnace(false).setCreativeTab(CreativeTabs.tabBlock);;
        soapstoneFurnaceActive = new SoapstoneFurnace(true).setBlockName("soapstoneFurnaceActive");


        RegisterHelper.registerBlock(liveOre);
        RegisterHelper.registerBlock(soapstone);
        RegisterHelper.registerBlock(soapstoneFurnace);
        RegisterHelper.registerBlock(soapstoneFurnaceActive);

        GameRegistry.registerTileEntity(TileEntitySoapstoneFurnace.class, "tileEntitySoapstoneFurnace");

        ironOven = new IronOven(3000, false).setHardness(4.0F).setResistance(15.0F).setBlockName("ironOven").setCreativeTab(CreativeTabs.tabBlock);
        ironOvenActive = new IronOven(3001, true).setHardness(4.0F).setResistance(15.0F).setBlockName("ironOvenActive");

        GameRegistry.registerBlock(ironOven, "ironOven");
        GameRegistry.registerBlock(ironOvenActive, "ironOvenActive");

        GameRegistry.registerTileEntity(TileEntityIronOven.class, "tileEntityIronOven");
    }
}
