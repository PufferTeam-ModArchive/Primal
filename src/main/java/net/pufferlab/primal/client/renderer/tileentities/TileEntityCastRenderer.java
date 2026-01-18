package net.pufferlab.primal.client.renderer.tileentities;

import static net.pufferlab.primal.tileentities.TileEntityCast.*;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockCast;
import net.pufferlab.primal.tileentities.TileEntityCast;

import org.lwjgl.opengl.GL11;

public class TileEntityCastRenderer extends TileEntityPrimalRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        World world = tileEntity.getWorldObj();
        TileEntityCast mold = (TileEntityCast) tileEntity;
        Block block = world.getBlock(mold.xCoord, mold.yCoord, mold.zCoord);
        if (!(block instanceof BlockCast)) return;

        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot2 = mold.getInventoryStack(slotOutput);
        ItemStack slot3 = mold.getInventoryStack(slotOutputSmall);

        double xOffset = x + 0.5;
        double zOffset = z + 0.5;
        renderSlotItem(slot2, xOffset, y, zOffset);
        renderSlotItemSmall(slot3, xOffset, y, zOffset);
    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust) {
        GL11.glPushMatrix();
        if (stack != null) {
            updateItem(stack);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(xAdjust, yAdjust + 0.0625, zAdjust - 0.1725F);
            GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);

            GL11.glScalef(1.65F, 1.65F, 3.0F);

            renderFrameItem();
        }
        GL11.glPopMatrix();
    }

    public void renderSlotItemSmall(ItemStack stack, double xAdjust, double yAdjust, double zAdjust) {
        GL11.glPushMatrix();
        if (stack != null) {
            updateItem(stack);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(xAdjust, yAdjust + 0.0625, zAdjust - 0.1125F);
            GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);

            GL11.glScalef(1.2F, 1.2F, 3.0F);

            renderFrameItem();
        }
        GL11.glPopMatrix();
    }
}
