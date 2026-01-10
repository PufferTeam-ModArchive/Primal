package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.SoundTypePrimal;

public abstract class TileEntityPrimal extends TileEntity implements ITile {

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

    public void updateTEState() {
        this.markDirty();
        this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.blockType);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
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
}
