package net.pufferlab.primal.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.PlantType;

public class BlockPlantBush extends BlockMetaBush {

    public PlantType[] plantTypes;

    public BlockPlantBush(PlantType[] plantTypes, String name) {
        super(getPlantNames(plantTypes), name);
        this.plantTypes = plantTypes;
        for (int i = 0; i < plantTypes.length; i++) {
            plantTypes[i].setPlantItem(this, i);
        }
        this.setStepSound(soundTypeGrass);
    }

    public static String[] getPlantNames(PlantType[] plantTypes) {
        String[] types = new String[plantTypes.length];
        for (int i = 0; i < plantTypes.length; i++) {
            types[i] = plantTypes[i].name;
        }
        return types;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta >= plantTypes.length) return EnumPlantType.Plains;
        if (plantTypes[meta].isDesertic) return EnumPlantType.Desert;
        return EnumPlantType.Plains;
    }

    @Override
    public int getRenderShape(int meta) {
        if (meta >= plantTypes.length) return Constants.crossedModel;
        return plantTypes[meta].modelType;
    }

    @Override
    public boolean isSnowlogged(int meta) {
        if (meta >= plantTypes.length) return false;
        if (plantTypes[meta].isSnowy) return true;
        return false;
    }

    @Override
    public boolean canReplace(World worldIn, int x, int y, int z, int side, ItemStack itemIn) {
        return this.canPlaceBlockOnSide(worldIn, x, y, z, side, itemIn);
    }

    public boolean canPlaceBlockOnSide(World worldIn, int x, int y, int z, int side, ItemStack stack) {
        return this.canPlaceBlockAt(worldIn, x, y, z, stack.getItemDamage());
    }

    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z, int plantMeta) {
        return canBlockStay(worldIn, x, y, z, plantMeta);
    }

    @Override
    public boolean canBlockStay(World worldIn, int x, int y, int z) {
        Block block = worldIn.getBlock(x, y - 1, z);
        int meta = worldIn.getBlockMetadata(x, y - 1, z);
        Block blockReplaced = worldIn.getBlock(x, y, z);
        int metaReplaced = worldIn.getBlockMetadata(x, y, z);
        return canPlantGrowOn(block, meta, blockReplaced, metaReplaced, metaReplaced);
    }

    public boolean canBlockStay(World worldIn, int x, int y, int z, int metaPlant) {
        Block block = worldIn.getBlock(x, y - 1, z);
        int meta = worldIn.getBlockMetadata(x, y - 1, z);
        Block blockReplaced = worldIn.getBlock(x, y, z);
        int metaReplaced = worldIn.getBlockMetadata(x, y, z);
        if (!blockReplaced.isReplaceable(worldIn, x, y, z)) return false;
        return canPlantGrowOn(block, meta, blockReplaced, metaReplaced, metaPlant);
    }

    public boolean canPlantGrowOn(Block ground, int meta, Block blockReplaced, int metaReplaced, int metaPlant) {
        if (metaPlant >= plantTypes.length) return false;
        if (isWaterlogged(metaPlant)) {
            if (blockReplaced != this) {
                if (!(BlockUtils.isWaterBlock(blockReplaced) && metaReplaced == 0)) {
                    return false;
                }
            }
        }
        if (plantTypes[metaPlant].isGrassy) {
            if (BlockUtils.isGrassBlock(ground) || BlockUtils.isDirtBlock(ground)
                || BlockUtils.isFarmlandBlock(ground)) {
                return true;
            }
        }
        if (plantTypes[metaPlant].isSnowy) {
            if (BlockUtils.isGrassBlock(ground) || BlockUtils.isDirtBlock(ground)) {
                return true;
            }
        }
        if (plantTypes[metaPlant].isDesertic) {
            if (BlockUtils.isSandBlock(ground)) {
                return true;
            }
        }
        if (plantTypes[metaPlant].isAquatic) {
            if (BlockUtils.isGrassBlock(ground) || BlockUtils.isDirtBlock(ground) || BlockUtils.isGravelBlock(ground)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        super.setBlockBoundsBasedOnState(worldIn, x, y, z);
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
        if (isWaterlogged(world, x, y, z)) {
            return world.setBlock(x, y, z, Blocks.water, 0, 2);
        } else {
            return super.removedByPlayer(world, player, x, y, z);
        }
    }

    @Override
    protected void checkAndDropBlock(World worldIn, int x, int y, int z) {
        if (!this.canBlockStay(worldIn, x, y, z)) {
            this.dropBlockAsItem(worldIn, x, y, z, worldIn.getBlockMetadata(x, y, z), 0);
            if (isWaterlogged(worldIn, x, y, z)) {
                worldIn.setBlock(x, y, z, Blocks.water, 0, 2);
            } else {
                worldIn.setBlockToAir(x, y, z);
            }
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        Material material = worldIn.getBlock(x, y, z)
            .getMaterial();
        int meta = worldIn.getBlockMetadata(x, y, z);
        if (meta >= plantTypes.length) return false;
        return (plantTypes[meta].isAquatic && (material == this.blockMaterial || material == Material.water)) ? false
            : (side == 1 ? true : super.shouldSideBeRendered(worldIn, x, y, z, side));
    }

    @Override
    public boolean isWaterlogged(int meta) {
        if (meta >= plantTypes.length) return false;
        if (plantTypes[meta].isAquatic) return true;
        return false;
    }

    @Override
    public List<AxisAlignedBB> getBounds(World world, int x, int y, int z, EntityPlayer player, BoundsType bounds) {
        int meta = world.getBlockMetadata(x, y, z);
        boolean snowy = isSnowlogged(meta);
        List<AxisAlignedBB> list = new ArrayList<>();
        if (snowy) {
            list.add(AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F));
        }
        return list;
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public boolean canRenderInPass(int pass) {
        return true;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }
}
