package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
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
    public int lastStackSize;
    public int timeToProcess;
    public boolean canProcess;
    public static int slotInput = 0;
    public static int slotOutput = 1;

    public TileEntityBarrel() {
        super(10000, 2);
        tankOutput = new FluidTank(10000);
        this.setInputSlots(slotInput);
        this.setOutputSlots(slotOutput);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.isOpen = tag.getBoolean("isOpen");
        this.isFloorBarrel = tag.getBoolean("isFloorBarrel");
        this.timePassed = tag.getInteger("timePassed");
        this.timePassedRain = tag.getInteger("timePassedRain");
        this.lastStackSize = tag.getInteger("lastStackSize");
        this.canProcess = tag.getBoolean("canProcess");
        this.timeToProcess = tag.getInteger("timeToProcess");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("isOpen", this.isOpen);
        tag.setBoolean("isFloorBarrel", this.isFloorBarrel);
        tag.setInteger("timePassed", this.timePassed);
        tag.setInteger("timePassedRain", this.timePassedRain);
        tag.setInteger("lastStackSize", this.lastStackSize);
        tag.setBoolean("canProcess", this.canProcess);
        tag.setInteger("timeToProcess", this.timeToProcess);
    }

    public void setFloorBarrel(boolean meta) {
        this.isFloorBarrel = meta;
        this.updateTEState();
    }

    public void setOpen(boolean meta) {
        if (this.isFloorBarrel) {
            this.isOpen = false;
        } else {
            this.isOpen = meta;
        }
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
        this.writeToNBT(tag);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.readFromNBT(tag);
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

        BarrelRecipe recipe = BarrelRecipe.getRecipe(getInventoryStack(slotInput), getFluidStack());
        if (recipe != null) {
            this.timeToProcess = recipe.processingTime;
            int numberInput = getInventoryStack(slotInput).stackSize;
            if (numberInput != lastStackSize) {
                timePassed = 0;
                lastStackSize = numberInput;
            }
            int scaledAmount = recipe.inputLiquid.amount * lastStackSize;
            if (getFluidStack().amount >= (scaledAmount)) {
                this.canProcess = true;
                timePassed++;
                if (timePassed > this.timeToProcess) {
                    timePassed = 0;
                    setInventorySlotContentsUpdate(slotInput);
                    tank.drain(scaledAmount, true);
                    if (recipe.output != null) {
                        ItemStack scaledOutput = recipe.output.copy();
                        scaledOutput.stackSize = lastStackSize;
                        setInventorySlotContentsUpdate(slotOutput, scaledOutput);
                    }
                    int scaledAmountO = recipe.inputLiquid.amount * lastStackSize;
                    if (recipe.outputLiquid != null) {
                        FluidStack scaledOutput = recipe.outputLiquid.copy();
                        scaledOutput.amount = scaledAmountO;
                        tankOutput.fill(scaledOutput, true);
                    }
                    updateTEState();
                }
            } else {
                this.canProcess = false;
            }
        } else {
            this.canProcess = false;
        }
        if (!this.canProcess) {
            timePassed = 0;
            timeToProcess = 0;
        }
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank.getInfo(), tankOutput.getInfo() };
    }
}
