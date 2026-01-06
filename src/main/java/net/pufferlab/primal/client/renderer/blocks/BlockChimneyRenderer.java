package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = false)
public class BlockChimneyRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        float t = 0.1875F;
        GL11.glPushMatrix();
        renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, t);
        renderStandardInvBlock(renderer, block, metadata);
        renderer.setRenderBounds(0.0F, 0.0F, 1.0F - t, 1.0F, 1.0F, 1.0F);
        renderStandardInvBlock(renderer, block, metadata);
        renderer.setRenderBounds(0.0F, 0.0F, t, t, 1.0F, 1.0F - t);
        renderStandardInvBlock(renderer, block, metadata);
        renderer.setRenderBounds(1.0F - t, 0.0F, t, 1.0F, 1.0F, 1.0F - t);
        renderStandardInvBlock(renderer, block, metadata);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        float t = 0.1875F;
        renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, t);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(0.0F, 0.0F, 1.0F - t, 1.0F, 1.0F, 1.0F);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(0.0F, 0.0F, t, t, 1.0F, 1.0F - t);
        renderer.renderStandardBlock(block, x, y, z);
        renderer.setRenderBounds(1.0F - t, 0.0F, t, 1.0F, 1.0F, 1.0F - t);
        renderer.renderStandardBlock(block, x, y, z);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getChimneyRenderID();
    }
}
