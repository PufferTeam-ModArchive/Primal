package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMetaFacing extends TileEntityPrimal {

    public int facingMeta = 2;
    public int facingAxis = 0;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.facingMeta = tag.getInteger("facingMeta");
        this.facingAxis = tag.getInteger("facingAxis");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setInteger("facingMeta", this.facingMeta);
        tag.setInteger("facingAxis", this.facingAxis);
    }

    public void setFacingMeta(int meta) {
        this.facingMeta = meta;
        this.updateTEState();
    }

    public void setFacingAxis(int meta) {
        this.facingAxis = meta;
        this.updateTEState();
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setInteger("facingMeta", this.facingMeta);
        tag.setInteger("facingAxis", this.facingAxis);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.facingMeta = tag.getInteger("facingMeta");
        this.facingAxis = tag.getInteger("facingAxis");
    }
}
