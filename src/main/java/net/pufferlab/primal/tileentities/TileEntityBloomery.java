package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.ScheduleManager;
import net.pufferlab.primal.world.Tasks;

public class TileEntityBloomery extends TileEntityInventory implements IHeatable, IScheduledTile {

    public ScheduleManager manager = new ScheduleManager(Tasks.fuel);

    public int temperature;

    public TileEntityBloomery() {
        super(8);
    }

    @Override
    public ScheduleManager getManager() {
        return manager;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        manager.readFromNBT(tag);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        manager.writeToNBT(tag);
    }

    @Override
    public void onSlotUpdate(int index) {
        removeSchedule(Tasks.fuel);
    }

    @Override
    public void onScheduleTask(Tasks task) {
        IScheduledTile.super.onScheduleTask(task);

        if (task == Tasks.fuel) {
            updateFuel();
        }
    }

    public void updateFuel() {
        int i = findLastFuel();
        if (isFired()) {
            removeFuel();
            if (i != -1) {
                setInventorySlotContentsUpdate(i);
                setInventorySlotContentsUpdate(i, ItemUtils.getModItem("ash", 1));
            }
        }
        sendFuelUpdate();
    }

    public void sendFuelUpdate() {
        if (!hasFuel()) {
            setFired(false);
        } else {
            addSchedule(Config.campfireBurnTime.getInt(), Tasks.fuel);
        }
    }

    public int findLastFuel() {
        int last = -1;

        for (int i = 0; i < 6; i++) {
            ItemStack stack = getInventoryStack(i);
            if (stack != null && !Utils.containsOreDict(stack, "ash")) {
                last = i;
            }
        }

        return last;
    }

    @Override
    public boolean canBeFired() {
        if (hasFuel()) {
            return true;
        }
        return false;
    }

    @Override
    public void setFired(boolean state) {
        IHeatable.super.setFired(state);
        if (this.isFired != state) {
            this.isFired = state;
            this.sendFuelUpdate();
            this.updateTEState();
        }
    }

    @Override
    public boolean consumesFuel() {
        return true;
    }

    @Override
    public int getMaxTemperature() {
        return 1300;
    }

    @Override
    public int getTemperature() {
        return 0;
    }

    @Override
    public boolean isHeatProvider() {
        return true;
    }
}
