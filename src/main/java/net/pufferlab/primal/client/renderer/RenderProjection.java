package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.network.packets.PacketChunkClient;

import org.lwjgl.opengl.GL11;

public class RenderProjection {

    public static final RenderProjection instance = new RenderProjection();

    public static boolean redirect = false;
    public static Block tempBlock;
    public static int tempX;
    public static int tempY;
    public static int tempZ;

    int count;

    public void renderISBRH(World world, int coordX, int coordY, int coordZ, int x, int y, int z,
        RenderBlocks renderBlocks, float partialTicks) {

        Tessellator tess = Tessellator.instance;
        GL11.glPushMatrix();

        tess.startDrawingQuads();

        tess.setTranslation(
            x - coordX - RenderManager.renderPosX,
            y - coordY - RenderManager.renderPosY,
            z - coordZ - RenderManager.renderPosZ);
        float angle = RenderMotion.getInterpolatedRotationDeg(1.0F, 0.0F, partialTicks);
        double difX = x - RenderManager.renderPosX;
        double difY = y - RenderManager.renderPosY;
        double difZ = z - RenderManager.renderPosZ;
        GL11.glTranslated(difX + 0.5F, difY + 0.5F, difZ + 0.5F);
        GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
        GL11.glTranslated(-difX - 0.5F, -difY - 0.5F, -difZ - 0.5F);
        int chunkX = coordX >> 4;
        int chunkZ = coordZ >> 4;
        count++;
        if (count > 10) {
            Primal.proxy.sendPacketToServer(new PacketChunkClient(chunkX, chunkZ));
            count = 0;
        }

        for (int ox = -8; ox < 8; ox++) {
            for (int oy = -8; oy < 8; oy++) {
                for (int oz = -8; oz < 8; oz++) {

                    int ax = x + ox;
                    int ay = y + oy;
                    int az = z + oz;
                    int cx = coordX + ox;
                    int cy = coordY + oy;
                    int cz = coordZ + oz;
                    Block block = world.getBlock(cx, cy, cz);
                    if (!world.blockExists(cx, cy, cz)) {
                        block = Blocks.command_block;
                    }

                    redirect = true;
                    tempBlock = block;
                    tempX = ax;
                    tempY = ay;
                    tempZ = az;
                    // Don't cull to avoid not visible faces
                    if (ox == -8 || ox == 7 || oy == -8 || oy == 7 || oz == -8 || oz == 7) {
                        renderBlocks.renderBlockAllFaces(block, cx, cy, cz);
                    } else {
                        renderBlocks.renderBlockByRenderType(block, cx, cy, cz);
                    }
                    redirect = false;
                }
            }
        }

        tess.draw();
        tess.setTranslation(0, 0, 0);
        GL11.glPopMatrix();
    }
}
