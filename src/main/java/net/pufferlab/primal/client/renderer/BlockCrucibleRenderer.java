package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.BlockForge;
import net.pufferlab.primal.client.models.ModelCrucible;
import net.pufferlab.primal.client.models.ModelFluid;
import net.pufferlab.primal.tileentities.TileEntityCrucible;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockCrucibleRenderer extends BlockPrimalRenderer {

    private static final ThreadLocal<ModelCrucible> modelCrucibleThread = ThreadLocal.withInitial(ModelCrucible::new);
    private final ThreadLocal<ModelFluid> modelFluidThread = ThreadLocal.withInitial(ModelFluid::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelCrucible modelCrucible = modelCrucibleThread.get();
        ModelFluid modelFluid = modelFluidThread.get();

        Tessellator tess = Tessellator.instance;
        TileEntity te = world.getTileEntity(x, y, z);
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        if (te instanceof TileEntityCrucible tef) {
            FluidStack stack = tef.getFluidStack();
            float height = tef.getFillLevel(0.0625F, 0.600F);
            modelFluid.dumpVertices(tess, x, y, z);
            double o = 2 * Constants.modelConst;
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
                Block blockBelow = world.getBlock(x, y - 1, z);
                int blockBelowMeta = world.getBlockMetadata(x, y - 1, z);
                float offsetY = 0.0F;
                if (blockBelow instanceof BlockForge) {
                    offsetY = 0.125F + 0.0625F * (4 - blockBelowMeta);
                }
                int heatingLevel = Utils.getHeatingLevel(tef.temperature);
                int index = 99;
                if (heatingLevel > 0) {
                    index = 98;
                }
                modelCrucible.render(renderer, tess, block, x, y, z, 0.0F, -offsetY, 0.0F, index);
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
        return Primal.proxy.getCrucibleRenderID();
    }
}
