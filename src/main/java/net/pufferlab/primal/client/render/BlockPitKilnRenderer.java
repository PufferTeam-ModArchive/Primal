package net.pufferlab.primal.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockPitKilnRenderer implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 0) {
            renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        }
        if (meta >= 1 && meta <= 5) {
            renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F * meta, 1.0F);
        }
        if (meta >= 6) {
            float cut = 0.3125F;
            if (meta == 7) {
                cut = 0.6875F;
            }
            if (meta == 8) {
                cut = 1.0F;
            }
            renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
            renderer.renderStandardBlock(block, x, y, z);
            renderer.setRenderBounds(0.0F, 0.625F, 0.0F, cut, 1.0F, 1.0F);
        }
        renderer.renderStandardBlock(block, x, y, z);

        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getPitKilnRenderID();
    }
}
