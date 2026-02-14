package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCutDouble extends TileEntityCut {

    public short materialMeta2 = -1;

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setShort("materialMeta2", this.materialMeta2);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.materialMeta2 = compound.getShort("materialMeta2");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        tag.setShort("materialMeta2", this.materialMeta2);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        this.materialMeta2 = tag.getShort("materialMeta2");
    }

    public void setMaterialMeta2(int meta) {
        this.materialMeta2 = (short) meta;
        this.markDirty();
    }

    public int getMaterialMeta2() {
        return this.materialMeta2;
    }
}
