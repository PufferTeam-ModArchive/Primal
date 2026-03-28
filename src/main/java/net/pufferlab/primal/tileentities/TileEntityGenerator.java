package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileEntityGenerator extends TileEntityMotion {

    public float generatedSpeed;
    public float newGeneratedSpeed;

    public TileEntityGenerator() {}

    @Override
    public void init() {
        this.scheduleGeneratorUpdate(10.0F);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.generatedSpeed = tag.getFloat("generatedSpeed");
        this.newGeneratedSpeed = tag.getFloat("newGeneratedSpeed");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("generatedSpeed", this.generatedSpeed);
        tag.setFloat("newGeneratedSpeed", this.newGeneratedSpeed);
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
    public boolean canUpdate() {
        return false;
    }

    @Override
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        super.onSchedule(world, x, y, z, type, id);

        if (type == updateGeneratorLate) {
            this.scheduleUpdate();
        }
        if (type == updateGenerator) {
            this.generatedSpeed = this.newGeneratedSpeed;
            this.speed = this.newGeneratedSpeed;
            this.newGeneratedSpeed = 0.0F;
            this.updateTEState();
            this.scheduleUpdate();
            this.scheduleLateUpdate();
        }
    }

    public void scheduleGeneratorUpdate(float newSpeed) {
        addSchedule(0, updateGenerator);
        this.newGeneratedSpeed = newSpeed;
    }

    public void scheduleLateUpdate() {
        addSchedule(20, updateGeneratorLate);
    }

    @Override
    public float getGeneratedSpeed() {
        return this.generatedSpeed;
    }

    @Override
    public float getTorque() {
        return 1000.0F;
    }
}
