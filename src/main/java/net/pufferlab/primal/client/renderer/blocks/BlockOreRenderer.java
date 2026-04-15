package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockOreRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();

        IPrimalBlock block0 = (IPrimalBlock) block;
        metadata = getValidMeta(block, metadata);

        boolean isEmissive = block0.isEmissive();

        block0.setInventory(true);
        block0.setPass(0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderStandardInvBlock(renderer, block, metadata);
        block0.setPass(1);
        if (isEmissive) {
            renderStandardInvBlockColorMaxBrightness(renderer, block, metadata, 1.0F);
        } else {
            renderStandardInvBlock(renderer, block, metadata);
        }
        block0.setPass(2);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderStandardInvBlock(renderer, block, metadata);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        block0.setPass(0);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        IPrimalBlock block0 = (IPrimalBlock) block;
        boolean isEmissive = block0.isEmissive();

        int worldPass = getWorldRenderPass();
        block0.setInventory(false);
        if (worldPass == 0) {
            block0.setPass(0);
            renderStandardBlockNoColor(renderer, block, x, y, z);

            block0.setPass(1);
            if (isEmissive) {
                renderStandardBlockMaxBrightness(renderer, block, x, y, z);
            } else {
                renderStandardBlockNoColor(renderer, block, x, y, z);
            }
            block0.setPass(0);
            return true;
        } else if (worldPass == 1) {
            block0.setPass(2);
            setRenderBounds(renderer);
            renderStandardBlockNoColor(renderer, block, x, y, z);
            restoreRenderBounds(renderer);
            block0.setPass(0);
            return true;
        }

        return false;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }
}
