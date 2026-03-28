package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.IPrimalBlock;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockCropsRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        int renderType = 0;
        if (block instanceof IPrimalBlock block2) {
            renderType = block2.getRenderShape();
        }

        if (renderType == Constants.crossedModel) {
            renderBlockCropsSimple(renderer, block, x, y, z, 1.0F);
        }
        if (renderType == Constants.crossedModel32) {
            renderBlockCropsSimple(renderer, block, x, y, z, 2.0F);
        }
        if (renderType == Constants.cropModel) {
            renderer.renderBlockCrops(block, x, y, z);
        }
        if (renderType == Constants.cropModel32) {
            renderBlockCrops(renderer, block, x, y, z, 2.0F);
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getCropsRenderID();
    }
}
