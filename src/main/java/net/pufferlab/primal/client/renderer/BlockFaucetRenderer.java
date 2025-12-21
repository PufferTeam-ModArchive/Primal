package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.client.models.ModelFaucet;
import net.pufferlab.primal.client.models.ModelFluid;
import net.pufferlab.primal.client.models.ModelValve;
import net.pufferlab.primal.tileentities.TileEntityBarrel;
import net.pufferlab.primal.tileentities.TileEntityFaucet;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockFaucetRenderer extends BlockPrimalRenderer {

    private final ThreadLocal<ModelFaucet> modelFaucetThread = ThreadLocal.withInitial(ModelFaucet::new);
    private final ThreadLocal<ModelValve> modelValveThread = ThreadLocal.withInitial(ModelValve::new);
    private final ThreadLocal<ModelFluid> modelFluidThread = ThreadLocal.withInitial(ModelFluid::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelFaucet modelFaucet = modelFaucetThread.get();
        ModelValve modelValve = modelValveThread.get();
        ModelFluid modelFluid = modelFluidThread.get();

        Tessellator tess = Tessellator.instance;
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityFaucet tef) {
            int meta = tef.facingMeta;
            FluidStack stack = tef.getFluidStack();
            FluidStack inputStack = tef.getFluidStackInput();
            if (stack != null) {
                if (!Utils.containsStack(stack, inputStack)) {
                    stack = null;
                }
            }
            TileEntity extractTe = tef.getExtractTile();
            if (extractTe instanceof TileEntityBarrel tef2) {
                float offset = 0.0F;
                if (!Utils.isSimpleAxisConnected(tef.facingMeta, tef2.facingMeta) || !tef2.isFloorBarrel) {
                    offset = 0.0625F;
                }
                modelFaucet.setFacingOffset(0.0F, -0.25F, -0.0625F - offset);
                modelValve.setFacingOffset(0.0F, -0.25F, -0.125F - offset);
                modelFluid.setFacingOffset(0.0F, 0.0F, -0.0625F - offset);
            } else {
                modelFaucet.setFacingOffset(0.0F, -0.1875F, 0.0F);
                modelValve.setFacingOffset(0.0F, -0.1875F, -0.0625F);
                modelFluid.setFacingOffset(0.0F, 0.0F, 0.0F);
            }
            modelValve.bb_main.rotateAngleY = 0;
            if (tef.isActive) {
                modelValve.bb_main.rotateAngleY = (float) (Math.PI / 4);
            }
            modelFaucet.setFacing(meta);
            modelValve.setFacing(meta);
            modelFluid.setFacing(meta);
            modelFluid.dumpVertices(tess, x, y, z);
            if (renderPass == 1 && tef.isActive == true && tef.getInputTile() != null && tef.getExtractTile() != null) {
                modelFluid.render(
                    renderer,
                    tess,
                    x,
                    y,
                    z,
                    stack,
                    0.375,
                    -0.8F * tef.flowLevel,
                    0.375,
                    0.625,
                    0.125F,
                    0.625,
                    true,
                    true);
            }
            if (renderPass == 0) {
                modelFaucet.render(renderer, tess, block, x, y, z, 99);
                modelValve.render(renderer, tess, block, x, y, z, 98);
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
        return Primal.proxy.getFaucetRenderID();
    }
}
