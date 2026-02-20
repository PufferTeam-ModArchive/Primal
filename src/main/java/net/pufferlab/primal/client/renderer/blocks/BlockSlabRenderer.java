package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockCutSlab;
import net.pufferlab.primal.tileentities.TileEntityCut;
import net.pufferlab.primal.tileentities.TileEntityCutDouble;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockSlabRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        renderer.field_152631_f = true;
        if (block instanceof BlockCutSlab slab) {
            if (slab.isFull) {
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
        }
        renderStandardInvBlock(renderer, block, metadata);
        renderer.field_152631_f = false;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCutDouble) {
            if (renderer.hasOverrideBlockTexture()) {
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                renderer.renderStandardBlock(block, x, y, z);
            } else {
                renderer.field_152631_f = true;
                renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 0));
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
                renderer.renderStandardBlock(block, x, y, z);

                renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 1));
                renderer.setRenderBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setOverrideBlockTexture(null);
                renderer.field_152631_f = false;
            }
            return true;
        }
        if (te instanceof TileEntityCut tef) {
            renderer.field_152631_f = true;
            renderer.renderStandardBlock(block, x, y, z);
            renderer.field_152631_f = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getSlabRenderID();
    }
}
