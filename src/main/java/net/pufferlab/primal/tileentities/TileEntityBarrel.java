package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import net.pufferlab.primal.recipes.BarrelRecipe;
import net.pufferlab.primal.world.UpdateTask;

public class TileEntityBarrel extends TileEntityFluidInventory implements IScheduledTile {

    public static final FluidStack waterFluidStack = new FluidStack(FluidRegistry.WATER, 5);
    public boolean isFloorBarrel = false;
    public boolean isOpen = false;
    private final FluidTank tankOutput;
    public int lastStackSize;
    public int recipeIndex;
    public boolean canProcess;
    public static int slotInput = 0;
    public static int slotOutput = 1;

    public static int updateRain = 0;
    public UpdateTask taskRain = new UpdateTask(updateRain);

    public static int updateProcess = 1;
    public UpdateTask taskProcess = new UpdateTask(updateProcess);

    public TileEntityBarrel() {
        super(10000, 2);
        tankOutput = new FluidTank(10000);
        this.setInputSlots(slotInput);
        this.setOutputSlots(slotOutput);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        UpdateTask.readFromNBT(tag, taskRain);
        UpdateTask.readFromNBT(tag, taskProcess);

        this.isOpen = tag.getBoolean("isOpen");
        this.isFloorBarrel = tag.getBoolean("isFloorBarrel");

        this.recipeIndex = tag.getInteger("recipeIndex");
        this.lastStackSize = tag.getInteger("lastStackSize");
        this.canProcess = tag.getBoolean("canProcess");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        UpdateTask.writeToNBT(tag, taskRain);
        UpdateTask.writeToNBT(tag, taskProcess);

        tag.setBoolean("isOpen", this.isOpen);
        tag.setBoolean("isFloorBarrel", this.isFloorBarrel);

        tag.setInteger("recipeIndex", this.recipeIndex);
        tag.setInteger("lastStackSize", this.lastStackSize);
        tag.setBoolean("canProcess", this.canProcess);
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
            onFluidUpdate();
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
        if (!taskRain.hasSentUpdate() && worldObj.isRaining()
            && worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord)
            && !this.isFloorBarrel
            && this.isOpen) {
            addSchedule(20, updateRain);
        }
    }

    public void updateRecipe() {
        BarrelRecipe recipe = BarrelRecipe.getRecipe(getInventoryStack(slotInput), getFluidStack());
        if (recipe != null) {
            this.recipeIndex = recipe.recipeID;
        }
    }

    public void fillRainWater() {
        fill(ForgeDirection.UP, waterFluidStack, true);
    }

    public void processBarrel(boolean process) {
        BarrelRecipe recipe = getRecipe();
        ItemStack input = getInventoryStack(slotInput);
        if (recipe != null && input != null) {
            int numberInput = input.stackSize;
            if (numberInput != lastStackSize) {
                removeSchedule(updateProcess);
                lastStackSize = numberInput;
            }
            int scaledAmount = recipe.inputLiquid.amount * lastStackSize;
            if (getFluidStack().amount >= (scaledAmount)) {
                this.canProcess = true;
                if (!taskProcess.hasSentUpdate()) {
                    addSchedule(recipe.processingTime, updateProcess);
                }
                if (process) {
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
            if (taskProcess.hasSentUpdate()) {
                removeSchedule(updateProcess);
            }
        }
    }

    public BarrelRecipe getRecipe() {
        return BarrelRecipe.getRecipeList()
            .get(this.recipeIndex);
    }

    @Override
    public void onSlotUpdate(int index) {
        updateRecipe();
        processBarrel(false);
    }

    @Override
    public void onFluidUpdate() {
        updateRecipe();
        processBarrel(false);
    }

    @Override
    public void addSchedule(int inTime, int type) {
        IScheduledTile.super.addSchedule(inTime, type);

        if (type == updateRain) {
            taskRain.addUpdate(this.worldObj, inTime);
        }
        if (type == updateProcess) {
            taskProcess.addUpdate(this.worldObj, inTime);
        }
    }

    @Override
    public void removeSchedule(int type) {
        IScheduledTile.super.removeSchedule(type);

        if (type == updateRain) {
            taskRain.removeUpdate(this.worldObj);
        }
        if (type == updateProcess) {
            taskProcess.removeUpdate(this.worldObj);
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();

        removeAllSchedule();
    }

    @Override
    public void onCoordChange(int oldX, int oldY, int oldZ) {
        super.onCoordChange(oldX, oldY, oldZ);

        moveAllSchedule(getWorldObj(), oldX, oldY, oldZ);
    }

    @Override
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        if (type == updateRain) {
            taskRain.onUpdate(this.worldObj);
            fillRainWater();
        }
        if (type == updateProcess) {
            taskProcess.onUpdate(this.worldObj);
            processBarrel(true);
        }
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank.getInfo(), tankOutput.getInfo() };
    }

    @Override
    public int getInventoryStackLimitAutomation() {
        return 1;
    }
}
