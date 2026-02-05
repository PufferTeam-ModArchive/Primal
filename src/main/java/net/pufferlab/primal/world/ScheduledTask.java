package net.pufferlab.primal.world;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.blocks.IScheduledBlock;
import net.pufferlab.primal.tileentities.IScheduledTile;

public class ScheduledTask implements Comparable<ScheduledTask> {

    public static final byte simpleTask = 0;
    public static final byte blockTask = 1;
    public static final byte tileTask = 2;

    long timeCurrent, timeScheduled, packedCoords;
    byte taskType;
    int blockID, x, y, z, type, id;

    public ScheduledTask(NBTTagCompound tag) {
        readFromNBT(tag);
    }

    public ScheduledTask(byte taskType, long currentTime, int inTime) {
        this.taskType = taskType;
        this.timeCurrent = currentTime;
        this.timeScheduled = currentTime + inTime;
    }

    public ScheduledTask(byte taskType, Block block, long currentTime, int inTime, int x, int y, int z, int type,
        int id) {
        this.taskType = taskType;
        this.blockID = Block.getIdFromBlock(block);
        this.timeCurrent = currentTime;
        this.timeScheduled = currentTime + inTime;
        this.packedCoords = Utils.packCoord(x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.id = id;
    }

    @Override
    public int compareTo(ScheduledTask o) {
        return Long.compare(this.timeScheduled, o.timeScheduled);
    }

    public void updateCoords(int newX, int newY, int newZ) {
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.packedCoords = Utils.packCoord(newX, newY, newZ);
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setByte("task", this.taskType);
        tag.setLong("timeSent", timeCurrent);
        tag.setLong("time", timeScheduled);
        tag.setInteger("blockID", blockID);
        tag.setLong("packedCoords", packedCoords);
        tag.setInteger("x", x);
        tag.setInteger("y", y);
        tag.setInteger("z", z);
        tag.setInteger("type", type);
        tag.setInteger("id", id);
    }

    public void readFromNBT(NBTTagCompound tag) {
        taskType = tag.getByte("task");
        timeCurrent = tag.getLong("timeSent");
        timeScheduled = tag.getLong("time");
        blockID = tag.getInteger("blockID");
        packedCoords = tag.getLong("packedCoords");
        x = tag.getInteger("x");
        y = tag.getInteger("y");
        z = tag.getInteger("z");
        type = tag.getInteger("type");
        id = tag.getInteger("id");
    }

    public Block getBlock() {
        return Block.getBlockById(this.blockID);
    }

    public boolean equals(Block block, int x, int y, int z) {
        if (this.x == x && this.y == y && this.z == z && this.blockID == Block.getIdFromBlock(block)) {
            return true;
        }
        return false;
    }

    public boolean equals(int x, int y, int z) {
        if (this.x == x && this.y == y && this.z == z) {
            return true;
        }
        return false;
    }

    public boolean equals(Block block, int x, int y, int z, int type) {
        if (this.x == x && this.y == y
            && this.z == z
            && this.type == type
            && this.blockID == Block.getIdFromBlock(block)) {
            return true;
        }
        return false;
    }

    public boolean equals(int x, int y, int z, int type) {
        if (this.x == x && this.y == y && this.z == z && this.type == type) {
            return true;
        }
        return false;
    }

    public boolean equals(int type) {
        if (this.type == type) {
            return true;
        }
        return false;
    }

    public boolean run(World world) {
        switch (this.taskType) {
            case simpleTask: {
                Primal.LOG.info("ScheduledTask Ran");
                return true;
            }
            case blockTask: {
                if(!isLoaded(world)) {
                    SchedulerData.addWaitingTask(this, world);
                    return false;
                }
                Block block = world.getBlock(this.x, this.y, this.z);
                if (getBlock() == block) {
                    if (block instanceof IScheduledBlock block2) {
                        block2.onSchedule(world, this.x, this.y, this.z, this.type, this.id);
                        return true;
                    }
                }
            }
            case tileTask: {
                if(!isLoaded(world)) {
                    SchedulerData.addWaitingTask(this, world);
                    return false;
                }
                Block block = world.getBlock(this.x, this.y, this.z);
                if (getBlock() == block) {
                    TileEntity te = world.getTileEntity(this.x, this.y, this.z);
                    if (te instanceof IScheduledTile te2) {
                        te2.onSchedule(world, this.x, this.y, this.z, this.type, this.id);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isLoaded(World world) {
        if(!world.blockExists(this.x, this.y, this.z)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ScheduledTask{" + "inTime="
            + (timeScheduled - GlobalTickingData.getTickTime())
            + ", taskType="
            + taskType
            + ", x="
            + x
            + ", y="
            + y
            + ", z="
            + z
            + ", type="
            + type
            + ", id="
            + id
            + '}';
    }
}
