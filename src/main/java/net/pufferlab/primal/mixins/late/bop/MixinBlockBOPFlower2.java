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
import biomesoplenty.common.blocks.BlockBOPFlower2;

@Mixin(BlockBOPFlower2.class)
public abstract class MixinBlockBOPFlower2 extends Block {

    protected MixinBlockBOPFlower2(Material materialIn) {
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
            case 2: // Burning Blossom
                return block == Blocks.netherrack || block == BOPCBlocks.overgrownNetherrack;

            case 6: // Miner's Delight
                return Utils.isNaturalStone(block);

            case 8: // Rose
                return Utils.isGrassBlock(block) || Utils.isDirtBlock(block)
                    || Utils.isFarmlandBlock(block)
                    || block == BOPCBlocks.longGrass
                    || block == BOPCBlocks.overgrownNetherrack
                    || block == BOPCBlocks.originGrass
                    || block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (IPlantable) this);

            default:
                return Utils.isGrassBlock(block) || Utils.isDirtBlock(block)
                    || Utils.isFarmlandBlock(block)
                    || block == BOPCBlocks.longGrass
                    || block == BOPCBlocks.overgrownNetherrack
                    || block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (IPlantable) this);
        }
    }
}
