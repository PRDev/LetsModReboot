package com.prdev.letsmod.gui;

import com.prdev.letsmod.container.ContainerSoapstoneFurnace;
import com.prdev.letsmod.tileEntity.TileEntitySoapstoneFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


/**
 * Created by Philipp on 26.06.2014.
 */
@SideOnly(Side.CLIENT)
public class GuiSoapstoneFurnace extends GuiContainer
{
    public TileEntitySoapstoneFurnace soapstoneFurnace;
    private static final ResourceLocation textureLocation = new ResourceLocation("textures/gui/container/furnace.png");
            //Reference.MODID, "textures/gui/soapstoneFurnace.png");

    public GuiSoapstoneFurnace(InventoryPlayer inventoryPlayer, TileEntitySoapstoneFurnace entity)
    {
        super(new ContainerSoapstoneFurnace(inventoryPlayer, entity));
        this.soapstoneFurnace = entity;
        //this.xSize = 176;
        //this.ySize = 166;
        //System.out.println("GuiSoapStoneFurnace constructor aufgerufen");
    }

    public void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String name = this.soapstoneFurnace.hasCustomInventoryName() ? this.soapstoneFurnace.getInventoryName() : I18n.format(this.soapstoneFurnace.getInventoryName(), new Object());


        this.fontRendererObj.drawString(name, this.xSize / 2 -this.fontRendererObj.getStringWidth(name) /2, 6, 4210752);
        this.fontRendererObj.drawString(("Inventory"),8, this.ySize - 96 +2, 4210752 );

    }


    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3)
    {
        GL11.glColor4f(1F, 1F, 1F, 1F);
        this.mc.getTextureManager().bindTexture(textureLocation);
        //System.out.println("drawguicontainerback");
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int progress;

        if(this.soapstoneFurnace.isBurning())
        {
            progress = this.soapstoneFurnace.getBurnTimeRemainingScaled(12);
            drawTexturedModalRect(k + 56, l +36 + 12 - progress, 176, 12 - progress, 14, progress + 2);
        }

        progress = this.soapstoneFurnace.getCookProgressScaled(24);
        drawTexturedModalRect(guiLeft + 79, l + 34, 176, 14, progress+1, 16);
    }
}
