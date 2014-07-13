package com.prdev.letsmod.items;

/**
 * Created by Philipp on 24.06.2014.
 */
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

import com.prdev.letsmod.help.RegisterHelper;

public class ModItems
{
    //non-functional items.
    public static Item liveIngot;

    //Tools
    public static Item livePickaxe;
    public static Item liveAxe;
    public static Item bellows;

    // Armor
    public static Item liveHelmet;

    //materials
    static ToolMaterial liveToolMaterial = EnumHelper.addToolMaterial("liveToolMaterial", 2, 750, 7, 2.0F, 20);
    static ArmorMaterial liveArmorMaterial = EnumHelper.addArmorMaterial("liveArmorMaterial", 20, new int[]{2, 6, 5, 2}, 20);

    /**
     * We call this in our main mod file.
     * We add all items here to the game.
     */
    public static void loadItems()
    {
        liveIngot = new ItemLiveIngot();

        RegisterHelper.registerItem(liveIngot);

//Tools
        livePickaxe = new ItemLivePickaxe(liveToolMaterial);
        liveAxe = new ItemLiveAxe(liveToolMaterial);
        bellows = new ItemBellows();

        RegisterHelper.registerItem(livePickaxe);
        RegisterHelper.registerItem(liveAxe);
        RegisterHelper.registerItem(bellows);

//Armor
        liveHelmet = new ItemLiveArmor(liveArmorMaterial, 0, "liveHelmet");

        RegisterHelper.registerItem(liveHelmet);
    }
}