package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.HeatInfo;
import net.pufferlab.primal.world.ScheduleManager;
import net.pufferlab.primal.world.Tasks;

public class TileEntityForge extends TileEntityInventory implements IHeatable, IScheduledTile {

    public ScheduleManager manager = new ScheduleManager(Tasks.fuel);
    public HeatInfo heat = new HeatInfo(1300);

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
    public HeatInfo getHeatInfo() {
        return heat;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        manager.readFromNBT(tag);
        heat.readFromNBT(tag);

        this.timeUpdate = tag.getInteger("timeUpdate");
        this.lastLevel = tag.getInteger("lastLevel");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        manager.writeToNBT(tag);
        heat.writeToNBT(tag);

        tag.setInteger("timeUpdate", this.timeUpdate);
        tag.setInteger("lastLevel", this.lastLevel);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        heat.readFromNBT(tag);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        heat.writeToNBT(tag);
    }

    @Override
    public void updateEntity() {
        timeUpdate++;
        if (timeUpdate > 20) {
            timeUpdate = 0;
            if (HeatUtils.getHeatingLevel(this.getTemperature()) != this.lastLevel) {
                this.lastLevel = HeatUtils.getHeatingLevel(this.getTemperature());
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
            setTemperature(-1.0F);
        } else {
            addSchedule(Config.campfireBurnTime.getInt(), Tasks.fuel);
            if (isFired()) {
                setTemperature(1.0F);
            }
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
    public boolean isHeatProvider() {
        return true;
    }

    @Override
    public boolean hasFacing() {
        return false;
    }
}
