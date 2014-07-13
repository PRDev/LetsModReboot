package com.prdev.letsmod.container;

import com.prdev.letsmod.tileEntity.TileEntitySoapstoneFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.RecipesMapCloning;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 * Created by Philipp on 27.06.2014.
 */
public class ContainerSoapstoneFurnace extends Container
{
    private TileEntitySoapstoneFurnace soapstoneFurnace;
    public int lastFurnaceBurnTime;
    public int lastItemBurnTime;
    public int lastCookTime;

    public ContainerSoapstoneFurnace(InventoryPlayer inventoryPlayer, TileEntitySoapstoneFurnace entity)
    {
        //System.out.println("ContainerSoapstoneFurnace constructor aufgerufen");
        this.soapstoneFurnace = entity;
        this.addSlotToContainer(new Slot(entity, 0, 56, 17));
        this.addSlotToContainer(new Slot(entity, 1, 56, 53));
        this.addSlotToContainer(new SlotFurnace(inventoryPlayer.player, entity, 2, 116, 35));


        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    public boolean canInteractWith(EntityPlayer player) {
        //System.out.println("canInteractWith aufgerufen");
        return  this.soapstoneFurnace.isUseableByPlayer(player);
    }

    public void addCraftingToCrafters(ICrafting icrafting) {
        super.addCraftingToCrafters(icrafting);
        icrafting.sendProgressBarUpdate(this, 0, this.soapstoneFurnace.cookTime);
        icrafting.sendProgressBarUpdate(this, 1, this.soapstoneFurnace.furnaceBurnTime);
        icrafting.sendProgressBarUpdate(this, 2, this.soapstoneFurnace.itemBurnTime);
    }

    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for(int i=0; i<this.crafters.size(); i++)
        {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);
            if(this.lastCookTime != this.soapstoneFurnace.cookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.soapstoneFurnace.cookTime);
            }
            if(this.lastFurnaceBurnTime != this.soapstoneFurnace.furnaceBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.soapstoneFurnace.furnaceBurnTime);
            }
            if(this.lastItemBurnTime != this.soapstoneFurnace.itemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.soapstoneFurnace.itemBurnTime);
            }
        }
        this.lastCookTime = this.soapstoneFurnace.cookTime;
        this.lastFurnaceBurnTime = this.soapstoneFurnace.furnaceBurnTime;
        this.lastItemBurnTime = this.soapstoneFurnace.itemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int slot, int j) {
        if(slot == 0) this.soapstoneFurnace.cookTime = j;
        if(slot == 1) this.soapstoneFurnace.furnaceBurnTime = j;
        if(slot == 2) this.soapstoneFurnace.itemBurnTime = j;
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int clickedSlotNumber) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(clickedSlotNumber);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (clickedSlotNumber == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (clickedSlotNumber != 1 && clickedSlotNumber != 0)
            {

                if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null)
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return null;
                    }
                }
                else if (soapstoneFurnace.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return null;
                    }
                }
                else if (clickedSlotNumber >= 3 && clickedSlotNumber < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return null;
                    }
                }
                else if (clickedSlotNumber >= 30 && clickedSlotNumber < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}
