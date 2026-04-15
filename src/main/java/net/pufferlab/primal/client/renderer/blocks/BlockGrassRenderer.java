package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockGrassRenderer extends BlockPrimalRenderer {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();

        IPrimalBlock block0 = (IPrimalBlock) block;
        metadata = getValidMeta(block, metadata);

        block0.setInventory(true);
        block0.setPass(0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderStandardInvBlock(renderer, block, metadata);
        block0.setPass(1);
        renderStandardInvBlockColor(renderer, block, metadata, 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        block0.setPass(0);
        GL11.glPopMatrix();
        block0.setInventory(false);

        // Better Foliage
        block0.setPass(1);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Material material = world.getBlock(x, y + 1, z)
            .getMaterial();
        IPrimalBlock block0 = (IPrimalBlock) block;

        block0.setInventory(false);
        block0.setPass(0);
        renderStandardBlockNoColor(renderer, block, x, y, z);

        block0.setPass(1);
        if (material != Material.craftedSnow && material != Material.snow) {
            renderer.renderStandardBlock(block, x, y, z);
        } else {
            renderStandardBlockNoColor(renderer, block, x, y, z);
        }
        block0.setPass(0);

        // Better Foliage
        block0.setPass(1);
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }
}
