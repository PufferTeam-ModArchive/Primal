package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.StoneType;

public class BlockStoneGravel extends BlockMetaGravel {

    public StoneType[] stoneTypes;

    public BlockStoneGravel(StoneType[] materials, String type) {
        super(Material.sand, StoneType.getNames(materials), type);
        this.stoneTypes = materials;
        this.setHasSuffix();
        this.setHardness(0.6F);
        this.setStepSound(soundTypeGravel);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        if (fortune > 3) {
            fortune = 3;
        }

        return random.nextInt(10 - fortune * 3) == 0 ? Items.flint : Item.getItemFromBlock(this);
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
