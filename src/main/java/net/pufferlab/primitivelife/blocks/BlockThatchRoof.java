package net.pufferlab.primitivelife.blocks;

import net.minecraft.block.BlockStairs;
import net.pufferlab.primitivelife.PrimitiveLife;
import net.pufferlab.primitivelife.Registry;

public class BlockThatchRoof extends BlockStairs {

    public BlockThatchRoof() {
        super(Registry.thatch, 0);
        this.setStepSound(soundTypeGrass);
        this.useNeighborBrightness = true;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + PrimitiveLife.MODID + ".thatch_roof";
    }
}
