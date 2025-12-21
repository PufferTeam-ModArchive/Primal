package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

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

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (!this.isOpen) {
            return false;
        }
        if (fluid != null) {
            if (fluid.getTemperature() > 500) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (!this.isOpen) {
            return false;
        }
        return true;
    }
}
