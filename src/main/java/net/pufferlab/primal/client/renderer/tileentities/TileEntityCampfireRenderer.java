package net.pufferlab.primal.client.renderer.tileentities;

import static net.pufferlab.primal.tileentities.TileEntityCampfire.*;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockCampfire;
import net.pufferlab.primal.tileentities.TileEntityCampfire;
import net.pufferlab.primal.utils.FacingUtils;

import org.lwjgl.opengl.GL11;

public class TileEntityCampfireRenderer extends TileEntityPrimalRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        World world = tileEntity.getWorldObj();
        TileEntityCampfire kiln = (TileEntityCampfire) tileEntity;
        Block block = world.getBlock(kiln.xCoord, kiln.yCoord, kiln.zCoord);
        if (!(block instanceof BlockCampfire)) return;

        block.setBlockBoundsForItemRender();
        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot1 = kiln.getInventoryStack(slotItem1);
        ItemStack slot2 = kiln.getInventoryStack(slotItem2);
        ItemStack slot3 = kiln.getInventoryStack(slotItem3);
        ItemStack slot4 = kiln.getInventoryStack(slotItem4);

        double iX = 0.25F;
        double iZ = 0.75F;
        double offsetX = 0;
        double offsetZ = 0;
        int facing = kiln.facingMeta;
        double multiplier = 1;
        if (facing == 1 || facing == 2) {
            multiplier = -1;
            iX = 0.75F;
            iZ = 0.25F;
        }
        if (facing == 1 || facing == 3) {
            offsetX = 0.25 * multiplier;
            iX = iX - 0.1F * multiplier;
            iZ = iZ - 0.25F * multiplier;
        }
        if (facing == 2 || facing == 4) {
            offsetZ = -0.25 * multiplier;
            iX = iX + 0.25F * multiplier;
            iZ = iZ + 0.1F * multiplier;
        }
        double offsetY = 0.9F;

        renderSlotItem(slot1, x + iX, y + offsetY, z + iZ, facing);
        renderSlotItem(slot2, x + (offsetX * 1) + iX, y + offsetY, z + (offsetZ * 1) + iZ, facing);
        renderSlotItem(slot3, x + (offsetX * 2) + iX, y + offsetY, z + (offsetZ * 2) + iZ, facing);
        renderSlotItem(slot4, x + (offsetX * 3) + iX, y + offsetY, z + (offsetZ * 3) + iZ, facing);

    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust, int facing) {
        GL11.glPushMatrix();
        if (stack != null) {
            updateItem(stack);

            GL11.glTranslated(xAdjust, yAdjust, zAdjust);

            GL11.glRotatef(FacingUtils.getFacingAngleDegree(facing), 0.0F, 1.0F, 0.0F);
            renderFrameItem();
        }
        GL11.glPopMatrix();
    }
}
