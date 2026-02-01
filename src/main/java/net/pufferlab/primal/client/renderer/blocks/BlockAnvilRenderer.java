package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelAnvil;
import net.pufferlab.primal.tileentities.TileEntityAnvil;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockAnvilRenderer extends BlockPrimalRenderer {

    private final ThreadLocal<ModelAnvil> modelAnvilThread = ThreadLocal.withInitial(ModelAnvil::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelAnvil modelAnvil = modelAnvilThread.get();
        Tessellator tess = Tessellator.instance;
        TileEntity te = world.getTileEntity(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if (te instanceof TileEntityAnvil tef) {
            modelAnvil.setFacing(tef.facingMeta);
            modelAnvil.render(renderer, tess, block, x, y, z, 100 + meta);
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getAnvilRenderID();
    }
}
