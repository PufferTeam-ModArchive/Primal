package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityLargeVessel extends TileEntityFluidInventory {

    public boolean isOpen = false;

    public TileEntityLargeVessel() {
        super(1000, 9);
    }

    @Override
    public String getInventoryName() {
        return "Large Vessel";
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.isOpen = tag.getBoolean("isOpen");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setBoolean("isOpen", this.isOpen);
    }

    public void setOpen(boolean meta) {
        this.isOpen = meta;
        updateTEState();
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.isOpen = tag.getBoolean("isOpen");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setBoolean("isOpen", this.isOpen);
    }

}
