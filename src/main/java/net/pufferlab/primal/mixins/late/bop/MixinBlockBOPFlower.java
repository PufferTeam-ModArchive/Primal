package net.pufferlab.primal.mixins.late.bop;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Utils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.blocks.BlockBOPFlower;

@Mixin(BlockBOPFlower.class)
public abstract class MixinBlockBOPFlower extends Block {

    protected MixinBlockBOPFlower(Material materialIn) {
        super(materialIn);
    }

    /**
     * @author JBT
     * @reason This fixes Primal grass/dirt not being detected as valid soil
     **/
    @Overwrite(remap = false)
    public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
        Block block = world.getBlock(x, y - 1, z);

        switch (metadata) {
            case 6: // Tulip
                return Utils.isGrassBlock(block) || Utils.isDirtBlock(block)
                    || Utils.isFarmlandBlock(block)
                    || block == BOPCBlocks.longGrass
                    || block == BOPCBlocks.overgrownNetherrack
                    || block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (IPlantable) this);

            case 10: // Lily Flower
                return block == Blocks.waterlily;

            case 11: // Enderlotus
                return block == BOPCBlocks.bopGrass || Utils.isGrassBlock(block);

            case 12: // Bromeliad
                return block == BOPCBlocks.hardDirt || Utils.isDirtBlock(block)
                    || block == Blocks.hardened_clay
                    || Utils.isSandBlock(block);

            case 13: // Eyebulb Bottom
                return block == Blocks.netherrack || block == BOPCBlocks.overgrownNetherrack
                    || block == BOPCBlocks.flesh;

            case 14: // Eyebulb Top
                return block == this;

            default:
                return Utils.isGrassBlock(block) || Utils.isDirtBlock(block)
                    || Utils.isFarmlandBlock(block)
                    || block == BOPCBlocks.longGrass
                    || block == BOPCBlocks.overgrownNetherrack
                    || block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (IPlantable) this);
        }
    }
}
