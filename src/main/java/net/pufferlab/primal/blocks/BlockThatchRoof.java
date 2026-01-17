package net.pufferlab.primal.blocks;

import net.minecraft.block.BlockStairs;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.SoundTypePrimal;

public class BlockThatchRoof extends BlockStairs {

    public BlockThatchRoof() {
        super(Registry.thatch, 0);
        this.setStepSound(SoundTypePrimal.soundTypeThatch);
        this.useNeighborBrightness = true;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".thatch_roof";
    }
}
