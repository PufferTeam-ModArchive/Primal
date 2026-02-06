package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.SoilType;

public class BlockSoilDirt extends BlockMetaDirt {

    public SoilType[] stoneTypes;

    public BlockSoilDirt(SoilType[] materials, String type) {
        super(Material.ground, SoilType.getNames(materials), type);
        this.stoneTypes = materials;
        this.setHasSuffix();
        this.setHardness(0.5F);
        this.setStepSound(soundTypeGravel);
    }

    @Override
    public boolean canRegister() {
        return Config.soilTypes.getBoolean();
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }

    @Override
    public boolean registerOre() {
        return true;
    }
}
