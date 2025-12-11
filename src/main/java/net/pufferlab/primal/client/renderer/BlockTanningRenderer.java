package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelTanning;
import net.pufferlab.primal.client.models.ModelTanningFrame;
import net.pufferlab.primal.tileentities.TileEntityTanning;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockTanningRenderer implements ISimpleBlockRenderingHandler {

    ModelTanningFrame modelTanningFrame = new ModelTanningFrame();
    ModelTanning modelTanning = new ModelTanning();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        modelTanning.render();
        modelTanningFrame.render();
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        TileEntityTanning tef = (TileEntityTanning) world.getTileEntity(x, y, z);
        double offsetX = 0.0F;
        double offsetY = -0.2217F;
        double offsetZ = 0.0F;
        if (tef.facingMeta == 1) {
            offsetZ = 0.45F;
        }
        if (tef.facingMeta == 2) {
            offsetX = 0.45F;
        }
        if (tef.facingMeta == 3) {
            offsetZ = -0.45F;
        }
        if (tef.facingMeta == 4) {
            offsetX = -0.45F;
        }
        modelTanning.setFacing(tef.facingMeta);
        modelTanningFrame.setFacing(tef.facingMeta);
        modelTanning.render(renderer, tess, block, x, y, z, 99);
        modelTanningFrame.render(renderer, tess, block, x, y, z, offsetX, offsetY, offsetZ, 99);

        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getTanningRenderID();
    }
}
