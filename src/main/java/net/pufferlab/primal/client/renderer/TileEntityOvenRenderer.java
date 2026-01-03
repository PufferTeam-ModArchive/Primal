package net.pufferlab.primal.client.renderer;

import static net.pufferlab.primal.tileentities.TileEntityOven.*;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockOven;
import net.pufferlab.primal.tileentities.TileEntityOven;

import org.lwjgl.opengl.GL11;

public class TileEntityOvenRenderer extends TileEntityPrimalRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        World world = tileEntity.getWorldObj();
        TileEntityOven kiln = (TileEntityOven) tileEntity;
        Block block = world.getBlock(kiln.xCoord, kiln.yCoord, kiln.zCoord);
        if (!(block instanceof BlockOven)) return;

        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot1 = kiln.getInventoryStack(slotItem1);
        ItemStack slot2 = kiln.getInventoryStack(slotItem2);
        ItemStack slot3 = kiln.getInventoryStack(slotItem3);
        ItemStack slot4 = kiln.getInventoryStack(slotItem4);

        double xOffset = x + 0.25;
        double zOffset = z + 0.25;
        renderSlotItem(slot1, xOffset + 0.5, y, zOffset + 0.5);
        renderSlotItem(slot2, xOffset, y, zOffset + 0.5);
        renderSlotItem(slot3, xOffset + 0.5, y, zOffset);
        renderSlotItem(slot4, xOffset, y, zOffset);
    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust) {
        GL11.glPushMatrix();
        if (stack != null) {
            updateItem(stack);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslated(xAdjust, yAdjust + 1, zAdjust - 0.0975);
            GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);

            renderFrameItem();
        }
        GL11.glPopMatrix();
    }
}
