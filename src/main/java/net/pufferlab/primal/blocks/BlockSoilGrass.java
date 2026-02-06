package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.ItemUtils;
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
    public void updateTick(World worldIn, int x, int y, int z, Random random) {
        if (!worldIn.isRemote) {
            if (worldIn.getBlockLightValue(x, y + 1, z) < 4 && worldIn.getBlockLightOpacity(x, y + 1, z) > 2) {
                int meta = worldIn.getBlockMetadata(x, y, z);
                worldIn.setBlock(x, y, z, Registry.dirt, meta, 2);
            } else if (worldIn.getBlockLightValue(x, y + 1, z) >= 9) {
                for (int l = 0; l < 4; ++l) {
                    int i1 = x + random.nextInt(3) - 1;
                    int j1 = y + random.nextInt(5) - 3;
                    int k1 = z + random.nextInt(3) - 1;
                    Block block = worldIn.getBlock(i1, j1 + 1, k1);
                    Block blockCurrent = worldIn.getBlock(i1, j1, k1);
                    int metaCurrent = worldIn.getBlockMetadata(i1, j1, k1);
                    if (ItemUtils.isDirtBlock(blockCurrent) && worldIn.getBlockLightValue(i1, j1 + 1, k1) >= 4
                        && worldIn.getBlockLightOpacity(i1, j1 + 1, k1) <= 2) {
                        worldIn.setBlock(i1, j1, k1, Registry.grass, metaCurrent, 2);
                    }
                }
            }
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
