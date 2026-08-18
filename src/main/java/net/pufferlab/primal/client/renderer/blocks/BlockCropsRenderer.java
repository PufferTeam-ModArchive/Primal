package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.blocks.IPrimalBlock;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockCropsRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        boolean flag = false;
        int renderType = 0;
        int renderPass = getWorldRenderPass();

        boolean isSnowlogged = false;
        boolean isWaterlogged = false;
        if (block instanceof IPrimalBlock block2) {
            int meta = world.getBlockMetadata(x, y, z);
            renderType = block2.getRenderShape(meta);
            isSnowlogged = block2.isSnowlogged(meta);
            isWaterlogged = block2.isWaterlogged(world, x, y, z);
        }

        if (renderPass == 1) {
            if (isWaterlogged) {
                flag = renderer.renderBlockByRenderType(Blocks.water, x, y, z);
            }
        }

        if (renderPass == 0) {
            if (isSnowlogged) {
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
                renderer.setOverrideBlockTexture(Blocks.snow.getIcon(0, 0));
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setOverrideBlockTexture(null);
                flag = true;
            }

            if (renderType == Constants.crossedModel) {
                return renderBlockCropsSimple(renderer, block, x, y, z, 1.0F);
            }
            if (renderType == Constants.crossedModel32) {
                return renderBlockCropsSimple(renderer, block, x, y, z, 2.0F);
            }
            if (renderType == Constants.cropModel) {
                return renderer.renderBlockCrops(block, x, y, z);
            }
            if (renderType == Constants.cropModel32) {
                return renderBlockCrops(renderer, block, x, y, z, 2.0F);
            }
        }

        return flag;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }
}
