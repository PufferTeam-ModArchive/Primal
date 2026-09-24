package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.scheduling.Task;

import io.netty.buffer.ByteBuf;

public class TileEntityWaterwheel extends TileEntityMotion {

    public boolean isExtension;
    public int baseXCoord;
    public int baseYCoord;
    public int baseZCoord;
    public float generatedSpeed;

    public TileEntityWaterwheel() {}

    @Override
    public void init() {
        this.scheduleFlowUpdate();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.isExtension = tag.getBoolean("isExtension");
        this.baseXCoord = tag.getInteger("baseX");
        this.baseYCoord = tag.getInteger("baseY");
        this.baseZCoord = tag.getInteger("baseZ");
        this.generatedSpeed = tag.getFloat("generatedSpeed");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("isExtension", this.isExtension);
        tag.setInteger("baseX", this.baseXCoord);
        tag.setInteger("baseY", this.baseYCoord);
        tag.setInteger("baseZ", this.baseZCoord);
        tag.setFloat("generatedSpeed", this.generatedSpeed);
    }

    @Override
    public void readFromBuffer(ByteBuf buf) {
        super.readFromBuffer(buf);

        this.isExtension = buf.readBoolean();
        this.generatedSpeed = buf.readFloat();
        this.baseXCoord = buf.readInt();
        this.baseYCoord = buf.readInt();
        this.baseZCoord = buf.readInt();
    }

    @Override
    public void writeToBuffer(ByteBuf buf) {
        super.writeToBuffer(buf);

        buf.writeBoolean(this.isExtension);
        buf.writeFloat(this.generatedSpeed);
        buf.writeInt(this.baseXCoord);
        buf.writeInt(this.baseYCoord);
        buf.writeInt(this.baseZCoord);
    }

    @Override
    public void onScheduleTask(Task task, long taskTime) {
        super.onScheduleTask(task, taskTime);

        if (task == Task.flow) {
            float newSpeed = getSpeedFromFlow();
            if (this.generatedSpeed != newSpeed) {
                this.generatedSpeed = newSpeed;
                this.speed = newSpeed;
                this.updateTEState();
                this.scheduleUpdate();
            }
        }
    }

    @Override
    public boolean hasConnection(int side) {
        if (!isExtension) {
            return super.hasConnection(side);
        }
        return false;
    }

    @Override
    public float getGeneratedSpeed() {
        return this.generatedSpeed;
    }

    @Override
    public float getTorque() {
        return 10.0F;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord - 2, zCoord - 2, xCoord + 2, yCoord + 2, zCoord + 2);
    }

    public float getSpeedFromFlow() {
        if (Config.waterwheelRestrictBiome.getBoolean()
            && !Utils.isRiverBiome(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) return 0.0F;

        float totalSpeed = 0.0F;
        float defaultSpeed = Config.waterwheelDefaultSpeed.getFloat();
        if (axisMeta == 1) {
            if (hasLiquid(ForgeDirection.WEST)) {
                totalSpeed = totalSpeed + defaultSpeed;
            }
            if (hasLiquid(ForgeDirection.EAST)) {
                totalSpeed = totalSpeed - defaultSpeed;
            }
        }
        if (axisMeta == 2) {
            if (hasLiquid(ForgeDirection.SOUTH)) {
                totalSpeed = totalSpeed + defaultSpeed;
            }
            if (hasLiquid(ForgeDirection.NORTH)) {
                totalSpeed = totalSpeed - defaultSpeed;
            }
        }
        return totalSpeed;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    public boolean hasLiquid(ForgeDirection facing) {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (BlockUtils.getAxis(direction.ordinal()) != axisMeta
                && BlockUtils.getAxis(direction.ordinal()) != BlockUtils.getAxis(facing.ordinal())) {
                Block block = worldObj.getBlock(
                    this.xCoord + (facing.offsetX * 2) + direction.offsetX,
                    this.yCoord + (facing.offsetY * 2) + direction.offsetY,
                    this.zCoord + (facing.offsetZ * 2) + direction.offsetZ);
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void scheduleFlowUpdate() {
        addSchedule(0, Task.flow);
    }
}
