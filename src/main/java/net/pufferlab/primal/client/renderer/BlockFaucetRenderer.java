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
import net.pufferlab.primal.client.helper.ModelTESS;
import net.pufferlab.primal.client.models.ModelFaucet;
import net.pufferlab.primal.client.models.ModelValve;
import net.pufferlab.primal.tileentities.TileEntityBarrel;
import net.pufferlab.primal.tileentities.TileEntityFaucet;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockFaucetRenderer implements ISimpleBlockRenderingHandler {

    ModelFaucet modelFaucet = new ModelFaucet();
    ModelValve modelValve = new ModelValve();
    ModelTESS modelTESS = new ModelTESS();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityFaucet tef) {
            int meta = tef.facingMeta;
            modelFaucet.setFacing(meta);
            FluidStack stack = tef.getFluidStack();
            FluidStack inputStack = tef.getFluidStackInput();
            if(stack != null) {
                if(!Utils.containsStack(stack, inputStack)) {
                    stack = null;
                }
            }
            double offsetX = 0.0F;
            double offsetY = -0.1875F;
            double offsetZ = 0.0F;
            double vOffsetX = 0.0F;
            double vOffsetZ = 0.0F;
            TileEntity extractTe = tef.getExtractTile();
            if (extractTe instanceof TileEntityBarrel tef2) {
                offsetY = -0.25F;
                float offset = 0.0F;
                if (!Utils.isSimpleAxisConnected(tef.facingMeta, tef2.facingMeta) || !tef2.isFloorBarrel) {
                    offset = 0.0625F;
                }
                if (tef.facingMeta == 1) {
                    offsetZ = 0.0625F + offset;
                }
                if (tef.facingMeta == 2) {
                    offsetX = 0.0625F + offset;
                }
                if (tef.facingMeta == 3) {
                    offsetZ = -0.0625F - offset;
                }
                if (tef.facingMeta == 4) {
                    offsetX = -0.0625F - offset;
                }
            }
            if (tef.facingMeta == 1) {
                vOffsetZ = 0.0625F;
            }
            if (tef.facingMeta == 2) {
                vOffsetX = 0.0625F;
            }
            if (tef.facingMeta == 3) {
                vOffsetZ = -0.0625F;
            }
            if (tef.facingMeta == 4) {
                vOffsetX = -0.0625F;
            }
            modelValve.bb_main.rotateAngleY = 0;
            if (tef.isActive) {
                modelValve.bb_main.rotateAngleY = (float) (Math.PI / 4);
            }
            modelTESS.dumpVertices(tess, x, y, z);
            if (renderPass == 1 && tef.isActive == true && tef.getInputTile() != null && tef.getExtractTile() != null) {
                modelTESS.renderFluid(
                    renderer,
                    tess,
                    x,
                    y,
                    z,
                    stack,
                    0.375 + offsetX,
                    -0.8F * tef.flowLevel,
                    0.375 + offsetZ,
                    0.625 + offsetX,
                    0.125F,
                    0.625 + offsetZ,
                    true,
                    true);
            }
            if (renderPass == 0) {
                modelFaucet.render(renderer, tess, block, x, y, z, offsetX, offsetY, offsetZ, 99);
                modelValve.render(renderer, tess, block, x, y, z, offsetX + vOffsetX, offsetY, offsetZ + vOffsetZ, 98);
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
