package net.pufferlab.primal.client.models;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;

public class ModelItem extends ModelPrimal {

    public ModelItem() {
        super(0);
    }

    public void renderItem(RenderBlocks renderblocks, Tessellator tess, Block block, int x, int y, int z,
        double offsetX, double offsetY, double offsetZ, int index, float scale) {
        modelTESS.renderItem(
            renderblocks,
            tess,
            block,
            x,
            y,
            z,
            facingOffsetX + offsetX,
            facingOffsetY + offsetY,
            facingOffsetZ + offsetZ,
            index,
            this.bb_main,
            scale);
    }

    public void renderItem(RenderBlocks renderblocks, Tessellator tess, Block block, int x, int y, int z, int index,
        float scale) {
        modelTESS.renderItem(
            renderblocks,
            tess,
            block,
            x,
            y,
            z,
            facingOffsetX,
            facingOffsetY,
            facingOffsetZ,
            index,
            this.bb_main,
            scale);
    }
}
