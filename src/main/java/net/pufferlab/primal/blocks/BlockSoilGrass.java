package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.SoilType;

public class BlockSoilGrass extends BlockMetaGrass {

    public SoilType[] stoneTypes;

    public BlockSoilGrass(SoilType[] materials, String type) {
        super(Material.grass, SoilType.getNames(materials), type);
        this.stoneTypes = materials;
        this.setHasSuffix();
        this.setHardness(0.6F);
        this.setStepSound(soundTypeGrass);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        if (this == Registry.grass) {
            return Item.getItemFromBlock(Registry.dirt);
        } else {
            return super.getItemDropped(meta, random, fortune);
        }
    }

    @Override
    public boolean canRegister() {
        return Config.soilTypes.getBoolean();
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
