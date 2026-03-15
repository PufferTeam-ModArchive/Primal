package net.pufferlab.primal.client.renderer.blocks;

import static net.pufferlab.primal.blocks.BlockBloomery.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelBloomery;
import net.pufferlab.primal.tileentities.TileEntityBloomery;

public class BlockBloomeryRenderer extends BlockPrimalRenderer {

    private final ModelBloomery modelBloomery = new ModelBloomery();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        TileEntity te = world.getTileEntity(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        meta = getValidMeta(block, meta);
        if (te instanceof TileEntityBloomery tef) {
            modelBloomery.setFacing(tef.facingMeta);
            modelBloomery.render(renderer, tess, block, x, y, z, iconBloomery);
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getBloomeryRenderID();
    }
}
