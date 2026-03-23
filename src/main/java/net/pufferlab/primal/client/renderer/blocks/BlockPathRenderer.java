package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockPathRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();

        metadata = getValidMeta(block, metadata);

        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

        setInventory(true);
        setPass(0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderStandardInvBlock(renderer, block, metadata);
        setPass(1);
        renderStandardInvBlock(renderer, block, metadata);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        setPass(0);
        GL11.glPopMatrix();
        setInventory(false);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

        setInventory(false);
        setPass(0);
        renderStandardBlockNoColor(renderer, block, x, y, z);

        setPass(1);
        renderStandardBlockNoColor(renderer, block, x, y, z);
        setPass(0);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getPathRenderID();
    }
}
