package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public abstract class BlockPrimalRenderer implements ISimpleBlockRenderingHandler {

    public void renderStandardInvBlockColor(RenderBlocks renderblocks, Block block, int meta, float scale) {
        int j;
        float f1;
        float f2;
        float f3;

        j = block.getRenderColor(meta);

        f1 = (float) (j >> 16 & 255) / 255.0F;
        f2 = (float) (j >> 8 & 255) / 255.0F;
        f3 = (float) (j & 255) / 255.0F;
        GL11.glColor4f(f1 * scale, f2 * scale, f3 * scale, 1.0F);
        renderStandardInvBlock(renderblocks, block, meta);
    }

    public void renderStandardInvBlock(RenderBlocks renderblocks, Block block, int meta) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        renderblocks.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        renderblocks.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        renderblocks.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);
        renderblocks.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        renderblocks.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        renderblocks.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
        tessellator.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }

    public boolean renderStandardBlockNoColor(RenderBlocks renderer, Block blockType, int blockX, int blockY,
        int blockZ) {
        int l = 16777215;
        float f = (float) (l >> 16 & 255) / 255.0F;
        float f1 = (float) (l >> 8 & 255) / 255.0F;
        float f2 = (float) (l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable) {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }

        return Minecraft.isAmbientOcclusionEnabled() && blockType.getLightValue() == 0
            ? (renderer.partialRenderBounds
                ? renderer.renderStandardBlockWithAmbientOcclusionPartial(blockType, blockX, blockY, blockZ, f, f1, f2)
                : renderer.renderStandardBlockWithAmbientOcclusion(blockType, blockX, blockY, blockZ, f, f1, f2))
            : renderer.renderStandardBlockWithColorMultiplier(blockType, blockX, blockY, blockZ, f, f1, f2);
    }

}
