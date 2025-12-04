package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.helper.ModelTESS;
import net.pufferlab.primal.client.models.ModelBarrel;
import net.pufferlab.primal.tileentities.TileEntityBarrel;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockBarrelRenderer implements ISimpleBlockRenderingHandler {

    ModelBarrel modelBarrel = new ModelBarrel();
    ModelTESS modelTESS = new ModelTESS();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        modelBarrel.top.isHidden = false;
        modelBarrel.render();
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        modelBarrel.top.isHidden = false;
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityBarrel tef) {
            FluidStack stack = tef.getFluidStack();
            float height = tef.getFillLevel(0.1875F, 0.875F);
            modelTESS.renderFluid(
                renderer,
                tess,
                x,
                y,
                z,
                stack,
                0.125F + Constants.modelConst,
                0.1875F,
                0.125F + Constants.modelConst,
                0.875F - Constants.modelConst,
                height,
                0.875F - Constants.modelConst);
            if (!tef.isEmpty()) {
                modelBarrel.top.isHidden = true;
            }
        }
        modelBarrel.render(renderer, tess, block, x, y, z, 99);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getBarrelRenderID();
    }
}
