package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelFluid;
import net.pufferlab.primal.client.models.ModelMold;
import net.pufferlab.primal.tileentities.TileEntityCast;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockCastRenderer extends BlockPrimalRenderer {

    private final ThreadLocal<ModelFluid> modelFluidThread = ThreadLocal.withInitial(ModelFluid::new);
    private final ThreadLocal<ModelMold> modelMoldThread = ThreadLocal.withInitial(ModelMold::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelFluid modelFluid = modelFluidThread.get();
        ModelMold modelMold = modelMoldThread.get();

        Tessellator tess = Tessellator.instance;
        TileEntity te = world.getTileEntity(x, y, z);
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        if (te instanceof TileEntityCast tef) {
            FluidStack stack = tef.getFluidStack();
            float height = tef.getFillLevel(0.0625F, 0.125F);
            modelFluid.dumpVertices(tess, x, y, z);
            if (renderPass == 1) {
                modelFluid.render(
                    renderer,
                    tess,
                    x,
                    y,
                    z,
                    stack,
                    0.1875F,
                    0.0625F,
                    0.1875F,
                    0.8125F,
                    height,
                    0.8125F,
                    false,
                    false);
            } else if (renderPass == 0) {
                modelMold.render(renderer, tess, block, x, y, z, 99);
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
        return Primal.proxy.getCastRenderID();
    }
}
