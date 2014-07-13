package com.prdev.letsmod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.prdev.letsmod.reference.Reference;

public class ItemLiveIngot extends Item
{
    public ItemLiveIngot()
    {
        super();
        setUnlocalizedName("liveIngot");
        setTextureName(Reference.MODID + ":" + getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.tabMaterials);
    }
}
