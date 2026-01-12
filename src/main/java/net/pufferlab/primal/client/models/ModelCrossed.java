package net.pufferlab.primal.client.models;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

public class ModelCrossed extends ModelPrimal {

    public ModelCrossed() {
        super(0);
    }

    public void render(RenderBlocks renderblocks, Tessellator tess, Block block, int x, int y, int z, int index) {
        modelTESS.renderCrossed(renderblocks, block, x, y, z, index);
    }

    public void render(RenderBlocks renderblocks, Block block, int x, int y, int z, int index) {
        modelTESS.renderCrossed(renderblocks, block, x, y, z, index);
    }
}
