package net.pufferlab.primal.client.renderer.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockMetaGrass;

import org.lwjgl.opengl.GL11;

import com.gtnewhorizons.angelica.api.ThreadSafeISBRH;

@ThreadSafeISBRH(perThread = true)
public class BlockGrassRenderer extends BlockPrimalRenderer {

    private final ThreadLocal<BlockMetaGrass> grassThread = ThreadLocal.withInitial(BlockMetaGrass::new);
    private final ThreadLocal<BlockMetaGrass> grassThread2 = ThreadLocal.withInitial(BlockMetaGrass::new);

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        GL11.glPushMatrix();

        BlockMetaGrass block2 = grassThread2.get();
        block2.blockTexture = (BlockMetaGrass) block;

        block2.isInventory = true;
        block2.renderPass2 = 0;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        renderStandardInvBlock(renderer, block2, metadata);
        block2.renderPass2 = 1;
        renderStandardInvBlockColor(renderer, block2, metadata, 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        block2.renderPass2 = 0;
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Material material = world.getBlock(x, y + 1, z)
            .getMaterial();

        BlockMetaGrass block2 = grassThread.get();
        block2.blockTexture = (BlockMetaGrass) block;

        block2.isInventory = false;
        block2.renderPass = 0;
        renderStandardBlockNoColor(renderer, block2, x, y, z);

        block2.renderPass = 1;
        if (material != Material.craftedSnow && material != Material.snow) {
            renderer.renderStandardBlock(block2, x, y, z);
        } else {
            renderStandardBlockNoColor(renderer, block2, x, y, z);
        }
        block2.renderPass = 0;
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getGrassRenderID();
    }
}
