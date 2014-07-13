package com.prdev.letsmod.handler;

import com.prdev.letsmod.LetsModReboot;
import com.prdev.letsmod.container.ContainerIronOven;
import com.prdev.letsmod.container.ContainerSoapstoneFurnace;
import com.prdev.letsmod.gui.GuiIronOven;
import com.prdev.letsmod.gui.GuiSoapstoneFurnace;
import com.prdev.letsmod.tileEntity.TileEntityIronOven;
import com.prdev.letsmod.tileEntity.TileEntitySoapstoneFurnace;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Philipp on 25.06.2014.
 */
public class GuiHandler implements IGuiHandler
{


    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity entity = world.getTileEntity(x,y,z);
        //System.out.println("GuiHandler getServerguiElement ausgeführt");
        if(entity != null) {
            switch(ID) {
                case LetsModReboot.guiSoapstoneFurnace:
                    if(entity instanceof TileEntitySoapstoneFurnace) {
                        return new ContainerSoapstoneFurnace(player.inventory, (TileEntitySoapstoneFurnace) entity);
                    }
                case LetsModReboot.guiIronOven:
                    if(entity instanceof TileEntityIronOven) {
                        return new ContainerIronOven(player.inventory, (TileEntityIronOven) entity);
                    }
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity entity = world.getTileEntity(x,y,z);
        //System.out.println("GuiHandler getCLientGuiElement aufgerufen");
        //if(entity != null) {
            switch(ID) {
                case 0:
                    if(entity instanceof TileEntitySoapstoneFurnace) {
                        //System.out.println("getClientGuiHandler ausgefuhrt");
                        return new GuiSoapstoneFurnace(player.inventory, (TileEntitySoapstoneFurnace) entity);
                    }
                case LetsModReboot.guiIronOven:
                    if(entity instanceof TileEntityIronOven) {
                        //System.out.println("getClientGuiHandler ausgefuhrt");
                        return new GuiIronOven(player.inventory, (TileEntityIronOven) entity);
                    }
            }

        //}
        return null;
    }
}
