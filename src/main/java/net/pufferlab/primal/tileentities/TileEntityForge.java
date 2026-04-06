package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.ScheduleManager;
import net.pufferlab.primal.world.Tasks;

public class TileEntityForge extends TileEntityInventory implements IHeatable, IScheduledTile {

    public ScheduleManager manager = new ScheduleManager(Tasks.fuel);

    public int temperature;
    public int timeHeat;
    public int timeUpdate;
    public int lastLevel;

    public TileEntityForge() {
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

        this.timeHeat = tag.getInteger("timeHeat");
        this.timeUpdate = tag.getInteger("timeUpdate");
        this.temperature = tag.getInteger("temperature");
        this.lastLevel = tag.getInteger("lastLevel");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        manager.writeToNBT(tag);

        tag.setInteger("timeHeat", this.timeHeat);
        tag.setInteger("timeUpdate", this.timeUpdate);
        tag.setInteger("temperature", this.temperature);
        tag.setInteger("lastLevel", this.lastLevel);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.temperature = tag.getInteger("temperature");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setInteger("temperature", this.temperature);
    }

    @Override
    public void updateEntity() {
        if (this.getMaxTemperature() > this.temperature) {
            if (this.isFired()) {
                this.timeHeat++;
            }
        }
        if (!this.isFired()) {
            this.timeHeat--;
        }
        if (timeHeat > 5) {
            timeHeat = 0;
            this.temperature++;
        }
        if (timeHeat < -3) {
            timeHeat = 0;
            if (this.temperature > 0) {
                this.temperature--;
            }
        }

        timeUpdate++;
        if (timeUpdate > 20) {
            timeUpdate = 0;
            if (HeatUtils.getHeatingLevel(this.temperature) != this.lastLevel) {
                this.lastLevel = HeatUtils.getHeatingLevel(this.temperature);
                updateTEState();
            }
        }

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
        int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        if (i != -1) {
            if (meta > 0) {
                markDirty();
                setInventorySlotContentsUpdate(i);
                this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, meta - 1, 2);
                markDirty();
            }
        }
        sendFuelUpdate();
    }

    public void sendFuelUpdate() {
        if (getMeta() == 0) {
            setFired(false);
        } else {
            addSchedule(Config.campfireBurnTime.getInt(), Tasks.fuel);
        }
    }

    public int findLastFuel() {
        int last = -1;

        for (int i = 0; i < 5; i++) {
            ItemStack stack = getInventoryStack(i);
            if (stack != null && !Utils.containsOreDict(stack, "ash")) {
                last = i;
            }
        }

        return last;
    }

    @Override
    public boolean canBeFired() {
        if (this.blockMetadata != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void setFired(boolean state) {
        if (this.isFired != state) {
            this.isFired = state;
            this.sendFuelUpdate();
            this.updateTEState();
        }
    }

    @Override
    public boolean isFired() {
        return this.isFired;
    }

    @Override
    public int getMaxTemperature() {
        return 1300;
    }

    @Override
    public int getTemperature() {
        return this.temperature;
    }

    @Override
    public boolean isHeatProvider() {
        return true;
    }

    @Override
    public boolean hasFacing() {
        return false;
    }
}
