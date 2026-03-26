package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockCropsPrimal;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockCropsRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        BlockCropsPrimal blockCropsPrimal = (BlockCropsPrimal) block;
        int renderType = blockCropsPrimal.cropType.cropRenderType;

        if (renderType == 0) {
            renderer.renderBlockCrops(block, x, y, z);
        }
        if (renderType == 1) {
            renderer.renderCrossedSquares(block, x, y, z);
        }
        if (renderType == 2) {
            renderCrossedSquares(renderer, block, x, y, z, 2.0F);
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
