package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.utils.Utils;

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

    public void rotateY() {
        int meta = this.facingMeta + 1;
        this.facingMeta = Utils.clamp(0, 3, meta) + 1;
    }

    public void rotateAxisY() {
        if (axisMeta > 0) {
            if (axisMeta == 1) {
                axisMeta = 2;
            }
            if (axisMeta == 2) {
                axisMeta = 1;
            }
        }
    }

    public boolean hasFacing() {
        return true;
    }

    public boolean hasAxis() {
        return false;
    }
}
