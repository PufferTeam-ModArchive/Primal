package net.pufferlab.primal.client.renderer.blocks;

import static net.pufferlab.primal.blocks.BlockGenerator.*;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelGenerator;
import net.pufferlab.primal.tileentities.TileEntityMotion;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockGeneratorRenderer extends BlockPrimalRenderer {

    private final ThreadLocal<ModelGenerator> modelGeneratorThread = ThreadLocal.withInitial(ModelGenerator::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelGenerator modelGenerator = modelGeneratorThread.get();

        Tessellator tess = Tessellator.instance;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityMotion tef) {
            int axis = tef.axisMeta;
            modelGenerator.setAxis(axis);
            modelGenerator.render(renderer, tess, block, x, y, z, 0.0F, 0.5F, 0.0F, iconGenerator);
        }

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getGeneratorRenderID();
    }
}
