package com.prdev.letsmod.blocks;

import java.util.Random;

import com.prdev.letsmod.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Soapstone extends Block {
    public Soapstone() {
        super(Material.rock);

        setHardness(2.5F);
        setStepSound(Block.soundTypePiston);
        setBlockName("soapstone");
        setBlockTextureName(Reference.MODID + ":" + getUnlocalizedName().substring(5));
        setCreativeTab(CreativeTabs.tabBlock);
        setHarvestLevel("pickaxe", 3);

    }

    @Override
    protected void dropBlockAsItem(World p_149642_1_, int p_149642_2_, int p_149642_3_, int p_149642_4_, ItemStack p_149642_5_) {
        super.dropBlockAsItem(p_149642_1_, p_149642_2_, p_149642_3_, p_149642_4_, p_149642_5_);
    }

    //If the block's drop is a block.
    @Override
    public Item getItemDropped(int metadata, Random random, int fortune)
    {
        return Item.getItemFromBlock(ModBlocks.soapstone);
    }


}
