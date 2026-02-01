package net.pufferlab.primal.client.renderer.blocks;

import static net.pufferlab.primal.blocks.BlockAxle.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelBracket;
import net.pufferlab.primal.tileentities.TileEntityAxle;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockAxleRenderer extends BlockPrimalRenderer {

    private final ModelBracket modelBracket = new ModelBracket();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityAxle tef) {
            Tessellator tess = Tessellator.instance;
            if (tef.hasBracket) {
                modelBracket.setFacingFromAxis(tef.facingMeta, tef.axisMeta);
                modelBracket.render(renderer, tess, block, x, y, z, iconAxle);
            }
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getAxleRenderID();
    }
}
