package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public interface ITile {

    public World getWorld();

    public int getX();

    public int getY();

    public int getZ();

    public void mark();

    public int getWorldID();

    default TileEntity getTile() {
        return getWorld().getTileEntity(getX(), getY(), getZ());
    }

    default int getMeta() {
        return getWorld().getBlockMetadata(getX(), getY(), getZ());
    }

    default void setMeta(int meta) {
        getWorld().setBlockMetadataWithNotify(getX(), getY(), getZ(), meta, 2);
    }

    default void updateTEState() {
        this.mark();
        if (this.getWorld() != null) {
            this.getWorld()
                .func_147453_f(getX(), getY(), getZ(), getBlock());
            this.getWorld()
                .markBlockForUpdate(getX(), getY(), getZ());
        }
    }

    default void updateTELight() {
        this.getWorld()
            .updateLightByType(EnumSkyBlock.Sky, getX(), getY(), getZ());
        this.getWorld()
            .updateLightByType(EnumSkyBlock.Block, getX(), getY(), getZ());
    }

    default Block getBlock() {
        return getTile().getBlockType();
    }

    default boolean shouldBatchUpdate() {
        return true;
    }
}
