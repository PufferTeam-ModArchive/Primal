package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.OreType;

public class BlockStoneGroundOre extends BlockGroundcover {

    public OreType[] oreTypes;

    public BlockStoneGroundOre(OreType[] materials, String type) {
        super(Material.rock, OreType.getNames(materials), type);
        this.oreTypes = materials;
        this.setHasSuffix();
        this.setStepSound(soundTypePiston);
        this.setTextureOverride(OreType.getTextures(materials));
    }

    @Override
    public boolean canRegister() {
        return Config.oreVeins.getBoolean();
    }
}
