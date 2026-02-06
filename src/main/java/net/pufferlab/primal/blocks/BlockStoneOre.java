package net.pufferlab.primal.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.OreType;
import net.pufferlab.primal.utils.StoneType;

public class BlockStoneOre extends BlockMetaOre {

    public StoneType[] stoneTypes;
    public OreType oreType;

    public BlockStoneOre(StoneType[] materials, OreType type) {
        super(Material.rock, StoneType.getNames(materials), type.name, type.miningLevel);
        this.stoneTypes = materials;
        this.oreType = type;
        type.oreBlock = this;
        this.setHasSuffix();
        this.setHardness(1.5F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypePiston);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(Registry.cobble);
    }

    @Override
    public Item getItem(World worldIn, int x, int y, int z) {
        return oreType.oreItem;
    }

    @Override
    public int getDamageValue(World worldIn, int x, int y, int z) {
        return oreType.oreMeta;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        ret.add(new ItemStack(oreType.oreItem, 1, oreType.oreMeta));

        int count = quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++) {
            Item item = getItemDropped(metadata, world.rand, fortune);
            if (item != null) {
                ret.add(new ItemStack(item, 1, damageDropped(metadata)));
            }
        }
        return ret;
    }

    @Override
    public boolean canRegister() {
        return Config.oreVeins.getBoolean();
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
