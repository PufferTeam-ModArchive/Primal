package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMetaFacing extends TileEntityPrimal {

    public int facingMeta = 2;
    public int axisMeta = 0;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.facingMeta = tag.getInteger("facingMeta");
        this.axisMeta = tag.getInteger("axisMeta");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setInteger("facingMeta", this.facingMeta);
        tag.setInteger("axisMeta", this.axisMeta);
    }

    public void setFacingMeta(int meta) {
        this.facingMeta = meta;
        this.updateTEState();
    }

    public void setAxisMeta(int meta) {
        this.axisMeta = meta;
        this.updateTEState();
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setInteger("facingMeta", this.facingMeta);
        tag.setInteger("axisMeta", this.axisMeta);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.facingMeta = tag.getInteger("facingMeta");
        this.axisMeta = tag.getInteger("axisMeta");
    }
}
