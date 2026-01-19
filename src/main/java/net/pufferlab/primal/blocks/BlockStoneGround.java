package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.StoneType;

public class BlockStoneGround extends BlockGroundcover {

    public StoneType[] stoneTypes;

    public BlockStoneGround(StoneType[] materials, String type) {
        super(Material.rock, StoneType.getNames(materials), type);
        this.stoneTypes = materials;
        this.setHasSuffix();
        this.setStepSound(soundTypePiston);
        this.setTextureOverride(StoneType.getTextures(materials));
    }

    @Override
    public boolean canRegister() {
        return Config.strataStoneTypes.getBoolean();
    }
}
