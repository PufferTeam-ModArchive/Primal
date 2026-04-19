package net.pufferlab.primal.mixins.late.bop;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.utils.BlockUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.blocks.BlockBOPPlant;

@Mixin(BlockBOPPlant.class)
public abstract class MixinBlockBOPPlant extends Block implements IPlantable {

    protected MixinBlockBOPPlant(Material materialIn) {
        super(materialIn);
    }

    /**
     * @author JBT
     * @reason This fixes Primal grass/dirt not being detected as valid soil
     **/
    @Overwrite(remap = false)
    public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
        Block block = world.getBlock(x, y - 1, z);
        int meta = world.getBlockMetadata(x, y - 1, z);
        Block root = world.getBlock(x, y + 1, z);
        Block reedwater = world.getBlock(x, y - 2, z);
        switch (metadata) {
            case 0:
                return block == BOPCBlocks.driedDirt || BlockUtils.isSandBlock(block);
            case 1:
                return block == Blocks.hardened_clay;
            case 2:
            case 3:
                return BlockUtils.isSandBlock(block);
            case 4:
                return BlockUtils.isGrassBlock(block);
            case 5:
                return BlockUtils.isDirtBlock(block) || BlockUtils.isGrassBlock(block)
                    || block == Blocks.soul_sand
                    || block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
            case 6:
                return BlockUtils.isDirtBlock(block) || BlockUtils.isGrassBlock(block)
                    || block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
            case 7:
                return block != Blocks.grass && block != BOPCBlocks.newBopGrass ? false
                    : (world.getBlock(x - 1, y - 1, z)
                        .getMaterial() == Material.water ? true
                            : (world.getBlock(x + 1, y - 1, z)
                                .getMaterial() == Material.water ? true
                                    : (world.getBlock(x, y - 1, z - 1)
                                        .getMaterial() == Material.water ? true
                                            : world.getBlock(x, y - 1, z + 1)
                                                .getMaterial() == Material.water)));
            case 8:
                return block == this || BlockUtils.isGrassBlock(block)
                    || block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
            case 9:
                return block == this;
            case 10:
                return block != Blocks.grass && block != BOPCBlocks.newBopGrass ? false
                    : (world.getBlock(x - 1, y - 1, z)
                        .getMaterial() == Material.water ? true
                            : (world.getBlock(x + 1, y - 1, z)
                                .getMaterial() == Material.water ? true
                                    : (world.getBlock(x, y - 1, z - 1)
                                        .getMaterial() == Material.water ? true
                                            : world.getBlock(x, y - 1, z + 1)
                                                .getMaterial() == Material.water)));
            case 11:
            default:
                return BlockUtils.isDirtBlock(block) || BlockUtils.isGrassBlock(block)
                    || block == Blocks.farmland
                    || block == BOPCBlocks.overgrownNetherrack
                    || block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
            case 12:
                return BlockUtils.isSandBlock(block) || block == Blocks.hardened_clay || block == Blocks.soul_sand;
            case 13:
                return block == Blocks.soul_sand;
            case 14:
                return block == Blocks.water && reedwater != Blocks.water;
            case 15:
                return root != Blocks.air && (BlockUtils.isSoilBlock(block, meta));
        }
    }
}
