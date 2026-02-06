package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.SoilType;

public class BlockSoilPath extends BlockMetaPath {

    public SoilType[] stoneTypes;

    public BlockSoilPath(SoilType[] materials, String type) {
        super(Material.ground, SoilType.getNames(materials), type);
        this.stoneTypes = materials;
        this.setHasSuffix();
        this.setHardness(0.5F);
        this.setStepSound(soundTypeGravel);
    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        Material material = worldIn.getBlock(x, y + 1, z)
            .getMaterial();

        if (material.isSolid()) {
            worldIn.setBlock(x, y, z, Registry.dirt, meta, 2);
        }
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(Registry.dirt);
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
