package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.IPositioned;

import io.netty.buffer.ByteBuf;

public interface ITile extends IPositioned {

    public World getWorld();

    public void mark();

    public int getWorldID();

    default TileEntity getTile() {
        return getWorld().getTileEntity(x(), y(), z());
    }

    default int getMeta() {
        return getWorld().getBlockMetadata(x(), y(), z());
    }

    default void setMeta(int meta) {
        getWorld().setBlockMetadataWithNotify(x(), y(), z(), meta, 2);
    }

    default void updateTEState() {
        this.mark();
        if (this.getWorld() != null) {
            this.getWorld()
                .func_147453_f(x(), y(), z(), getBlock());
            this.getWorld()
                .markBlockForUpdate(x(), y(), z());
        }
    }

    default void updateTELight() {
        this.getWorld()
            .updateLightByType(EnumSkyBlock.Sky, x(), y(), z());
        this.getWorld()
            .updateLightByType(EnumSkyBlock.Block, x(), y(), z());
    }

    default Block getBlock() {
        return getTile().getBlockType();
    }

    default boolean shouldBatchUpdate() {
        return true;
    }

    public void writeToBuffer(ByteBuf buf);

    public void readFromBuffer(ByteBuf buf);
}
