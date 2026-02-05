package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.utils.TemperatureUtils;

public class TileEntityForge extends TileEntityInventory implements IHeatable, IScheduledTile {

    public static int burnTime = Config.forgeBurnTime.getDefaultInt();

    public static int updateFuel = 0;
    public long nextUpdateFuel;
    public boolean hasUpdateFuel;
    public boolean needsUpdateFuel;

    public int temperature;
    public int timeHeat;
    public int timeUpdate;
    public int lastLevel;

    public TileEntityForge() {
        super(8);

        burnTime = Config.forgeBurnTime.getInt();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.nextUpdateFuel = tag.getLong("nextUpdateFuel");
        this.hasUpdateFuel = tag.getBoolean("hasUpdateFuel");
        this.needsUpdateFuel = tag.getBoolean("needsUpdateFuel");

        this.timeHeat = tag.getInteger("timeHeat");
        this.timeUpdate = tag.getInteger("timeUpdate");
        this.temperature = tag.getInteger("temperature");
        this.lastLevel = tag.getInteger("lastLevel");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setLong("nextUpdateFuel", this.nextUpdateFuel);
        tag.setBoolean("hasUpdateFuel", this.hasUpdateFuel);
        tag.setBoolean("needsUpdateFuel", this.needsUpdateFuel);

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
            if (TemperatureUtils.getHeatingLevel(this.temperature) != this.lastLevel) {
                this.lastLevel = TemperatureUtils.getHeatingLevel(this.temperature);
                updateTEState();
            }
        }

        if (isFired) {
            if (!hasUpdateFuel) {
                addSchedule(burnTime, updateFuel);
            }
            if (this.blockMetadata == 0) {
                setFired(false);
            }
        }

        if (this.needsUpdateFuel) {
            this.needsUpdateFuel = false;
            updateFuel();
        }
    }

    @Override
    public void onSlotUpdate(int index) {
        removeSchedule(updateFuel);
    }

    @Override
    public void addSchedule(int inTime, int type) {
        IScheduledTile.super.addSchedule(inTime, type);

        long time = getWorldTime(inTime);
        if (type == updateFuel) {
            nextUpdateFuel = time;
            hasUpdateFuel = true;
        }
    }

    @Override
    public void removeSchedule(int type) {
        IScheduledTile.super.removeSchedule(type);

        if (type == updateFuel) {
            hasUpdateFuel = false;
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();

        removeAllSchedule();
    }

    @Override
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        if (type == updateFuel) {
            needsUpdateFuel = true;
            hasUpdateFuel = false;
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
        return true;
    }

    @Override
    public void setFired(boolean state) {
        this.isFired = state;
        this.updateTEState();
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
}
