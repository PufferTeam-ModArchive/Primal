package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;

public class BlockStoneSand extends BlockMetaSand {

    public StoneType[] stoneTypes;

    public BlockStoneSand(StoneType[] materials, String type) {
        super(Material.sand, StoneType.getNames(materials), type);
        this.stoneTypes = materials;
        this.setHasSuffix();
        this.setHardness(0.5F);
        this.setStepSound(soundTypeSand);
    }

    @Override
    public boolean canRegister() {
        return Config.strataStoneTypes.getBoolean();
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
