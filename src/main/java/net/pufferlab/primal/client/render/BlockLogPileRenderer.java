package net.pufferlab.primal.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockLogPileRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        int metadata = world.getBlockMetadata(x, y, z);
        int next = (metadata) % 3;
        int layer = (metadata / 3) % 3;
        if (next >= 0) {
            float nextWidth = getLogPileMax(next);
            float layerMax = getLogPileMax(layer);
            float layerMinY = getLogPileMin(layer);
            if (metadata >= 8) {
                layerMinY = 1.0F;
            }

            renderer.uvRotateNorth = 3;

            if (layerMinY > 0.1F) {
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, layerMinY, 1.0F);
                renderer.renderStandardBlock(block, x, y, z);
            }

            if (metadata < 8) {
                renderer.setRenderBounds(0.0F, layerMinY, 0.0F, nextWidth, layerMax, 1.0F);
                renderer.renderStandardBlock(block, x, y, z);
            }

            return true;
        }
        return false;
    }

    private float getLogPileMax(int i) {
        float cut = 0.3125F;
        if (i == 1) {
            cut = 0.6875F;
        }
        if (i == 2) {
            cut = 1.0F;
        }
        return cut;
    }

    private float getLogPileMin(int i) {
        float cut = 0.0F;
        if (i == 1) {
            cut = 0.3125F;
        }
        if (i == 2) {
            cut = 0.6875F;
        }
        return cut;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getLogPileRenderID();
    }
}
