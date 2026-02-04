package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.utils.SoundTypePrimal;

public abstract class TileEntityPrimal extends TileEntity implements ITile {

    public int cachedX;
    public int cachedY;
    public int cachedZ;

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound dataTag = new NBTTagCompound();

        this.writeToNBTPacket(dataTag);

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
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("xCached", this.cachedX);
        compound.setInteger("yCached", this.cachedY);
        compound.setInteger("zCached", this.cachedZ);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.cachedX = compound.getInteger("xCached");
        this.cachedY = compound.getInteger("yCached");
        this.cachedZ = compound.getInteger("zCached");
    }

    public void updateTEState() {
        this.markDirty();
        this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.blockType);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    public void updateTELight() {
        this.worldObj.updateLightByType(EnumSkyBlock.Sky, this.xCoord, this.yCoord, this.zCoord);
        this.worldObj.updateLightByType(EnumSkyBlock.Block, this.xCoord, this.yCoord, this.zCoord);
    }

    public void writeToNBTPacket(NBTTagCompound tag) {}

    public void readFromNBTPacket(NBTTagCompound tag) {}

    public void playSound(SoundTypePrimal stepSound) {
        World world = this.getWorldObj();
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;
        Utils.playSound(world, x, y, z, stepSound);
    }

    @Override
    public int getX() {
        return this.xCoord;
    }

    @Override
    public int getY() {
        return this.yCoord;
    }

    @Override
    public int getZ() {
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

        if (this.xCoord != this.cachedX || this.yCoord != this.cachedY || this.zCoord != this.cachedZ) {
            onCoordChange(this.cachedX, this.cachedY, this.cachedZ);
            this.cachedX = this.xCoord;
            this.cachedY = this.yCoord;
            this.cachedZ = this.zCoord;
        }
    }

    public void onCoordChange(int oldX, int oldY, int oldZ) {}
}
