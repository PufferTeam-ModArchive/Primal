package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import net.pufferlab.primal.recipes.BarrelRecipe;

public class TileEntityBarrel extends TileEntityFluidInventory {

    public static final FluidStack waterFluidStack = new FluidStack(FluidRegistry.WATER, 5);
    public boolean isFloorBarrel = false;
    public boolean isOpen = false;
    private final FluidTank tankOutput;
    public int timePassed;
    public int timePassedRain;

    public TileEntityBarrel() {
        super(10000, 2);
        tankOutput = new FluidTank(10000);
        this.setInputSlots(0);
        this.setOutputSlots(1);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.isOpen = tag.getBoolean("isOpen");
        this.isFloorBarrel = tag.getBoolean("isFloorBarrel");
        this.timePassed = tag.getInteger("timePassed");
        this.timePassedRain = tag.getInteger("timePassedRain");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("isOpen", this.isOpen);
        tag.setBoolean("isFloorBarrel", this.isFloorBarrel);
        tag.setInteger("timePassed", this.timePassed);
        tag.setInteger("timePassedRain", this.timePassedRain);
    }

    public void setFloorBarrel(boolean meta) {
        this.isFloorBarrel = meta;
        this.updateTEState();
    }

    public void setOpen(boolean meta) {
        this.isOpen = meta;
        updateTEState();
    }

    @Override
    public void readFromNBTInventory(NBTTagCompound tag) {
        super.readFromNBTInventory(tag);
        if (tag.hasKey("TankOutput")) {
            tankOutput.readFromNBT(tag.getCompoundTag("TankOutput"));
        }
    }

    @Override
    public void writeToNBTInventory(NBTTagCompound tag) {
        super.writeToNBTInventory(tag);
        NBTTagCompound tankTag = new NBTTagCompound();
        tankOutput.writeToNBT(tankTag);
        tag.setTag("TankOutput", tankTag);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setBoolean("isOpen", this.isOpen);
        tag.setBoolean("isFloorBarrel", this.isFloorBarrel);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.isOpen = tag.getBoolean("isOpen");
        this.isFloorBarrel = tag.getBoolean("isFloorBarrel");
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (doDrain) {
            this.updateTEState();
        }
        if (tankOutput.getFluid() != null) {
            FluidStack stack = tankOutput.drain(maxDrain, false);
            if (stack != null) {
                return tankOutput.drain(maxDrain, doDrain);
            }
        }
        return tank.drain(maxDrain, doDrain);
    }

    public FluidStack getFluidStackRelative() {
        if (getFluidStackOutput() != null) {
            return getFluidStackOutput();
        }
        return getFluidStack();
    }

    public FluidStack getFluidStackProcessed() {
        BarrelRecipe recipe = BarrelRecipe.getRecipe(getInventoryStack(0), getFluidStack());
        if (recipe != null) {
            return recipe.outputLiquid;
        }
        return getFluidStack();
    }

    public FluidStack getFluidStackOutput() {
        return tankOutput.getFluid();
    }

    public float getFillLevelOutput(float min, float max) {
        int capacity = tankOutput.getCapacity();
        if (capacity <= 0) return min;

        float ratio = (float) tankOutput.getFluidAmount() / capacity;
        ratio = MathHelper.clamp_float(ratio, 0f, 1f);
        return ratio * (max - min) + min;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (!this.isOpen) {
            return false;
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

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRaining() && worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord)
            && !this.isFloorBarrel
            && this.isOpen) {
            this.timePassedRain++;
            if (this.timePassedRain > 20) {
                fill(ForgeDirection.UP, waterFluidStack, true);
                this.timePassedRain = 0;
            }
        }
        BarrelRecipe recipe = BarrelRecipe.getRecipe(getInventoryStack(0), getFluidStack());
        if (getInventoryStack(0) == null) {
            timePassed = 0;
        }
        if (recipe != null) {
            timePassed++;
            if (timePassed > recipe.processingTime) {
                timePassed = 0;
                if (recipe.output != null) {
                    addItemInSlotUpdate(1, recipe.output);
                }
                decrStackSize(0, 1);
                tank.drain(recipe.inputLiquid.amount, true);
                tankOutput.fill(recipe.outputLiquid, true);
                updateTEState();
            }
        }
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank.getInfo(), tankOutput.getInfo() };
    }
}
