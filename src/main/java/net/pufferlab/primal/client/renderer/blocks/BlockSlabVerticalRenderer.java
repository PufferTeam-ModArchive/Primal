package net.pufferlab.primal.client.renderer.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.blocks.BlockCutSlabVertical;
import net.pufferlab.primal.blocks.BoundsType;
import net.pufferlab.primal.blocks.IPrimalBlock;
import net.pufferlab.primal.tileentities.TileEntityCut;
import net.pufferlab.primal.tileentities.TileEntityCutDouble;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockSlabVerticalRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        renderer.field_152631_f = true;
        if (block instanceof BlockCutSlabVertical slab) {
            if (slab.isFull) {
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
            }
        }
        renderStandardInvBlock(renderer, block, metadata);
        renderer.field_152631_f = false;
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        TileEntity te = world.getTileEntity(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (renderBreaking) {
            if (block instanceof IPrimalBlock block2) {
                List<AxisAlignedBB> list = block2.getBounds(player.worldObj, x, y, z, player, BoundsType.rendered);
                if (list != null) {
                    for (AxisAlignedBB bb : list) {
                        setRenderBounds(renderer, bb);
                    }
                    renderer.renderStandardBlock(block, x, y, z);
                    return true;
                }
            }
        }
        if (te instanceof TileEntityCutDouble) {
            renderer.field_152631_f = true;
            if (meta == 2) {
                renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 0));
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
                renderer.renderStandardBlock(block, x, y, z);

                renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 1));
                renderer.setRenderBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
                renderer.renderStandardBlock(block, x, y, z);

                renderer.setOverrideBlockTexture(null);
            } else if (meta == 3) {
                renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 0));
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
                renderer.renderStandardBlock(block, x, y, z);

                renderer.setOverrideBlockTexture(block.getIcon(world, x, y, z, 1));
                renderer.setRenderBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
                renderer.renderStandardBlock(block, x, y, z);

                renderer.setOverrideBlockTexture(null);
            }
            renderer.field_152631_f = false;
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
}
