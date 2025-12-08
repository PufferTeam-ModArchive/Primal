package net.pufferlab.primal.client.renderer;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockGround;
import net.pufferlab.primal.client.models.ModelRock;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockGroundRenderer implements ISimpleBlockRenderingHandler {

    ModelRock modelRock = new ModelRock();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {}

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        Tessellator tess = Tessellator.instance;
        long seed = x * 3129871L ^ z * 116129781L ^ (long)y * 42317861L;
        Random rand = new Random(seed);

        int meta = world.getBlockMetadata(x, y, z);
        float range = 0.3F;
        float ox = (rand.nextFloat() * 2F - 1F) * range;
        float oz = (rand.nextFloat() * 2F - 1F) * range;
        int rotation = 1 + rand.nextInt(4);
        modelRock.setFacing(rotation);
        modelRock.render(renderer, tess, block, x, y, z, ox, 0.0F, oz, meta);


        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    @Override
    public int getRenderId() {
        return Primal.proxy.getGroundRenderID();
    }
}
