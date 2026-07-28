package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.tileentities.TileEntityCut;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockButtonRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        renderer.field_152631_f = true;
        block.setBlockBoundsForItemRender();
        renderer.setRenderBoundsFromBlock(block);
        renderStandardInvBlock(renderer, block, metadata);
        renderer.field_152631_f = false;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        boolean flag = false;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityCut tef) {
            block.setBlockBoundsBasedOnState(world, x, y, z);
            flag = renderer.renderStandardBlock(block, x, y, z);
        }
        return flag;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }
}
