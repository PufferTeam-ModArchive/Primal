package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.helper.ModelTESS;
import net.pufferlab.primal.client.models.ModelFaucet;
import net.pufferlab.primal.tileentities.TileEntityFaucet;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockFaucetRenderer implements ISimpleBlockRenderingHandler {

    ModelFaucet modelFaucet = new ModelFaucet();
    ModelTESS modelTESS = new ModelTESS();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        modelFaucet.render();
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        int renderPass = ForgeHooksClient.getWorldRenderPass();
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityFaucet tef) {
            int meta = tef.facingMeta;
            modelFaucet.setFacing(meta);
            modelTESS.dumpVertices(tess, x, y, z);
            FluidStack stack = tef.getFluidStack();
            if (renderPass == 1 && tef.isActive == true && tef.getInputTile() != null && tef.getExtractTile() != null) {
                modelTESS.renderFluid(renderer, tess, x, y, z, stack, 0.375, -0.8F, 0.375, 0.625, 0.125F, 0.625, true);
            } else if (renderPass == 0) {
                modelFaucet.render(renderer, tess, block, x, y, z, 0.0F, -0.2F, 0.0F, 99);
            }
        }
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getFaucetRenderID();
    }
}
