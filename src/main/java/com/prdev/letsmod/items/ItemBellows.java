package com.prdev.letsmod.items;

/**
 * Created by Philipp on 23.06.2014.
 */
import com.prdev.letsmod.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBellows extends ItemClass {
    public ItemBellows()
    {
        setMaxStackSize(64);
        setCreativeTab(CreativeTabs.tabMisc);
        setUnlocalizedName("bellows");
        setTextureName(Reference.MODID + ":" + getUnlocalizedName().substring(5));
    }


}
