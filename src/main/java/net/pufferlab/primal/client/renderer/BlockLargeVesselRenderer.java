package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelLargeVessel;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockLargeVesselRenderer implements ISimpleBlockRenderingHandler {

    ModelLargeVessel modelLargeVessel = new ModelLargeVessel(0);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -0.5F, 0.0F);
        modelLargeVessel.render();
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        modelLargeVessel.render(renderer, tess, block, x, y, z, 99);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getLargeVesselRenderID();
    }
}
