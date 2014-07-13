package com.prdev.letsmod.items;


import com.prdev.letsmod.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;

public class ItemLivePickaxe extends ItemPickaxe
{
    public ItemLivePickaxe(ToolMaterial material)
    {
        super(material);
        setUnlocalizedName("livePickaxe");
        setTextureName(Reference.MODID + ":" + getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.tabTools);
    }
}