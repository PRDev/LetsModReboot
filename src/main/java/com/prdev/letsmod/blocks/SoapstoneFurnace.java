package com.prdev.letsmod.blocks;

import com.prdev.letsmod.LetsModReboot;
import com.prdev.letsmod.help.LogHelper;
import com.prdev.letsmod.tileEntity.TileEntitySoapstoneFurnace;
import com.prdev.letsmod.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Philipp on 24.06.2014.
 */
public class SoapstoneFurnace extends BlockContainer{

    private final boolean active;
    @SideOnly(Side.CLIENT)
    private IIcon iconFront;
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    //@SideOnly(Side.CLIENT)
    //private IIcon blockIcon;
    private static boolean keepInventory;
    private final Random rand = new Random();


    public SoapstoneFurnace(boolean isActive) {
        super(Material.rock);

        setHardness(3.5F);
        setStepSound(Block.soundTypePiston);
        setBlockName("soapstoneFurnace");
        //setBlockTextureName(Reference.MODID + ":" + getUnlocalizedName().substring(5));
        setHarvestLevel("pickaxe", 3);
        active = isActive;
        if (active) {
            setLightLevel(0.9F);
        }


    }



    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
        this.setDefaultDirection(world, x, y, z);
    }
    private void setDefaultDirection(World world, int x, int y, int z) {
        if(!world.isRemote){
            Block a = world.getBlock(x, y, z - 1);
            Block b = world.getBlock(x, y, z + 1);
            Block c = world.getBlock(x - 1, y, z);
            Block d = world.getBlock(x + 1, y, z);

            byte b0 =3;

            if (a.func_149730_j() && !b.func_149730_j())
            {
                b0 = 3;
            }

            if (b.func_149730_j() && !a.func_149730_j())
            {
                b0 = 2;
            }

            if (c.func_149730_j() && !d.func_149730_j())
            {
                b0 = 5;
            }

            if (d.func_149730_j() && !c.func_149730_j())
            {
                b0 = 4;
            }


            world.setBlockMetadataWithNotify(x,y,z,b0,2);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata) {
        return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : (side != metadata ? this.blockIcon : this.iconFront));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        //System.out.println("adding texture");
        this.blockIcon = iconRegister.registerIcon(Reference.MODID + ":" +"soapstoneFurnace_side");
        this.iconFront = iconRegister.registerIcon((this.active ? Reference.MODID + ":" +"soapstoneFurnace_front_active" : Reference.MODID + ":" +"soapstoneFurnace_front"));
        this.iconTop = iconRegister.registerIcon(Reference.MODID + ":" +"soapstoneFurnace_top");
    }


    public int idDropped(int par1, Random random, int par2) {
        return getIdFromBlock(ModBlocks.soapstoneFurnace);
    }




    //If the block's drop is a block.
    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemFromBlock(ModBlocks.soapstone);
    }


    @Override
    public TileEntity createNewTileEntity(World world, int var2) {
        return new TileEntitySoapstoneFurnace();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLivingBase, ItemStack itemStack) {
        int ori = MathHelper.floor_double((double)(entityLivingBase.rotationYaw * 4.0F / 360.0F) +0.5D) & 3;

        if(ori ==0){
            world.setBlockMetadataWithNotify(x,y,z,2,2);
        }
        if(ori ==1){
            world.setBlockMetadataWithNotify(x,y,z,5,2);
        }
        if(ori ==2){
            world.setBlockMetadataWithNotify(x,y,z,3,2);
        }
        if(ori ==3){
            world.setBlockMetadataWithNotify(x,y,z,4,2);
        }

        if(itemStack.hasDisplayName()) {
            ((TileEntitySoapstoneFurnace)world.getTileEntity(x,y,z)).setGuiDisplayName(itemStack.getDisplayName());
        }
    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
        {
            return true;
        }
        else
        {
            //System.out.println("onBlockActivated: Versuch gui zu öffnen");
            //FMLNetworkHandler.openGui(player, LetsModReboot.instance, LetsModReboot.guiSoapstoneFurnace, world, x, y, z );
            TileEntitySoapstoneFurnace tileEntitySoapstoneFurnace = (TileEntitySoapstoneFurnace)world.getTileEntity(x,y,z);
            if (tileEntitySoapstoneFurnace != null)
            {
                //System.out.println("rufe player.openGui");
                player.openGui(LetsModReboot.instance,0,world,x,y,z);
                //player.func_146101_a(tileEntitySoapstoneFurnace);
                player.func_146100_a(tileEntitySoapstoneFurnace);
            }
            return true;
        }

    }

    public static void updateSoapstoneFurnaceBlockState(boolean active, World worldObj, int xCoord, int yCoord, int zCoord)
    {
        int i = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
        TileEntity tileEntity = worldObj.getTileEntity(xCoord, yCoord, zCoord);
        keepInventory = true;

        if(active) {
            worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.soapstoneFurnaceActive);
        }else{
            worldObj.setBlock(xCoord, yCoord, zCoord, ModBlocks.soapstoneFurnace);
        }
        keepInventory = false;

        worldObj.setBlockMetadataWithNotify(xCoord,yCoord,zCoord,i,2);

        if(tileEntity != null) {
            tileEntity.validate();
            worldObj.setTileEntity(xCoord,yCoord,zCoord, tileEntity);
        }


    }
    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    public int getComparatorInputOverride(World world, int x, int y, int z) {
        return Container.calcRedstoneFromInventory((IInventory)world.getTileEntity(x,y,z));
    }


    public void breakBlock(World world, int x, int y, int z, Block oldBlock, int oldMetaData)
    {
        if(!keepInventory)
        {
            LogHelper.info("keepinventory ist false");
            TileEntitySoapstoneFurnace tileEntity = (TileEntitySoapstoneFurnace)world.getTileEntity(x,y,z);
            if (tileEntity != null)
            {
                LogHelper.info("tileEntity gefunden");
                for (int i = 0; i < tileEntity.getSizeInventory(); i++)
                {
                    ItemStack itemstack = tileEntity.getStackInSlot(i);

                    if (itemstack != null)
                    {
                        LogHelper.info("itemstack in furnace vorhanden");
                        float f = this.rand.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                        float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int j = this.rand.nextInt(21) + 10;

                            if (j > itemstack.stackSize)
                            {
                                j = itemstack.stackSize;
                            }

                            itemstack.stackSize -= j;
                            LogHelper.info("Itemstack reduziert");
                            EntityItem entityitem = new EntityItem(world, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                                LogHelper.info("itemstack hasTagCompound");
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)this.rand.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)this.rand.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)this.rand.nextGaussian() * f3);
                            world.spawnEntityInWorld(entityitem);
                            LogHelper.info("item gespawnt");
                        }
                    }
                }

                world.func_147453_f(x, y, z, oldBlock);
            }
        }
        LogHelper.info("breackblock super wird aufgerufen");

        super.breakBlock(world, x, y, z, oldBlock, oldMetaData);
    }


    public Block blockPicked(World world, int x, int y, int z) {
        return ModBlocks.soapstoneFurnace;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random random)
    {
        if (this.active)
        {
            int direction = world.getBlockMetadata(x,y,z);

            float x1 = (float) x + 0.5F;
            float y1 = (float) y + 0.5F;
            float z1 = (float) z + 0.5F;

            float f = 0.52F;
            float f1 = random.nextFloat() * 0.6F - 0.3F;

            if (direction == 4)
            {
                world.spawnParticle("smoke", (double) x1 - f, (double) y1, (double) z1 +f1, 0D, 0D, 0D );
                world.spawnParticle("flame", (double) x1 - f, (double) y1, (double) z1 +f1, 0D, 0D, 0D );
            }
            if (direction == 5)
            {
                world.spawnParticle("smoke", (double) x1 + f, (double) y1, (double) z1 +f1, 0D, 0D, 0D );
                world.spawnParticle("flame", (double) x1 + f, (double) y1, (double) z1 +f1, 0D, 0D, 0D );
            }
            if (direction == 2)
            {
                world.spawnParticle("smoke", (double) x1 + f1, (double) y1, (double) z1 - f, 0D, 0D, 0D );
                world.spawnParticle("flame", (double) x1 + f1, (double) y1, (double) z1 - f, 0D, 0D, 0D );
            }
            if (direction == 3)
            {
                world.spawnParticle("smoke", (double) x1 + f1, (double) y1, (double) z1 + f, 0D, 0D, 0D );
                world.spawnParticle("flame", (double) x1 + f1, (double) y1, (double) z1 + f, 0D, 0D, 0D );
            }
        }
    }
}
