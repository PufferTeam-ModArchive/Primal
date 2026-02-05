package net.pufferlab.primal.world;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class UpdateTask {

    public String name;
    public int inTime;
    public long timeSent;
    public long timeScheduled;
    public boolean sentUpdate;

    public UpdateTask(int id) {
        this.name = "UpdateTask" + id;
    }

    public boolean sentUpdate() {
        return sentUpdate;
    }

    public int getTime() {
        return inTime;
    }

    public long getNextUpdate() {
        return timeScheduled;
    }

    public void addUpdate(World world, int inTime) {
        this.inTime = inTime;
        this.timeSent = GlobalTickingData.getTickTime(world);
        this.timeScheduled = timeSent + inTime;
        this.sentUpdate = true;
    }

    public void removeUpdate(World world) {
        this.sentUpdate = false;
        this.timeScheduled = GlobalTickingData.getTickTime(world);
    }

    public void onUpdate(World world) {
        this.sentUpdate = false;
        this.timeScheduled = GlobalTickingData.getTickTime(world);
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger("inTime", this.inTime);
        tag.setLong("timeSent", this.timeSent);
        tag.setLong("timeScheduled", this.timeScheduled);
        tag.setBoolean("sentUpdate", this.sentUpdate);
    }

    public void readFromNBT(NBTTagCompound tag) {
        this.inTime = tag.getInteger("inTime");
        this.timeSent = tag.getLong("timeSent");
        this.timeScheduled = tag.getLong("timeScheduled");
        this.sentUpdate = tag.getBoolean("sentUpdate");
    }

    public static void writeToNBT(NBTTagCompound tag, UpdateTask task) {
        NBTTagCompound tagTask = new NBTTagCompound();
        task.writeToNBT(tagTask);
        tag.setTag(task.name, tagTask);
    }

    public static void readFromNBT(NBTTagCompound tag, UpdateTask task) {
        NBTTagCompound tagTask = tag.getCompoundTag(task.name);
        task.readFromNBT(tagTask);
    }
}
