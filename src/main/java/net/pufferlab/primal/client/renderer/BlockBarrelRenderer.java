package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockBarrel;
import net.pufferlab.primal.client.helper.ModelTESS;
import net.pufferlab.primal.client.models.ModelBarrel;
import net.pufferlab.primal.tileentities.TileEntityBarrel;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

@ThreadSafeISBRH(perThread = true)
public class BlockBarrelRenderer implements ISimpleBlockRenderingHandler {

    private final ThreadLocal<ModelBarrel> modelBarrelThread = ThreadLocal.withInitial(ModelBarrel::new);
    private final ThreadLocal<ModelTESS> modelTESSThread = ThreadLocal.withInitial(ModelTESS::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        ModelBarrel modelBarrel = modelBarrelThread.get();
        ModelTESS modelTESS = modelTESSThread.get();

        Tessellator tess = Tessellator.instance;
        modelBarrel.top.isHidden = false;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityBarrel tef) {
            int renderPass = ForgeHooksClient.getWorldRenderPass();
            FluidStack stack = tef.getFluidStack();
            FluidStack stackOutput = tef.getFluidStackOutput();
            float height = tef.getFillLevel(0.1875F, 0.875F);
            float heightOutput = tef.getFillLevelOutput(0.1875F, 0.875F);
            modelBarrel.bb_main.rotateAngleX = 0.0F;
            if (tef.isFloorBarrel) {
                modelBarrel.setFacingOffset(0.0F, 0.375F, -0.5F);
                modelBarrel.bb_main.rotateAngleX = (float) (Math.PI / 2);
            } else {
                modelBarrel.setFacingOffset(0.0F, 0.0F, 0.0F);
                modelBarrel.top.isHidden = true;
            }
            int meta = tef.facingMeta;
            modelBarrel.setFacing(meta);
            modelTESS.dumpVertices(tess, x, y, z);
            double o = Constants.modelConst;
            if (renderPass == 1 && !tef.isFloorBarrel) {
                modelTESS.renderFluid(
                    renderer,
                    tess,
                    x,
                    y,
                    z,
                    stack,
                    0.125F + o,
                    0.1875F,
                    0.125F + o,
                    0.875F - o,
                    height,
                    0.875F - o,
                    false);
                modelTESS.renderFluid(
                    renderer,
                    tess,
                    x,
                    y,
                    z,
                    stackOutput,
                    0.125F + o,
                    0.1875F,
                    0.125F + o,
                    0.875F - o,
                    heightOutput,
                    0.875F - o,
                    false);
            } else if (renderPass == 0) {
                Block blockAbove = world.getBlock(x, y + 1, z);
                if (blockAbove instanceof BlockBarrel) {
                    renderer.setRenderBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
                    renderer.setOverrideBlockTexture(Blocks.planks.getIcon(0, 1));
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setOverrideBlockTexture(null);
                }
                modelBarrel.render(renderer, tess, block, x, y, z, 99);
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
        return Primal.proxy.getBarrelRenderID();
    }
}
