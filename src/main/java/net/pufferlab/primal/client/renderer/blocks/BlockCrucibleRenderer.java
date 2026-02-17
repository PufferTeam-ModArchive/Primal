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
import net.pufferlab.primal.blocks.BlockForge;
import net.pufferlab.primal.client.models.ModelFluid;
import net.pufferlab.primal.tileentities.TileEntityCrucible;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockCrucibleRenderer extends BlockPrimalRenderer {

    private final ModelFluid modelFluid = new ModelFluid();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        // spotless:off
        Tessellator tess = Tessellator.instance;
        dumpVertices(tess, x, y, z);
        TileEntity te = world.getTileEntity(x, y, z);
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        if (te instanceof TileEntityCrucible tef) {
            Block blockBelow = tef.getWorldObj()
                .getBlock(tef.xCoord, tef.yCoord - 1, tef.zCoord);
            int blockBelowMeta = tef.getWorldObj()
                .getBlockMetadata(tef.xCoord, tef.yCoord - 1, tef.zCoord);
            float offsetY = 0.0F;
            if (blockBelow instanceof BlockForge) {
                offsetY = 0.125F + 0.0625F * (4 - blockBelowMeta);
            }
            FluidStack stack = tef.getFluidStack();
            float height = tef.getFillLevel(0.0625F, 0.600F);
            double o = 2 * Constants.modelConst;
            if (renderPass == 1) {
                modelFluid.setFacingOffset(0.0F, -offsetY, 0.0F);
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
            } else if (renderPass == 0) {}
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
