package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMetaFacing extends TileEntityPrimal {

    public int facingMeta = 2;
    public int axisMeta = 0;

    public TileEntityMetaFacing() {}

    public TileEntityMetaFacing(int facingMeta) {
        setFacingMeta(facingMeta);
    }

    public TileEntityMetaFacing(int facingMeta, int axisMeta) {
        setFacingMeta(facingMeta);
        setAxisMeta(axisMeta);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        if (hasFacing()) {
            this.facingMeta = tag.getByte("facingMeta");
        }
        if (hasAxis()) {
            this.axisMeta = tag.getByte("axisMeta");
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        if (hasFacing()) {
            tag.setByte("facingMeta", (byte) this.facingMeta);
        }
        if (hasAxis()) {
            tag.setByte("axisMeta", (byte) this.axisMeta);
        }
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
        if (hasFacing()) {
            tag.setByte("facingMeta", (byte) this.facingMeta);
        }
        if (hasAxis()) {
            tag.setByte("axisMeta", (byte) this.axisMeta);
        }
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        if (hasFacing()) {
            this.facingMeta = tag.getByte("facingMeta");
        }
        if (hasAxis()) {
            this.axisMeta = tag.getByte("axisMeta");
        }
    }

    public boolean hasFacing() {
        return true;
    }

    public boolean hasAxis() {
        return false;
    }
}
