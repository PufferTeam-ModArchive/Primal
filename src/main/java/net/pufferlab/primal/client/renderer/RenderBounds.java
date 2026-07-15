package net.pufferlab.primal.client.renderer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.pufferlab.primal.blocks.BoundsType;
import net.pufferlab.primal.blocks.IPrimalBlock;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.utils.BoundingBox;
import net.pufferlab.primal.utils.CollideUtils;
import net.pufferlab.primal.utils.Line;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public class RenderBounds {

    public static final RenderBounds instance = new RenderBounds();

    public static boolean handleRendering(EntityPlayer player, MovingObjectPosition mop, float partialTicks) {
        return instance.handleRenderingImpl(player, mop, partialTicks);
    }

    private boolean handleRenderingImpl(EntityPlayer player, MovingObjectPosition mop, float partialTicks) {
        drawStructureSelectionBox(player, 0, partialTicks);
        Block block = player.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
        if (block instanceof IPrimalBlock block2) {
            drawSelectionBox(player, mop, 0, partialTicks);
            if (!block2.renderDefaultBounds()) {
                return false;
            }
        }
        return true;
    }

    private static final TIntObjectMap<AxisAlignedBB> aabbMap = new TIntObjectHashMap<>();
    private static final TIntObjectMap<BoundingBox> bbMap = new TIntObjectHashMap<>();

    public static void setTemporaryAABB(int id, AxisAlignedBB bb) {
        aabbMap.put(id, bb);
    }

    public static void setTemporaryBB(int id, BoundingBox bb) {
        bbMap.put(id, bb);
    }

    public void drawStructureSelectionBox(EntityPlayer player, int index, float partialTicks) {
        PlayerData data = PlayerData.get(player);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
        GL11.glLineWidth(4.0F);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(false);
        float f1 = 0.002F;

        double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
        double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
        double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
        if (data.hasValidSelection()) {
            AxisAlignedBB structureBB = data.getSelectionBB();
            RenderGlobal.drawOutlinedBoundingBox(
                structureBB.expand((double) f1, (double) f1, (double) f1)
                    .getOffsetBoundingBox(-d0, -d1, -d2),
                -1);
        }

        if (!aabbMap.isEmpty()) {
            for (AxisAlignedBB bb : aabbMap.valueCollection()) {
                RenderGlobal.drawOutlinedBoundingBox(
                    bb.expand((double) f1, (double) f1, (double) f1)
                        .getOffsetBoundingBox(-d0, -d1, -d2),
                    -1);
            }
        }
        if (!bbMap.isEmpty()) {
            for (BoundingBox bb : bbMap.valueCollection()) {
                BoundingBox bb2 = bb.expand((double) f1, (double) f1, (double) f1)
                    .getOffsetBoundingBox(-d0, -d1, -d2);
                drawOutlinedRotatedBoundingBox(bb2, -1);
            }
        }

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawOutlinedRotatedBoundingBox(BoundingBox bb, int index) {
        List<Line> lines = CollideUtils.getRotatedLines(bb);
        renderLines(lines);
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
                List<AxisAlignedBB> boxProcessed = new ArrayList<>();
                if (block instanceof IPrimalBlock block2) {
                    boxes = block2
                        .getBounds(player.worldObj, mop.blockX, mop.blockY, mop.blockZ, player, BoundsType.rendered);
                    if (boxes == null) return;
                    for (AxisAlignedBB box : boxes) {
                        boxProcessed.add(
                            box.getOffsetBoundingBox(mop.blockX, mop.blockY, mop.blockZ)
                                .expand((double) f1, (double) f1, (double) f1)
                                .getOffsetBoundingBox(-d0, -d1, -d2));
                    }
                }

                List<Line> lines = CollideUtils.getCleanedLines(boxProcessed);
                renderLines(lines);
            }

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    public static void renderLines(List<Line> lines) {
        if (lines == null || lines.isEmpty()) {
            return;
        }

        Tessellator tessellator = Tessellator.instance;

        tessellator.startDrawing(GL11.GL_LINES);

        for (Line line : lines) {
            if (line == null || line.a == null || line.b == null) {
                continue;
            }

            addVertex(tessellator, line.a);
            addVertex(tessellator, line.b);
        }

        tessellator.draw();
    }

    public static void addVertex(Tessellator tess, Vector3f v) {
        if (v == null) return;

        tess.addVertex(v.x, v.y, v.z);
    }

}
