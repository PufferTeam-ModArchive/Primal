package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockMetaPath;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockPathRenderer extends BlockPrimalRenderer {

    private final BlockMetaPath block1 = new BlockMetaPath();
    private final BlockMetaPath block2 = new BlockMetaPath();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();

        block2.blockTexture = (BlockMetaPath) block;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

        block2.isInventory = true;
        block2.renderPass2 = 0;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderStandardInvBlock(renderer, block2, metadata);
        block2.renderPass2 = 1;
        renderStandardInvBlock(renderer, block2, metadata);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        block2.renderPass2 = 0;
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        block1.blockTexture = (BlockMetaPath) block;
        renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);

        block1.isInventory = false;
        block1.renderPass = 0;
        renderStandardBlockNoColor(renderer, block1, x, y, z);

        block1.renderPass = 1;
        renderStandardBlockNoColor(renderer, block1, x, y, z);
        block1.renderPass = 0;
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
