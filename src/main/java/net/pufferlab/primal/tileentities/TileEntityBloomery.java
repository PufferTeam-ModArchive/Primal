package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.UpdateTask;

public class TileEntityBloomery extends TileEntityInventory implements IHeatable, IScheduledTile {

    public static int updateFuel = 0;
    public UpdateTask taskFuel = new UpdateTask(updateFuel);

    public int temperature;

    public TileEntityBloomery() {
        super(8);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        UpdateTask.readFromNBT(tag, taskFuel);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        UpdateTask.writeToNBT(tag, taskFuel);
    }

    @Override
    public void onSlotUpdate(int index) {
        removeSchedule(updateFuel);
    }

    @Override
    public void addSchedule(int inTime, int type) {
        IScheduledTile.super.addSchedule(inTime, type);

        if (type == updateFuel) {
            taskFuel.addUpdate(this.worldObj, inTime);
        }
    }

    @Override
    public void removeSchedule(int type) {
        IScheduledTile.super.removeSchedule(type);

        if (type == updateFuel) {
            taskFuel.removeUpdate(this.worldObj);
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();

        removeAllSchedule();
    }

    @Override
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        IScheduledTile.super.onSchedule(world, x, y, z, type, id);

        if (type == updateFuel) {
            taskFuel.onUpdate(this.worldObj);
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
            if (!taskFuel.hasSentUpdate()) {
                addSchedule(Config.campfireBurnTime.getInt(), updateFuel);
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
