package net.pufferlab.primal.mixins.early.minecraft;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.pufferlab.primal.blocks.IPrimalBlock;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockDoublePlant.class)
public abstract class MixinBlockDoublePlant implements IPrimalBlock {

    @Shadow
    public abstract IIcon getIcon(int side, int meta);

    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side) {
        int meta = worldIn.getBlockMetadata(x, y, z);
        if (meta >= 8) {
            meta = worldIn.getBlockMetadata(x, y - 1, z);
        }
        return this.getIcon(side, meta);
    }

    @Override
    public boolean useWorldIcon() {
        return true;
    }
}
