package net.pufferlab.primal.mixins.late.dragonapi;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.ItemUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import Reika.DragonAPI.Libraries.World.ReikaBlockHelper;
import Reika.DragonAPI.ModInteract.ItemHandlers.ChiselBlockHandler;
import Reika.DragonAPI.ModList;

@Mixin(ReikaBlockHelper.class)
public class MixinBlockHelper {

    @Shadow(remap = false)
    public static boolean isOre(Block id, int meta) {
        throw new AbstractMethodError();
    };

    /**
     * @author JBT
     * @reason This fixes Primal grass/dirt not being detected as valid soil
     **/
    @Overwrite(remap = false)
    public static boolean isDirtType(Block id, int meta) {
        if (id == Blocks.dirt) return true;
        if (id == Blocks.grass) return true;
        if (ItemUtils.isDirtBlock(id)) return true;
        if (ItemUtils.isGrassBlock(id)) return true;
        if (id == Blocks.gravel) return false;
        return false;
    }

    /**
     * @author JBT
     * @reason This fixes Primal grass/dirt not being detected as valid soil
     **/
    @Overwrite(remap = false)
    public static boolean isNaturalStone(World world, int x, int y, int z) {
        Block b = world.getBlock(x, y, z);
        if (ItemUtils.isNaturalStone(b)) {
            return true;
        }
        if (b != Blocks.stone && b != Blocks.sandstone && b != Blocks.bedrock) {
            if (b.isReplaceableOreGen(world, x, y, z, Blocks.stone)) {
                return true;
            } else {
                int meta = world.getBlockMetadata(x, y, z);
                if (isOre(b, meta)) {
                    return true;
                } else {
                    return ModList.CHISEL.isLoaded() && ChiselBlockHandler.isWorldgenBlock(b, meta);
                }
            }
        } else {
            return true;
        }
    }
}
