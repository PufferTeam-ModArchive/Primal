package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelFluid;
import net.pufferlab.primal.client.models.ModelLargeVessel;
import net.pufferlab.primal.tileentities.TileEntityLargeVessel;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockLargeVesselRenderer extends BlockPrimalRenderer {

    private static final ThreadLocal<ModelLargeVessel> modelLargeVesselThread = ThreadLocal
        .withInitial(ModelLargeVessel::new);
    private final ThreadLocal<ModelFluid> modelFluidThread = ThreadLocal.withInitial(ModelFluid::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelLargeVessel modelLargeVessel = modelLargeVesselThread.get();
        ModelFluid modelFluid = modelFluidThread.get();

        Tessellator tess = Tessellator.instance;
        TileEntity te = world.getTileEntity(x, y, z);
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        if (te instanceof TileEntityLargeVessel tef) {
            FluidStack stack = tef.getFluidStack();
            float height = tef.getFillLevel(0.0625F, 0.600F);
            if (tef.isOpen) {
                modelLargeVessel.lid.isHidden = true;
            } else {
                modelLargeVessel.lid.isHidden = false;
            }
            modelFluid.dumpVertices(tess, x, y, z);
            double o = Constants.modelConst;
            if (renderPass == 1) {
                modelFluid.render(
                    renderer,
                    tess,
                    x,
                    y,
                    z,
                    stack,
                    0.1875F + o,
                    0.0625F,
                    0.1875F + o,
                    0.8125F - o,
                    height,
                    0.8125F - o,
                    false,
                    false);
            } else if (renderPass == 0) {
                modelLargeVessel.render(renderer, tess, block, x, y, z, 99);
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
        return Primal.proxy.getLargeVesselRenderID();
    }
}
