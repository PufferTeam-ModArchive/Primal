package net.pufferlab.primal.client.renderer;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.lwjgl.opengl.GL11;

public class RenderBounds {

    public static final RenderBounds instance = new RenderBounds();

    public static void handleRendering(EntityPlayer player, MovingObjectPosition mop, float partialTicks) {
        instance.handleRenderingImpl(player, mop, partialTicks);
    }

    private void handleRenderingImpl(EntityPlayer player, MovingObjectPosition mop, float partialTicks) {
        Block block = player.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
        if (block instanceof IPrimalBlock) {
            drawSelectionBox(player, mop, 0, partialTicks);
        }
    }

    public void drawSelectionBox(EntityPlayer player, MovingObjectPosition mop, int index, float partialTicks) {
        if (index == 0 && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(false);
            float f1 = 0.002F;
            Block block = player.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);

            if (block.getMaterial() != Material.air) {
                block.setBlockBoundsBasedOnState(player.worldObj, mop.blockX, mop.blockY, mop.blockZ);
                double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
                double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
                double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
                List<AxisAlignedBB> boxes = null;
                if (block instanceof IPrimalBlock block2) {
                    boxes = block2.getBounds(player.worldObj, mop.blockX, mop.blockY, mop.blockZ);
                }
                if (boxes == null) return;
                for (AxisAlignedBB box : boxes) {
                    RenderGlobal.drawOutlinedBoundingBox(
                        box.getOffsetBoundingBox(mop.blockX, mop.blockY, mop.blockZ)
                            .expand((double) f1, (double) f1, (double) f1)
                            .getOffsetBoundingBox(-d0, -d1, -d2),
                        -1);
                }
            }

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }
}
