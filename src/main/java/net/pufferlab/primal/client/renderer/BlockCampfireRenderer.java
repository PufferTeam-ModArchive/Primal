package net.pufferlab.primal.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.models.ModelCampfire;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockCampfireRenderer implements ISimpleBlockRenderingHandler {

    ModelCampfire modelCampfire = new ModelCampfire();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        int meta = world.getBlockMetadata(x, y, z);
        modelCampfire.kindling.isHidden = true;
        modelCampfire.log1.isHidden = true;
        modelCampfire.log2.isHidden = true;
        modelCampfire.log3.isHidden = true;
        modelCampfire.log4.isHidden = true;
        if (meta >= 1) {
            modelCampfire.kindling.isHidden = false;
        }
        if (meta >= 2) {
            modelCampfire.log1.isHidden = false;
        }
        if (meta >= 3) {
            modelCampfire.log2.isHidden = false;
        }
        if (meta >= 4) {
            modelCampfire.log3.isHidden = false;
        }
        if (meta >= 5) {
            modelCampfire.log4.isHidden = false;
        }
        modelCampfire.render(renderer, tess, block, x, y, z, meta);
        renderer.drawCrossedSquares(block.getIcon(world, x, y, z, 98), x, y, z, 1.0F);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getCampfireRenderID();
    }
}
