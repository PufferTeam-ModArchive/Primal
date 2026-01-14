package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.events.ticks.GlobalTickingData;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.recipes.AlloyingRecipe;
import net.pufferlab.primal.recipes.MeltingRecipe;
import net.pufferlab.primal.utils.TemperatureUtils;

public class TileEntityCrucible extends TileEntityFluidInventory implements IHeatable {

    public int timeHeat;
    public int timeMelting;
    public int lastLevel;
    public boolean isHeating;
    public FluidStack[] fluidInventory;

    public static int meltingTime = 20 * 10;

    public int temperature;
    public int maxTemperature;

    public TileEntityCrucible() {
        super(3000, 5);
        fluidInventory = new FluidStack[getSizeInventory()];
    }

    @Override
    public String getInventoryName() {
        return "Crucible";
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.timeHeat = tag.getInteger("timeHeat");
        this.timeMelting = tag.getInteger("timeMelting");
        this.lastLevel = tag.getInteger("lastLevel");
        this.isHeating = tag.getBoolean("isHeating");
        this.temperature = tag.getInteger("temperature");
        this.maxTemperature = tag.getInteger("maxTemperature");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setInteger("timeHeat", this.timeHeat);
        tag.setInteger("timeMelting", this.timeMelting);
        tag.setInteger("lastLevel", this.lastLevel);
        tag.setBoolean("isHeating", this.isHeating);
        tag.setInteger("temperature", this.temperature);
        tag.setInteger("maxTemperature", this.maxTemperature);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.isHeating = tag.getBoolean("isHeating");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setBoolean("isHeating", this.isHeating);
    }

    public void addFluidInventory(FluidStack stack) {
        boolean hasExistingFluid = false;
        for (int i = 0; i < getSizeInventory(); i++) {
            FluidStack stack2 = getFluidInventoryStack(i);
            if (Utils.equalsStack(stack2, stack)) {
                stack2.amount = stack2.amount + stack.amount;
                hasExistingFluid = true;
            }
        }
        if (!hasExistingFluid) {
            for (int i = 0; i < getSizeInventory(); i++) {
                FluidStack stack2 = getFluidInventoryStack(i);
                if (stack2 == null) {
                    fluidInventory[i] = stack.copy();
                    break;
                }
            }
        }
    }

    public FluidStack getFluidInventoryStack(int index) {
        return fluidInventory[index];
    }

    public FluidStack[] getFluidInventory() {
        return fluidInventory;
    }

    public void putFluidInventoryStack(FluidStack stack) {
        for (int i = 0; i < getSizeInventory(); i++) {
            fluidInventory[i] = null;
        }
        fluidInventory[0] = stack;
    }

    public void removeFluidInventoryStack(FluidStack stack) {
        for (int i = 0; i < getSizeInventory(); i++) {
            if (Utils.equalsStack(stack, getFluidInventoryStack(i))) {
                fluidInventory[i] = null;
            }
        }
    }

