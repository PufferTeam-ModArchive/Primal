package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityFluidInventory extends TileEntityMetaFacing implements IFluidHandler {

    private FluidTank tank;

    private int maxSize;

    public TileEntityFluidInventory(int maxSize) {
        tank = new FluidTank(maxSize);
        this.maxSize = maxSize;
    }

    public int getSizeInventory() {
        return this.maxSize;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.readFromNBTInventory(tag);
    }

    public void readFromNBTInventory(NBTTagCompound tag) {
        tank.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.writeToNBTInventory(tag);
    }

    public void writeToNBTInventory(NBTTagCompound tag) {
        tank.writeToNBT(tag);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(tank.getFluid())) {
            return null;
        }
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    public int getFluidAmount() {
        return tank.getFluidAmount();
    }

    public FluidStack getFluidStack() {
        return tank.getFluid();
    }

    public boolean isEmpty() {
        int amount = getFluidAmount();
        if (amount == 0) {
            return true;
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank.getInfo() };
    }
}
