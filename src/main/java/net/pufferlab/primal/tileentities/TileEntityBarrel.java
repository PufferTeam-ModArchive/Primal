package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBarrel extends TileEntityFluidInventory {

    public boolean isFloorBarrel = false;

    public TileEntityBarrel() {
        super(16000);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.isFloorBarrel = tag.getBoolean("isFloorBarrel");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("isFloorBarrel", this.isFloorBarrel);
    }

    public void setFloorBarrel(boolean meta) {
        this.isFloorBarrel = meta;
        this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, this.blockType);
        this.markDirty();
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setBoolean("isFloorBarrel", this.isFloorBarrel);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.isFloorBarrel = tag.getBoolean("isFloorBarrel");
    }
}
