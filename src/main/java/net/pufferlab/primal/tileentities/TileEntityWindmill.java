package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;

public class TileEntityWindmill extends TileEntityMotion {

    public static float defaultSpeed = Config.windmillDefaultSpeed.getDefaultFloat();
    public static int idealHeight = Config.windmillIdealHeight.getDefaultInt();
    public static int windRange = Config.windmillRange.getDefaultInt();

    public float generatedSpeed;
    int timePassed;

    public TileEntityWindmill() {
        defaultSpeed = Config.windmillDefaultSpeed.getFloat();
        idealHeight = Config.windmillIdealHeight.getInt();
        windRange = Config.windmillRange.getInt();
    }

    @Override
    public void init() {
        addSchedule(10, updateWind);
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.generatedSpeed = tag.getFloat("generatedSpeed");
        this.timePassed = tag.getInteger("timePassed");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("generatedSpeed", this.generatedSpeed);
        tag.setInteger("timePassed", this.timePassed);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        this.generatedSpeed = tag.getFloat("generatedSpeed");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        tag.setFloat("generatedSpeed", this.generatedSpeed);
    }

    @Override
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        super.onSchedule(world, x, y, z, type, id);

        if (type == updateWind) {
            float newSpeed = getSpeedFromWind();
            if (this.generatedSpeed != newSpeed) {
                this.generatedSpeed = newSpeed;
                this.speed = newSpeed;
                this.updateTEState();
                this.scheduleUpdate();
            }
            addSchedule(300, updateWind);
        }
    }

    public float getSpeedFromWind() {

        float modifier;
        if (isClearedAround()) {
            modifier = 1.0F;
        } else {
            modifier = 0.0F;
        }
        int relativeY = Math.abs(this.yCoord - idealHeight);

        float percentage = 1.0F;

        if (relativeY > 0) {
            percentage = (((float) windRange) - relativeY) / ((float) windRange);
        }

        if (percentage < 0) {
            percentage = 0;
        }
        return percentage * defaultSpeed * modifier;
    }

    public boolean isClearedAround() {
        for (int xf = -5; xf <= 5; xf++) {
            for (int zf = -5; zf <= 5; zf++) {
                if (!(xf == 0 && zf == 0)) {
                    int x2 = this.xCoord;
                    int y2 = this.yCoord;
                    int z2 = this.zCoord;

                    if (axisMeta == 0) {
                        x2 += xf;
                        z2 += zf;
                    } else if (axisMeta == 1) {
                        x2 += xf;
                        y2 += zf;
                    } else if (axisMeta == 2) {
                        y2 += xf;
                        z2 += zf;
                    }

                    Block block = this.worldObj.getBlock(x2, y2, z2);
                    if (block.getMaterial() != Material.air) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public float getGeneratedSpeed() {
        return this.generatedSpeed;
    }

    public void scheduleWindUpdate() {
        addSchedule(0, updateWind);
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord - 5, yCoord - 5, zCoord - 5, xCoord + 5, yCoord + 5, zCoord + 5);
    }

    @Override
    public float getTorque() {
        return 10.0F;
    }
}
