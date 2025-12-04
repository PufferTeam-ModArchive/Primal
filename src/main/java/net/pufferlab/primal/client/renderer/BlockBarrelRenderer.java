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

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockBarrelRenderer implements ISimpleBlockRenderingHandler {

    ModelBarrel modelBarrel = new ModelBarrel();
    ModelTESS modelTESS = new ModelTESS();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        modelBarrel.top.isHidden = false;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityBarrel tef) {
            int renderPass = ForgeHooksClient.getWorldRenderPass();
            FluidStack stack = tef.getFluidStack();
            float height = tef.getFillLevel(0.1875F, 0.875F);
            int meta = tef.facingMeta;
            modelBarrel.setFacing(meta);
            double offsetX = 0.0F;
            double offsetY = 0.0F;
            double offsetZ = 0.0F;
            modelBarrel.bb_main.rotateAngleX = 0.0F;
            if (tef.isFloorBarrel) {
                offsetY = 0.5F - 0.125F;
                if (tef.facingMeta == 1) {
                    offsetZ = 0.5F;
                }
                if (tef.facingMeta == 2) {
                    offsetX = 0.5F;
                }
                if (tef.facingMeta == 3) {
                    offsetZ = -0.5F;
                }
                if (tef.facingMeta == 4) {
                    offsetX = -0.5F;
                }
                modelBarrel.bb_main.rotateAngleX = (float) (Math.PI / 2);
            } else {
                modelBarrel.top.isHidden = true;
            }
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
            } else if (renderPass == 0) {
                Block blockAbove = world.getBlock(x, y + 1, z);
                if (blockAbove instanceof BlockBarrel) {
                    renderer.setRenderBounds(0.0F, 0.875F, 0.0F, 1.0F, 1.0F, 1.0F);
                    renderer.setOverrideBlockTexture(Blocks.planks.getIcon(0, 1));
                    renderer.renderStandardBlock(block, x, y, z);
                    renderer.setOverrideBlockTexture(null);
                }
                modelBarrel.render(renderer, tess, block, x, y, z, offsetX, offsetY, offsetZ, 99);
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
