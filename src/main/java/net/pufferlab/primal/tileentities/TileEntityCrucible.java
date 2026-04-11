package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.recipes.AlloyingRecipe;
import net.pufferlab.primal.recipes.MeltingRecipe;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.GlobalTickingData;
import net.pufferlab.primal.world.HeatInfo;
import net.pufferlab.primal.world.ScheduleManager;
import net.pufferlab.primal.world.Tasks;

public class TileEntityCrucible extends TileEntityFluidInventory implements IHeatable, IScheduledTile {

    public ScheduleManager manager = new ScheduleManager(Tasks.heat, Tasks.melting, Tasks.inventory);

    public HeatInfo heat = new HeatInfo(1300);

    public int timeMelting;
    public int lastLevel;
    public boolean isHeating;
    public FluidStack[] fluidInventory;

    public TileEntityCrucible() {
        super(Config.metalIngotValue.getInt() * 20, 5);
        fluidInventory = new FluidStack[getSizeInventory()];
    }

    @Override
    public HeatInfo getHeatInfo() {
        return heat;
    }

    @Override
    public ScheduleManager getManager() {
        return manager;
    }

    @Override
    public String getInventoryName() {
        return "Crucible";
    }

    @Override
    public void init() {
        addSchedule(0, Tasks.heat);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        heat.readFromNBT(tag);
        manager.readFromNBT(tag);
        this.timeMelting = tag.getInteger("timeMelting");
        this.lastLevel = tag.getInteger("lastLevel");
        this.isHeating = tag.getBoolean("isHeating");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        heat.writeToNBT(tag);
        manager.writeToNBT(tag);
        tag.setInteger("timeMelting", this.timeMelting);
        tag.setInteger("lastLevel", this.lastLevel);
        tag.setBoolean("isHeating", this.isHeating);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        heat.readFromNBT(tag);
        this.isHeating = tag.getBoolean("isHeating");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        heat.writeToNBT(tag);
        tag.setBoolean("isHeating", this.isHeating);
    }

    public void addFluidInventory(FluidStack stack) {
        for (int i = 0; i < getSizeInventory(); i++) {
            FluidStack stack2 = getFluidInventoryStack(i);
            if (Utils.equalsStack(stack2, stack)) {
                stack2.amount = stack2.amount + stack.amount;
                return;
            }
        }
        fluidInventory = Utils.append(getFluidInventory(), stack.copy());
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
        heat.readFromNBT(compound);
        NBTTagList tagList = compound.getTagList("Fluids", Constants.tagCompound);
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
        heat.writeToNBT(compound);
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

    public void meltContent() {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getInventoryStack(i);
            if (MeltingRecipe.hasRecipe(stack)) {
                if (HeatUtils.hasImpl(stack)) {
                    IHeatableItem impl = HeatUtils.getImpl(stack);
                    FluidStack output = MeltingRecipe.getOutput(stack)
                        .copy();
                    int scaled = stack.stackSize;
                    output.amount = output.amount * scaled;
                    int currentTemperature = HeatUtils.getInterpolatedTemperature(
                        GlobalTickingData.getTickTime(this.worldObj),
                        stack.getTagCompound());
                    if (currentTemperature > impl.getMeltingTemperature(stack)) {
                        addFluidInventory(output);
                        decrStackSize(i, scaled);
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

    public void updateHeat() {
        TileEntity teBelow = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if (teBelow instanceof IHeatable heat) {
            setMaxTemperature(heat.getTemperature());
            if (heat.isFired()) {
                setTemperatureMultiplier(1.0F);
            } else {
                setTemperatureMultiplier(-1.0F);
            }
        }
    }

    @Override
    public void onScheduleTask(Tasks task) {
        IScheduledTile.super.onScheduleTask(task);

        if (task == Tasks.heat) {
            updateHeat();
            scheduleInventoryUpdate();

            boolean hasIngotMelting = getHeatInventoryMelting();
            if (hasIngotMelting) {
                scheduleMeltingUpdate();
            }
            FluidStack[] fluids = getFluidInventory();
            for (FluidStack fluid : fluids) {
                if (fluid != null) {
                    scheduleMeltingUpdate();
                }
            }
            addSchedule(40, Tasks.heat);
        }

        if (task == Tasks.inventory) {
            float modifier = 1.5F;
            if (!this.isHeating) {
                modifier = 1.0F;
            }
            updateHeatInventory(modifier, this.getTemperature());
        }

        if (task == Tasks.melting) {
            meltContent();
        }
    }

    @Override
    public void onItemRemoved(ItemStack stack) {
        if (stack != null) {
            if (HeatUtils.hasImpl(stack)) {
                IHeatableItem item = HeatUtils.getImpl(stack);
                item.updateHeat(stack, this.getWorld(), -1.0F, this.getMaxTemperature());
            }
        }
    }

    public boolean getHeatInventoryMelting() {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getInventoryStack(i);
            if (stack != null) {
                if (HeatUtils.hasImpl(stack)) {
                    int temp = HeatUtils.getInterpolatedTemperature(
                        GlobalTickingData.getTickTime(this.worldObj),
                        ItemUtils.getOrCreateTagCompound(stack));
                    IHeatableItem item = HeatUtils.getImpl(stack);
                    if (temp > item.getMeltingTemperature(stack)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void scheduleMeltingUpdate() {
        addSchedule(0, Tasks.melting);
    }

    @Override
    public void scheduleInventoryUpdate() {
        addSchedule(0, Tasks.inventory);
    }

    public void updateHeatInventory(float modifier, int maxTemperature) {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getInventoryStack(i);
            if (stack != null) {
                if (HeatUtils.hasImpl(stack)) {
                    IHeatableItem item = HeatUtils.getImpl(stack);
                    int temperature = HeatUtils
                        .getInterpolatedTemperature(GlobalTickingData.getTickTime(getWorld()), stack.getTagCompound());
                    item.updateHeat(stack, this.getWorld(), modifier, temperature, maxTemperature);
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
    public boolean isHeatProvider() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean hasFacing() {
        return false;
    }
}
