package net.pufferlab.primal.client.models;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraftforge.fluids.FluidStack;

public class ModelFluid extends ModelPrimal {

    public ModelFluid() {
        super(0);
    }

    public void render(RenderBlocks renderblocks, Tessellator tess, int x, int y, int z, FluidStack fs, double minX,
        double minY, double minZ, double maxX, double maxY, double maxZ, boolean renderAllSides, boolean isFlowing) {
        modelTESS.renderFluid(
            renderblocks,
            tess,
            x,
            y,
            z,
            fs,
            minX + facingOffsetX,
            minY + facingOffsetY,
            minZ + facingOffsetZ,
            maxX + facingOffsetX,
            maxY + facingOffsetY,
            maxZ + facingOffsetZ,
            renderAllSides,
            isFlowing);
    }
}
