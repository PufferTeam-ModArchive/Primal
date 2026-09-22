package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.HashUtils;
import net.pufferlab.primal.utils.SoundTypePrimal;

import io.netty.buffer.ByteBuf;

public abstract class TileEntityPrimal extends TileEntity implements ITile {

    public long oldCoord;

    public TileEntityPrimal() {}

    public TileEntityPrimal(World world, int x, int y, int z) {
        this.worldObj = world;
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound dataTag = new NBTTagCompound();

        this.writeToNBTPacket(dataTag);

        Primal.proxy.packet.sendTileClientPacket(this);

        return (Packet) new S35PacketUpdateTileEntity(
            this.xCoord,
            this.yCoord,
            this.zCoord,
            this.blockMetadata,
            dataTag);
    }

    @Override
    public void onDataPacket(NetworkManager manager, S35PacketUpdateTileEntity packet) {
        NBTTagCompound nbtData = packet.func_148857_g();
        this.readFromNBTPacket(nbtData);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        // updateTELight();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        if (shouldCacheCoords()) {
            compound.setLong("pos", this.oldCoord);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if (shouldCacheCoords()) {
            this.oldCoord = compound.getLong("pos");
        }
    }

    public void updateServer() {

    }

    public void writeToNBTPacket(NBTTagCompound tag) {}

    public void readFromNBTPacket(NBTTagCompound tag) {}

    @Override
    public void writeToBuffer(ByteBuf buf) {

    }

    @Override
    public void readFromBuffer(ByteBuf buf) {

    }

    public void playSound(SoundTypePrimal stepSound) {
        World world = this.getWorldObj();
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;
        BlockUtils.playSound(world, x, y, z, stepSound);
    }

    @Override
    public int x() {
        return this.xCoord;
    }

    @Override
    public int y() {
        return this.yCoord;
    }

    @Override
    public int z() {
        return this.zCoord;
    }

    @Override
    public World getWorld() {
        return getWorldObj();
    }

    @Override
    public int getWorldID() {
        if (getWorld() == null) return 0;
        return getWorld().provider.dimensionId;
    }

    @Override
    public void mark() {
        this.markDirty();
    }

    @Override
    public Block getBlock() {
        return this.getBlockType();
    }

    @Override
    public void markDirty() {
        super.markDirty();

        if (shouldCacheCoords()) {
            int cachedX = HashUtils.unpackX(oldCoord);
            int cachedY = HashUtils.unpackY(oldCoord);
            int cachedZ = HashUtils.unpackZ(oldCoord);
            if (this.xCoord != cachedX || this.yCoord != cachedY || this.zCoord != cachedZ) {
                onCoordChange(cachedX, cachedY, cachedZ);
                cachedX = this.xCoord;
                cachedY = this.yCoord;
                cachedZ = this.zCoord;
                this.oldCoord = HashUtils.packCoord(cachedX, cachedY, cachedZ);
            }
        }
    }

    @Override
    public boolean shouldBatchUpdate() {
        return false;
    }

    public boolean shouldCacheCoords() {
        if (this instanceof IScheduledTile tile) {
            return true;
        }
        return false;
    }

    public void init() {}

    @Override
    public void invalidate() {
        super.invalidate();

        if (this instanceof IScheduledTile tile) {
            tile.removeAllSchedule();
        }
    }

    public void onCoordChange(int oldX, int oldY, int oldZ) {
        if (this instanceof IScheduledTile tile) {
            tile.moveAllSchedule(getWorldObj(), oldX, oldY, oldZ);
        }
    }
}
