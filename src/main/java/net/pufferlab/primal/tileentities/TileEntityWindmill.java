package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityWindmill extends TileEntityMotion {

    public float generatedSpeed;
    public boolean needsWindUpdate;
    int timePassed;

    public TileEntityWindmill() {
        this.scheduleWindUpdate();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.generatedSpeed = tag.getFloat("generatedSpeed");
        this.needsWindUpdate = tag.getBoolean("needsWindUpdate");
        this.timePassed = tag.getInteger("timePassed");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("generatedSpeed", this.generatedSpeed);
        tag.setBoolean("needsWindUpdate", this.needsWindUpdate);
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
    public void updateEntity() {
        super.updateEntity();

        this.timePassed++;
        if (this.timePassed > 20) {
            this.timePassed = 0;
            this.scheduleWindUpdate();
        }

        if (this.needsWindUpdate) {
            this.needsWindUpdate = false;
            float newSpeed = getSpeedFromWind();
            if (this.generatedSpeed != newSpeed) {
                this.generatedSpeed = newSpeed;
                this.speed = newSpeed;
                this.updateTEState();
            }
            this.scheduleUpdate();
        }
    }

    public float getSpeedFromWind() {

        float modifier;
        if (isClearedAround()) {
            modifier = 1.0F;
        } else {
            modifier = 0.0F;
        }
        int ideal = 100;
        int relativeY = Math.abs(this.yCoord - ideal);

        float percentage = 1.0F;

        if (relativeY > 0) {
            percentage = (40 - relativeY) / 40.0F;
        }

        if (percentage < 0) {
            percentage = 0;
        }
        return percentage * 5F * modifier;
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
        this.needsWindUpdate = true;
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
