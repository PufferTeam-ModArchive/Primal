package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockMetaOre;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockOreRenderer extends BlockPrimalRenderer {

    private final BlockMetaOre block1 = new BlockMetaOre();
    private final BlockMetaOre block2 = new BlockMetaOre();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();

        block2.blockTexture = (BlockMetaOre) block;
        boolean isEmissive = true;
        if (block instanceof IPrimalBlock block0) {
            isEmissive = block0.isEmissive();
        }

        block2.isInventory = true;
        block2.renderPass2 = 0;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderStandardInvBlock(renderer, block2, metadata);
        block2.renderPass2 = 1;
        if (isEmissive) {
            renderStandardInvBlockColorMaxBrightness(renderer, block2, metadata, 1.0F);
        } else {
            renderStandardInvBlock(renderer, block2, metadata);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        block2.renderPass2 = 0;
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        block1.blockTexture = (BlockMetaOre) block;
        boolean isEmissive = true;
        if (block instanceof IPrimalBlock block0) {
            isEmissive = block0.isEmissive();
        }

        block1.isInventory = false;
        block1.renderPass = 0;
        renderStandardBlockNoColor(renderer, block1, x, y, z);

        block1.renderPass = 1;
        if (isEmissive) {
            renderStandardBlockMaxBrightness(renderer, block1, x, y, z);
        } else {
            renderStandardBlockNoColor(renderer, block1, x, y, z);
        }
        block1.renderPass = 0;
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getOreRenderID();
    }
}
