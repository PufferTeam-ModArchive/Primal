package net.pufferlab.primal.client.renderer.tileentities;

import static net.pufferlab.primal.tileentities.TileEntityAnvil.slotInput;
import static net.pufferlab.primal.tileentities.TileEntityAnvil.slotOutput;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.blocks.BlockMetalAnvil;
import net.pufferlab.primal.tileentities.TileEntityAnvil;
import net.pufferlab.primal.utils.BlockUtils;

import org.lwjgl.opengl.GL11;

public class TileEntityAnvilRenderer extends TileEntityPrimalRenderer {

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        World world = tileEntity.getWorldObj();
        TileEntityAnvil anvil = (TileEntityAnvil) tileEntity;
        Block block = world.getBlock(anvil.xCoord, anvil.yCoord, anvil.zCoord);
        if (!(block instanceof BlockMetalAnvil)) return;

        this.itemRenderer.setRenderManager(renderManager);

        GL11.glEnable(GL11.GL_LIGHTING);
        ItemStack slot = anvil.getInventoryStack(slotInput);
        ItemStack slot2 = anvil.getInventoryStack(slotOutput);
        int facing = anvil.facingMeta;
        double x2 = x;
        double z2 = z;

        if (facing == 1) {
            z2 = z2 + 0.1;
        }
        if (facing == 2) {
            x2 = x2 + 0.1;
        }
        if (facing == 3) {
            z2 = z2 - 0.1;
        }
        if (facing == 4) {
            x2 = x2 - 0.1;
        }
        renderSlotItem(slot, x2 + 0.5, y + 0.75F, z2 + 0.5, facing);
        renderSlotItem(slot2, x2 + 0.5, y + 0.75F, z2 + 0.5, facing);
    }

    public void renderSlotItem(ItemStack stack, double xAdjust, double yAdjust, double zAdjust, int facing) {
        GL11.glPushMatrix();
        if (stack != null) {
            updateItem(stack);

            GL11.glTranslated(xAdjust, yAdjust, zAdjust);
            GL11.glRotatef(BlockUtils.getFacingAngleDegree(facing), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(90, 1.0F, 0.0F, 0.0F);

            renderFrameItem();
        }
        GL11.glPopMatrix();
    }
}
