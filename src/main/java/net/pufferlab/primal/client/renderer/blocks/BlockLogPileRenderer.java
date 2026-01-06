package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityMetaFacing;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = false)
public class BlockLogPileRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        int metadata = world.getBlockMetadata(x, y, z);
        int next = (metadata) % 3;
        int layer = ((metadata + 1) / 3) % 3;
        TileEntity te = world.getTileEntity(x, y, z);
        boolean rotated = false;
        if (te instanceof TileEntityMetaFacing tef) {
            if (tef.facingMeta == 1 || tef.facingMeta == 3) {
                rotated = true;
            }
        }
        if (next >= 0) {
            float layerMax = getLogPileMax(layer);
            float layerMin = getLogPileMin(layer);
            if (metadata >= 8) {
                layerMin = 1.0F;
            }

            if (!rotated) {
                renderer.uvRotateTop = 1;
            } else {
                renderer.uvRotateTop = 0;
            }

            if (layerMin > 0.1F) {
                renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, layerMin, 1.0F);
                renderer.renderStandardBlock(block, x, y, z);
            }

            if (metadata < 8) {
                if (next == 1) {
                    if (rotated) {
                        renderer.setRenderBounds(0.3125F, layerMin, 0.0F, 0.6875F, layerMax, 1.0F);
                    } else {
                        renderer.setRenderBounds(0.0F, layerMin, 0.3125F, 1.0F, layerMax, 0.6875F);
                    }
                    renderer.renderStandardBlock(block, x, y, z);
                }
                if (next == 0 || next == 1) {
                    if (rotated) {
                        renderer.setRenderBounds(0.0F, layerMin, 0.0F, 0.3125F, layerMax, 1.0F);
                    } else {
                        renderer.setRenderBounds(0.0F, layerMin, 0.0F, 1.0F, layerMax, 0.3125F);
                    }
                    renderer.renderStandardBlock(block, x, y, z);
                }

            }
            renderer.uvRotateNorth = 0;
            renderer.uvRotateBottom = 0;
            renderer.uvRotateEast = 0;
            renderer.uvRotateSouth = 0;
            renderer.uvRotateWest = 0;
            renderer.uvRotateTop = 0;

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
