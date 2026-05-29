package net.pufferlab.primal.client.renderer;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.pufferlab.primal.blocks.BoundsType;
import net.pufferlab.primal.blocks.IPrimalBlock;
import net.pufferlab.primal.client.utils.ModelBound;
import net.pufferlab.primal.utils.Utils;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public class RenderBounds {

    public static final RenderBounds instance = new RenderBounds();

    public static boolean handleRendering(EntityPlayer player, MovingObjectPosition mop, float partialTicks) {
        return instance.handleRenderingImpl(player, mop, partialTicks);
    }

    private boolean handleRenderingImpl(EntityPlayer player, MovingObjectPosition mop, float partialTicks) {
        Block block = player.worldObj.getBlock(mop.blockX, mop.blockY, mop.blockZ);
        if (block instanceof IPrimalBlock block2) {
            drawSelectionBox(player, mop, 0, partialTicks);
            if (!block2.renderDefaultBounds()) {
                return false;
            }
        }
        return true;
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

                List<Line> lines = getLines(boxProcessed);
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

    public static class Line {

        Vector3f a;
        Vector3f b;
        public static double range = 0.02D;

        public Line(Vector3f a, Vector3f b) {
            this.a = a;
            this.b = b;
        }

        public boolean isClose(Line line) {

            boolean direct = Utils.isClose(this.a.x, line.a.x, range) && Utils.isClose(this.a.y, line.a.y, range)
                && Utils.isClose(this.a.z, line.a.z, range)
                &&

                Utils.isClose(this.b.x, line.b.x, range)
                && Utils.isClose(this.b.y, line.b.y, range)
                && Utils.isClose(this.b.z, line.b.z, range);

            if (direct) return true;

            boolean reversed = Utils.isClose(this.a.x, line.b.x, range) && Utils.isClose(this.a.y, line.b.y, range)
                && Utils.isClose(this.a.z, line.b.z, range)
                &&

                Utils.isClose(this.b.x, line.a.x, range)
                && Utils.isClose(this.b.y, line.a.y, range)
                && Utils.isClose(this.b.z, line.a.z, range);

            return reversed;
        }
    }

    public static List<Line> getLines(List<AxisAlignedBB> axisAlignedBBS) {
        List<Line> lines = new ArrayList<>();
        for (AxisAlignedBB axisAlignedBB : axisAlignedBBS) {
            processLines(lines, new ModelBound(axisAlignedBB).getCorners());
        }
        cleanLines(lines);
        return lines;
    }

    public static void processLines(List<Line> lines, Vector3f[] v) {
        // Bottom face
        putLine(lines, v[0], v[1]);
        putLine(lines, v[1], v[2]);
        putLine(lines, v[2], v[3]);
        putLine(lines, v[3], v[0]);

        // Top face
        putLine(lines, v[4], v[5]);
        putLine(lines, v[5], v[6]);
        putLine(lines, v[6], v[7]);
        putLine(lines, v[7], v[4]);

        // Vertical edges
        putLine(lines, v[0], v[4]);
        putLine(lines, v[1], v[5]);
        putLine(lines, v[2], v[6]);
        putLine(lines, v[3], v[7]);
    }

    public static void putLine(List<Line> lines, Vector3f a, Vector3f b) {
        lines.add(new Line(new Vector3f(a), new Vector3f(b)));
    }

    public static void cleanLines(List<Line> lines) {
        List<Line> copy = new ArrayList<>(lines);
        lines.clear();

        for (int i = 0; i < copy.size(); i++) {
            Line a = copy.get(i);

            boolean hasDuplicate = false;

            for (int j = 0; j < copy.size(); j++) {
                if (i == j) continue;

                if (a.isClose(copy.get(j))) {
                    hasDuplicate = true;
                    break;
                }
            }

            // only keep lines that have NO duplicates at all
            if (!hasDuplicate) {
                lines.add(a);
            }
        }
    }
}