    @Override
    public void readFromNBTInventory(NBTTagCompound compound) {
        super.readFromNBTInventory(compound);
        this.temperature = compound.getInteger("temperature");

        NBTTagList tagList = compound.getTagList("Fluids", 10);
        this.fluidInventory = new FluidStack[getSize()];
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < this.fluidInventory.length)
                this.fluidInventory[slot] = FluidStack.loadFluidStackFromNBT(tag);
        }
    }

    @Override
    public void writeToNBTInventory(NBTTagCompound compound) {
        super.writeToNBTInventory(compound);
        compound.setInteger("temperature", this.temperature);

        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < this.fluidInventory.length; i++) {
            FluidStack stack = this.fluidInventory[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag((NBTBase) tag);
            }
        }
        compound.setTag("Fluids", (NBTBase) itemList);
    }

    boolean needsUpdate = true;

    @Override
    public void updateEntity() {
        if (needsUpdate) {
            needsUpdate = false;
            scheduleInventoryUpdate();
        }
        TileEntity teBelow = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        this.maxTemperature = 1300;
        boolean isForgeFired = false;
        if (teBelow instanceof IHeatable tef) {
            if (tef.isHeatProvider()) {
                this.maxTemperature = tef.getMaxTemperature();
                if (tef.getTemperature() > this.temperature) {
                    if (tef.isFired()) {
                        this.timeHeat++;
                        isForgeFired = true;
                    } else {
                        if (this.temperature > 0 && (this.temperature != tef.getTemperature())) {
                            this.timeHeat--;
                        }
                    }
                } else {
                    if (this.temperature > 0 && (this.temperature != tef.getTemperature())) {
                        this.timeHeat--;
                    }
                }
                this.isHeating = tef.isFired();
            }
        } else {
            if (this.temperature > 0) {
                this.timeHeat--;
            }
            this.isHeating = false;
        }

        if (timeHeat > 3) {
            timeHeat = 0;
            this.temperature++;
        }
        if (timeHeat < -3) {
            timeHeat = 0;
            this.temperature--;
        }

        if (this.needsInventoryUpdate) {
            this.needsInventoryUpdate = false;
            float modifier = 1.5F;
            if (!this.isHeating) {
                modifier = -1.0F;
            }
            updateHeatInventory(modifier, this.maxTemperature);
        }

        if (isForgeFired) {
            timeMelting++;
        }

        if (timeMelting > 40) {
            timeMelting = 0;
            for (int i = 0; i < getSizeInventory(); i++) {
                ItemStack stack = getInventoryStack(i);
                if (MeltingRecipe.hasRecipe(stack)) {
                    if (TemperatureUtils.hasImpl(stack)) {
                        IHeatableItem impl = TemperatureUtils.getImpl(stack);
                        FluidStack output = MeltingRecipe.getOutput(stack);
                        int currentTemperature = TemperatureUtils.getInterpolatedTemperature(
                            GlobalTickingData.getTickTime(this.worldObj),
                            stack.getTagCompound());
                        if (currentTemperature > impl.getMeltingTemperature(stack)) {
                            addFluidInventory(output);
                            decrStackSize(i, 1);
                        }
                    }
                }
            }

            FluidStack[] fluids = getFluidInventory();
            if (getFluidStack() != null) {
                fluids = Utils.combineArrays(fluids, getFluidStack());
            }
            if (AlloyingRecipe.hasRecipe(fluids)) {
                AlloyingRecipe recipe = AlloyingRecipe.getRecipe(fluids);
                FluidStack output = AlloyingRecipe.getOutput(fluids);
                if (Utils.containsStack(getFluidStack(), recipe.inputs)) {
                    drain(ForgeDirection.UP, Integer.MAX_VALUE, true);
                }
                putFluidInventoryStack(output);
            }

            for (int j = 0; j < getSizeInventory(); j++) {
                FluidStack stack = getFluidInventoryStack(j);
                if (stack != null) {
                    if (fill(ForgeDirection.UP, stack, false) > 0) {
                        fill(ForgeDirection.UP, stack, true);
                        removeFluidInventoryStack(stack);
                    }
                }
            }
        }
    }

    @Override
    public void onItemRemoved(ItemStack stack) {
        if (stack != null) {
            if (TemperatureUtils.hasImpl(stack)) {
                IHeatableItem item = TemperatureUtils.getImpl(stack);
                item.updateHeat(stack, this.getWorld(), -1.0F, this.maxTemperature);
            }
        }
    }

    public void updateHeatInventory(float modifier, int maxTemperature) {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getInventoryStack(i);
            if (stack != null) {
                if (TemperatureUtils.hasImpl(stack)) {
                    IHeatableItem item = TemperatureUtils.getImpl(stack);
                    float modifier2 = modifier;
                    if (TemperatureUtils
                        .getInterpolatedTemperature(GlobalTickingData.getTickTime(getWorld()), stack.getTagCompound())
                        > this.temperature) {
                        modifier2 = -0.5F;
                    }
                    if (TemperatureUtils.getMultiplierFromNBT(stack.getTagCompound()) != modifier2) {
                        item.updateHeat(stack, this.getWorld(), modifier2, maxTemperature);
                    }
                }
            }
        }
    }

    @Override
    public boolean canBeFired() {
        return false;
    }

    @Override
    public void setFired(boolean state) {}

    @Override
    public boolean isFired() {
        return this.isFired;
    }

    @Override
    public int getMaxTemperature() {
        return 0;
    }

    @Override
    public int getTemperature() {
        return this.temperature;
    }

    @Override
    public boolean isHeatProvider() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
