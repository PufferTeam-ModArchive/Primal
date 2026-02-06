package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.SoilType;

public class BlockSoilFarmland extends BlockMetaFarmland {

    public BlockSoilFarmland(SoilType[] materials, String type) {
        super(Material.ground, SoilType.getNames(materials), type);
        this.setHardness(0.6F);
        this.setStepSound(soundTypeGravel);
        this.setHasSuffix();
    }

    public void onFallenUpon(World worldIn, int x, int y, int z, Entity entityIn, float fallDistance) {
        if (this == Registry.farmland) {
            int meta = worldIn.getBlockMetadata(x, y, z);
            if (!worldIn.isRemote && worldIn.rand.nextFloat() < fallDistance - 0.5F) {
                if (!(entityIn instanceof EntityPlayer) && !worldIn.getGameRules()
                    .getGameRuleBooleanValue("mobGriefing")) {
                    return;
                }

                worldIn.setBlock(x, y, z, Registry.dirt, meta, 2);
            }
        }

    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortune) {
        return Item.getItemFromBlock(Registry.dirt);
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
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
