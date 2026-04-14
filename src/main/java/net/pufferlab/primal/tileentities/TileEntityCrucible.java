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
import net.pufferlab.primal.Primal;
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
        this.readFromNBTFluidInventory(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        heat.writeToNBT(tag);
        manager.writeToNBT(tag);
        tag.setInteger("timeMelting", this.timeMelting);
        tag.setInteger("lastLevel", this.lastLevel);
        this.writeToNBTFluidInventory(tag);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        heat.readFromNBT(tag);
        this.readFromNBTFluidInventory(tag);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        heat.writeToNBT(tag);
        this.writeToNBTFluidInventory(tag);
    }

    public void addFluidInventory(FluidStack stack) {
        for (int i = 0; i < fluidInventory.length; i++) {
            FluidStack stack2 = fluidInventory[i];
            if (Utils.equalsStack(stack2, stack)) {
                stack2.amount = stack2.amount + stack.amount;
                return;
            }
        }
        fluidInventory = Utils.append(fluidInventory, stack.copy());
    }

    public void putFluidInventoryStack(FluidStack stack) {
        Utils.clear(fluidInventory);
        fluidInventory[0] = stack;
    }

    public void removeFluidInventoryStack(FluidStack stack) {
        for (int i = 0; i < fluidInventory.length; i++) {
            if (Utils.equalsStack(stack, fluidInventory[i])) {
                fluidInventory[i] = null;
            }
        }
    }

    @Override
    public void readFromNBTInventory(NBTTagCompound compound) {
        super.readFromNBTInventory(compound);
        heat.readFromNBT(compound);
    }

    public void readFromNBTFluidInventory(NBTTagCompound compound) {
        NBTTagList tagList = compound.getTagList("Fluids", Constants.tagCompound);
        this.fluidInventory = new FluidStack[tagList.tagCount()];
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
    }

    public void writeToNBTFluidInventory(NBTTagCompound compound) {
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

        FluidStack[] fluids = fluidInventory;
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

        for (int j = 0; j < fluidInventory.length; j++) {
            FluidStack stack = fluidInventory[j];
            if (stack != null) {
                if (fill(ForgeDirection.UP, stack, false) > 0) {
                    fill(ForgeDirection.UP, stack, true);
                    removeFluidInventoryStack(stack);
                }
            }
        }
    }

    public FluidStack lastResult;

    public FluidStack getMeltResult() {
        return this.lastResult;
    }

    public void updateMeltCache() {
        this.lastResult = calculateMeltContent();
    }

    public FluidStack calculateMeltContent() {
        fluidInventoryTemp = new FluidStack[0];
        FluidStack lastFluid = null;
        boolean valid = true;
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack stack = getInventoryStack(i);
            if (MeltingRecipe.hasRecipe(stack)) {
                FluidStack output = MeltingRecipe.getOutput(stack)
                    .copy();
                int scaled = stack.stackSize;
                output.amount = output.amount * scaled;
                addFluidInventoryTemp(output);
                if (lastFluid == null) {
                    lastFluid = output;
                    continue;
                }
                if (Utils.equalsStack(output, lastFluid)) {
                    lastFluid.amount = lastFluid.amount + output.amount;
                } else {
                    valid = false;
                }
            }
        }

        FluidStack[] fluids = Utils.combineArrays(fluidInventory, fluidInventoryTemp);
        if (getFluidStack() != null) {
            fluids = Utils.combineArrays(fluids, getFluidStack());
        }
        if (AlloyingRecipe.hasRecipe(fluids)) {
            FluidStack output = AlloyingRecipe.getOutput(fluids);
            return output;
        } else {
            if (!valid) return null;
            return lastFluid;
        }
    }

    FluidStack[] fluidInventoryTemp = new FluidStack[0];

    public void addFluidInventoryTemp(FluidStack stack) {
        for (int i = 0; i < fluidInventoryTemp.length; i++) {
            FluidStack stack2 = fluidInventoryTemp[i];
            if (Utils.equalsStack(stack2, stack)) {
                stack2.amount = stack2.amount + stack.amount;
                return;
            }
        }
        fluidInventoryTemp = Utils.append(fluidInventoryTemp, stack.copy());
    }

    public void updateHeat() {
        TileEntity teBelow = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if (teBelow instanceof IHeatable heat) {
            setMaxTemperature(heat.getTemperature() + 10);
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
            FluidStack[] fluids = fluidInventory;
            for (FluidStack fluid : fluids) {
                if (fluid != null) {
                    scheduleMeltingUpdate();
                }
            }
            addSchedule(40, Tasks.heat);
        }

        if (task == Tasks.inventory) {
            float modifier = 1.0F;
            Primal.proxy.packet.sendCruciblePacket(this);
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
    public boolean canUpdate() {
        return false;
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
