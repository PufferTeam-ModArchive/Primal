package net.pufferlab.primal.client.models.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

public class ModelFace extends ModelPrimal {

    public ModelFace() {
        super(0);
    }

    public boolean isHidden = false;

    public void render(RenderBlocks renderblocks, Tessellator tess, Block block, int x, int y, int z, int index) {
        if (!isHidden) {
            modelTESS.renderFace(renderblocks, block, x, y, z, 0, 0, 0, index);
        }
    }

    public void render(RenderBlocks renderblocks, Block block, int x, int y, int z, int index) {
        if (!isHidden) {
            modelTESS.renderFace(renderblocks, block, x, y, z, 0, 0, 0, index);
        }
    }

    public void render(RenderBlocks renderblocks, Block block, int x, int y, int z, double offsetX, double offsetY,
        double offsetZ, int index) {
        if (!isHidden) {
            modelTESS.renderFace(renderblocks, block, x, y, z, offsetX, offsetY, offsetZ, index);
        }
    }
}
