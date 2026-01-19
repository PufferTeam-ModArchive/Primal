package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;

public class BlockStoneDirt extends BlockMetaDirt {

    public StoneType[] stoneTypes;

    public BlockStoneDirt(StoneType[] materials, String type) {
        super(Material.ground, StoneType.getNames(materials), type);
        this.stoneTypes = materials;
        this.setHasSuffix();
        this.setHardness(0.5F);
        this.setStepSound(soundTypeGravel);
    }

    @Override
    public boolean canRegister() {
        return Config.strataSoilTypes.getBoolean();
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabStone;
    }
}
