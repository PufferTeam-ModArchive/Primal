package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCut extends TileEntityPrimal {

    public short materialMeta = -1;

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setShort("materialMeta", this.materialMeta);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.materialMeta = compound.getShort("materialMeta");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        tag.setShort("materialMeta", this.materialMeta);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        this.materialMeta = tag.getShort("materialMeta");
    }

    public void setMaterialMeta(int meta) {
        this.materialMeta = (short) meta;
    }

    public int getMaterialMeta() {
        return this.materialMeta;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }
}
