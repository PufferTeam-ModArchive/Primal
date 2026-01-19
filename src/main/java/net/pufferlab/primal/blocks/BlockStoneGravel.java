package net.pufferlab.primal.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;

public class BlockStoneGravel extends BlockMetaFalling {

    public StoneType[] stoneTypes;

    public BlockStoneGravel(StoneType[] materials, String type) {
        super(Material.rock, StoneType.getNames(materials), type);
        this.stoneTypes = materials;
        this.setHasSuffix();
        this.setHardness(0.6F);
        this.setStepSound(soundTypeGravel);
    }

    @Override
    public boolean canRegister() {
        return Config.strataStoneTypes.getBoolean();
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabStone;
    }
}
