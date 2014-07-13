package com.prdev.letsmod.tileEntity;

import com.prdev.letsmod.blocks.SoapstoneFurnace;
import com.prdev.letsmod.reference.Reference;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Philipp on 25.06.2014.
 */
public class TileEntitySoapstoneFurnace extends TileEntity implements ISidedInventory{

    private String localizedName;
    //Source-Slot = 0, Fuel-Slot = 1, Result-Slot = 2
    private ItemStack[] slots = new ItemStack[3];

    private static int[] slots_top = new int[]{0};
    private static int[] slots_bottom = new int[]{2, 1};
    private static int[] slots_sides = new int[]{1, 2, 0};

    //
    public int furnaceSpeed =200;
    /** The number of ticks that the furnace will keep burning */
    public int furnaceBurnTime;
    /**
     * The number of ticks that a fresh copy of the currently-burning item would keep the furnace burning for
     */
    public int itemBurnTime;
    /** The number of ticks that the current item has been cooking for */
    public int cookTime;
    private String field_145958_o;

    public int getSizeInventory()
    {
        return this.slots.length;
    }

    public void updateEntity()
    {
        boolean flag0 = this.furnaceBurnTime > 0;
        boolean flag1 = false;

        if (this.isBurning())
        {
            furnaceBurnTime--;
        }
        if (!this.worldObj.isRemote)
        {
            if(this.furnaceBurnTime == 0 && this.canSmelt())  //if fuel is available but not currently burning, remove 1 fuel and set itemburntime and furnaceburntime
            {
                this.itemBurnTime = this.furnaceBurnTime = getItemBurnTime(slots[1]);
                
                if (this.furnaceBurnTime > 0)
                {
                    flag1 = true;
                    
                    slots[1].stackSize--;

                    if (this.slots[1].stackSize == 0)
                    {
                        this.slots[1] = this.slots[1].getItem().getContainerItem(this.slots[1]);
                    }
                }


            }
            if(flag0 != this.isBurning())
            {
                flag1 = true;
                SoapstoneFurnace.updateSoapstoneFurnaceBlockState(this.furnaceBurnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);

            }
        }
        if(this.isBurning() && this.canSmelt())
        {
            this.cookTime++;//_______________________hier die geschwindigkeit erhöhen?
            if(this.cookTime == this.furnaceSpeed) {
                this.cookTime = 0;
                this.smeltItem();
                flag1=true;
            }
        }else
        {
            this.cookTime=0;

        }

        if(flag1)
        {
            this.markDirty();
        }
    }
    /**
     * Turn one item from the furnace source stack into the appropriate smelted item in the furnace result stack
     */
    public void smeltItem()
    {
        if(this.canSmelt()) {
            ItemStack outputStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);

            if (this.slots[2] == null)
            {
                this.slots[2] = outputStack.copy();
            }else if (this.slots[2].isItemEqual(outputStack))
            {
                this.slots[2].stackSize += outputStack.stackSize;

            }
            this.slots[0].stackSize--;
            if(this.slots[0].stackSize <= 0)
            {
                this.slots[0] = null;
            }
        }
    }

    /**
     * Returns true if the furnace can smelt an item, i.e. has a source item, destination stack isn't full, etc.
     */
    private boolean canSmelt()
    {
        if(this.slots[0] == null) return false;
        else
        {
            ItemStack outputStack = FurnaceRecipes.smelting().getSmeltingResult(this.slots[0]);
            if(outputStack == null) return false;
            if(this.slots[2] == null) return true; //keine items im ausgabeslot
            if(!this.slots[2].isItemEqual(outputStack)) return false; //Schmelzergebnis ist ungleich item im ausgabeslot -> stop

            int result = this.slots[2].stackSize + outputStack.stackSize;

            return (result <= getInventoryStackLimit() && result <= outputStack.getMaxStackSize());

        }
    }

    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    @Override
    public ItemStack getStackInSlot(int var1)
    {
        return this.slots[var1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    public ItemStack decrStackSize(int var1, int var2)
    {
        if (this.slots[var1] != null) {
            ItemStack itemStack;

            if (this.slots[var1].stackSize <= var2) {
                itemStack = this.slots[var1];
                this.slots[var1] = null;
                return itemStack;
            }else {
                itemStack = this.slots[var1].splitStack(var2);
                if(slots[var1].stackSize == 0) slots[var1] = null;
                return itemStack;
            }

        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int var1)
    {
        if(slots[var1] != null) {
            ItemStack itemStack = this.slots[var1];
            this.slots[var1] = null;
            return itemStack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack)
    {
        slots[i] = itemStack;
        if(itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

    }

    @Override
    public String getInventoryName()
    {
        return this.hasCustomInventoryName() ? this.field_145958_o : "container.furnace";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return this.field_145958_o != null && this.field_145958_o.length() > 0;

    }

    public void func_145951_a(String p_145951_1_)
    {
        this.field_145958_o = p_145951_1_;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int i)
    {
        return this.cookTime * i / this.furnaceSpeed;
    }

    /**
     * Returns an integer between 0 and the passed value representing how much burn time is left on the current fuel
     * item, where 0 means that the item is exhausted and the passed value means that the item is fresh
     */
    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int i)
    {
        if (this.itemBurnTime == 0)
        {
            this.itemBurnTime = this.furnaceSpeed;
        }

        return this.furnaceBurnTime * i / this.itemBurnTime;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        this.slots = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.slots.length)
            {
                this.slots[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.furnaceBurnTime = nbt.getShort("BurnTime");
        this.cookTime = nbt.getShort("CookTime");
        this.itemBurnTime = getItemBurnTime(this.slots[1]);

        if (nbt.hasKey("CustomName"))
        {
            this.field_145958_o = nbt.getString("CustomName");
        }

    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        nbt.setShort("furnaceBurnTime", (short)this.furnaceBurnTime);
        nbt.setShort("cookTime", (short)this.cookTime);

        NBTTagList list = new NBTTagList();

        for(int i = 0; i<this.slots.length; i++)
        {
            if(this.slots[i] != null)
            {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setByte("Slot", (byte)i);
                this.slots[i].writeToNBT(compound);
                list.appendTag(compound);
            }
        }
        nbt.setTag("Items", list);

        if(this.hasCustomInventoryName())
        {
            nbt.setString("CustomName", this.localizedName);
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPLayer)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityPLayer.getDistanceSq((double)this.xCoord +0.5D, (double)this.yCoord +0.5D, (double)this.zCoord +0.5D) <= 64.0D ;
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack)
    {
        if (slot==2) return false;
        if (slot==1 && isItemFuel(itemStack)) return true;

        return false;
    }

    public boolean isItemFuel(ItemStack itemStack)
    {
        return getItemBurnTime(itemStack) > 0 ? true : false;
    }

    public static int getItemBurnTime(ItemStack itemStack)
    {
        if(itemStack == null) return 0;
        else
        {
            if (itemStack.getItem() instanceof ItemBlock && (GameRegistry.findBlock(Reference.MODID, itemStack.getUnlocalizedName())) != null)
            {
            Block block = GameRegistry.findBlock(Reference.MODID, itemStack.getUnlocalizedName());

            if (block.getMaterial() == Material.wood) return 300;
            if (block == Blocks.coal_block) return 16000;
            }
            if (itemStack.getItem()== Items.stick) return 120;
            if (itemStack.getItem()== Items.coal) return 1920;
            if (itemStack.getItem()== Items.lava_bucket) return 24000;
            if (itemStack.getItem()== Item.getItemFromBlock(Blocks.sapling)) return 120;
            if (itemStack.getItem()== Items.blaze_rod) return 2400;

            return GameRegistry.getFuelValue(itemStack) +100;
        }

    }

    public void setGuiDisplayName(String displayName) {
        this.localizedName = displayName;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1)
    {
        return var1 == 0 ? slots_bottom : (var1 == 1 ? slots_top : slots_sides);
    }

    @Override
    public boolean canInsertItem(int var1, ItemStack itemStack, int var3)
    {
        return this.isItemValidForSlot(var1, itemStack);
    }

    @Override
    public boolean canExtractItem(int var1, ItemStack var2, int var3)
    {
        return var3 != 0 || var1 == 2 || var2.getItem() == Items.bucket;
    }
}
