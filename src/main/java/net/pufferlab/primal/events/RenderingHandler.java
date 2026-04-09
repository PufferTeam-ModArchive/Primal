package net.pufferlab.primal.events;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.blocks.IPrimalBlock;
import net.pufferlab.primal.client.renderer.RenderAccessory;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class RenderingHandler {

    @SubscribeEvent
    public void renderPlayer(RenderPlayerEvent.Specials.Pre event) {
        if (Config.wearableRenderer.getBoolean()) {
            RenderAccessory.handleRendering(event.entityLiving, event.renderer);
        }
    }

    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event) {
        Block block = event.player.worldObj.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
        if (block instanceof IPrimalBlock) {
            drawSelectionBox(event.player, event.target, 0, event.partialTicks);
        }
    }

    public void drawSelectionBox(EntityPlayer p_72731_1_, MovingObjectPosition p_72731_2_, int p_72731_3_,
        float p_72731_4_) {
        if (p_72731_3_ == 0 && p_72731_2_.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
            GL11.glLineWidth(2.0F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(false);
            float f1 = 0.002F;
            Block block = p_72731_1_.worldObj.getBlock(p_72731_2_.blockX, p_72731_2_.blockY, p_72731_2_.blockZ);

            if (block.getMaterial() != Material.air) {
                AxisAlignedBB mask = AxisAlignedBB.getBoundingBox(
                    p_72731_2_.blockX,
                    p_72731_2_.blockY,
                    p_72731_2_.blockZ,
                    p_72731_2_.blockX + 1,
                    p_72731_2_.blockY + 1,
                    p_72731_2_.blockZ + 1);
                block.setBlockBoundsBasedOnState(
                    p_72731_1_.worldObj,
                    p_72731_2_.blockX,
                    p_72731_2_.blockY,
                    p_72731_2_.blockZ);
                double d0 = p_72731_1_.lastTickPosX + (p_72731_1_.posX - p_72731_1_.lastTickPosX) * (double) p_72731_4_;
                double d1 = p_72731_1_.lastTickPosY + (p_72731_1_.posY - p_72731_1_.lastTickPosY) * (double) p_72731_4_;
                double d2 = p_72731_1_.lastTickPosZ + (p_72731_1_.posZ - p_72731_1_.lastTickPosZ) * (double) p_72731_4_;
                List<AxisAlignedBB> boxes = null;
                if (block instanceof IPrimalBlock block2) {
                    boxes = block2
                        .getBounds(p_72731_1_.worldObj, p_72731_2_.blockX, p_72731_2_.blockY, p_72731_2_.blockZ);
                }
                if (boxes == null) return;
                for (AxisAlignedBB box : boxes) {
                    RenderGlobal.drawOutlinedBoundingBox(
                        box.getOffsetBoundingBox(p_72731_2_.blockX, p_72731_2_.blockY, p_72731_2_.blockZ)
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
