package net.pufferlab.primal.blocks;

import net.pufferlab.primal.Config;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.utils.OreType;
import net.pufferlab.primal.utils.StoneType;

public class BlockStoneOreThaumcraft extends BlockStoneOre {

    int color;

    public BlockStoneOreThaumcraft(StoneType[] materials, OreType type) {
        super(materials, type);
        this.color = type.color;
        this.setEmissive(type.color);
        this.setEmissiveTexture("tc_infused_ore");
    }

    @Override
    public boolean isEmissive() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return Config.oreVeins.getBoolean() && Mods.tc.isLoaded();
    }
}
