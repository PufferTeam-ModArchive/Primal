package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockOreRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();

        metadata = getValidMeta(block, metadata);

        boolean isEmissive = true;
        if (block instanceof IPrimalBlock block0) {
            isEmissive = block0.isEmissive();
        }

        setInventory(true);
        setPass(0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderStandardInvBlock(renderer, block, metadata);
        setPass(1);
        if (isEmissive) {
            renderStandardInvBlockColorMaxBrightness(renderer, block, metadata, 1.0F);
        } else {
            renderStandardInvBlock(renderer, block, metadata);
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        setPass(0);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        boolean isEmissive = true;
        if (block instanceof IPrimalBlock block0) {
            isEmissive = block0.isEmissive();
        }

        setInventory(false);
        setPass(0);
        renderStandardBlockNoColor(renderer, block, x, y, z);

        setPass(1);
        if (isEmissive) {
            renderStandardBlockMaxBrightness(renderer, block, x, y, z);
        } else {
            renderStandardBlockNoColor(renderer, block, x, y, z);
        }
        setPass(0);
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
