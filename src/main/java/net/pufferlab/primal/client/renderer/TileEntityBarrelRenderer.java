package net.pufferlab.primal.client.renderer;

import static net.pufferlab.primal.tileentities.TileEntityBarrel.*;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockBarrel;
import net.pufferlab.primal.tileentities.TileEntityBarrel;

import org.lwjgl.opengl.GL11;

public class TileEntityBarrelRenderer extends TileEntityPrimalRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        World world = tileEntity.getWorldObj();
        TileEntityBarrel barrel = (TileEntityBarrel) tileEntity;
        Block block = world.getBlock(barrel.xCoord, barrel.yCoord, barrel.zCoord);
        if (!(block instanceof BlockBarrel)) return;

        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot = barrel.getInventoryStack(slotInput);
        ItemStack slot2 = barrel.getInventoryStack(slotOutput);

        if (!barrel.isFloorBarrel) {
            renderSlotItem(slot, x + 0.5, y + 0.50F, z + 0.5);
            renderSlotItem(slot2, x + 0.5, y + 0.50F, z + 0.5);
        }
    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust) {
        GL11.glPushMatrix();
        if (stack != null) {
            updateItem(stack);

            GL11.glTranslated(xAdjust, yAdjust, zAdjust);

            renderFrameItem();
        }
        GL11.glPopMatrix();
    }
}
