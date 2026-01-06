package net.pufferlab.primal.client.renderer.tileentities;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockChoppingLog;
import net.pufferlab.primal.tileentities.TileEntityChoppingLog;

import org.lwjgl.opengl.GL11;

public class TileEntityChoppingLogRenderer extends TileEntityPrimalRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        World world = tileEntity.getWorldObj();
        TileEntityChoppingLog log = (TileEntityChoppingLog) tileEntity;
        Block block = world.getBlock(log.xCoord, log.yCoord, log.zCoord);
        if (!(block instanceof BlockChoppingLog)) return;

        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot = log.getInventoryStack(0);

        renderSlotItem(slot, x + 0.5, y + 0.90F, z + 0.5);
    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust) {
        GL11.glPushMatrix();
        if (stack != null) {
            updateItem(stack);

            GL11.glTranslated(xAdjust, yAdjust, zAdjust);
            GL11.glScalef(1.75F, 1.75F, 1.75F);

            renderFrameItem();
        }
        GL11.glPopMatrix();
    }
}
