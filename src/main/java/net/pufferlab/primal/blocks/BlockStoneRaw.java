package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;

public class BlockStoneRaw extends BlockMeta {

    public StoneType[] stoneTypes;

    public BlockStoneRaw(StoneType[] materials, String type) {
        super(Material.rock, StoneType.getNames(materials), type);
        this.stoneTypes = materials;
        this.setHasSuffix();
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypePiston);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        if (this == Registry.stone) {
            return Item.getItemFromBlock(Registry.cobble);
        } else {
            return super.getItemDropped(meta, random, fortune);
        }
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
