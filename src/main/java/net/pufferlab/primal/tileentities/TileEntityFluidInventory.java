package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityFluidInventory extends TileEntityInventory implements IFluidHandler {

    public final FluidTank tank;

    public TileEntityFluidInventory(int maxSize, int itemMaxSize) {
        super(itemMaxSize);
        tank = new FluidTank(maxSize);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.readFromNBTInventory(tag);
    }

    @Override
    public void readFromNBTInventory(NBTTagCompound tag) {
        super.readFromNBTInventory(tag);
        if (tag.hasKey("Tank")) {
            tank.readFromNBT(tag.getCompoundTag("Tank"));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        this.writeToNBTInventory(tag);
    }

    @Override
    public void writeToNBTInventory(NBTTagCompound tag) {
        super.writeToNBTInventory(tag);
        NBTTagCompound tankTag = new NBTTagCompound();
        tank.writeToNBT(tankTag);
        tag.setTag("Tank", tankTag);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        this.writeToNBTInventory(tag);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.readFromNBTInventory(tag);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (doFill) {
            this.updateTE();
        }
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(tank.getFluid())) {
            return null;
        }
        if (doDrain) {
            this.updateTE();
        }
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (doDrain) {
            this.updateTE();
        }
        return tank.drain(maxDrain, doDrain);
    }

    public void updateTE() {
        this.markDirty();
        this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.blockType);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (fluid.getTemperature() > 500) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    public FluidStack getFluidStackRelative() {
        return tank.getFluid();
    }

    public FluidStack getFluidStack() {
        return tank.getFluid();
    }

    public float getFillLevel(float min, float max) {
        int capacity = tank.getCapacity();
        if (capacity <= 0) return min;

        float ratio = (float) tank.getFluidAmount() / capacity;
        ratio = MathHelper.clamp_float(ratio, 0f, 1f);
        return ratio * (max - min) + min;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank.getInfo() };
    }
}
