package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import io.netty.buffer.ByteBuf;

public class TileEntityCut extends TileEntityPrimal {

    public short materialMeta = -1;

    public TileEntityCut() {}

    public TileEntityCut(World world, int x, int y, int z, int material) {
        super(world, x, y, z);
        setMaterialMeta(material);
    }

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
    public void writeToBuffer(ByteBuf buf) {
        super.writeToBuffer(buf);

        buf.writeShort(materialMeta);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        super.readFromBuffer(buf);

        this.materialMeta = buf.readShort();
    }

    public void setMaterialMeta(int meta) {
        this.materialMeta = (short) meta;
        this.updateTEState();
    }

    public int getMaterialMeta() {
        return this.materialMeta;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }
}
