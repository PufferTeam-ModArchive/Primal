package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.world.Tasks;

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
    public void onScheduleTask(Tasks task) {
        super.onScheduleTask(task);

        if (task == Tasks.generatorLate) {
            this.scheduleUpdate();
        }
        if (task == Tasks.generator) {
            this.generatedSpeed = this.newGeneratedSpeed;
            this.speed = this.newGeneratedSpeed;
            this.newGeneratedSpeed = 0.0F;
            this.updateTEState();
            this.scheduleUpdate();
            this.scheduleLateUpdate();
        }
    }

    public void scheduleGeneratorUpdate(float newSpeed) {
        addSchedule(0, Tasks.generator);
        this.newGeneratedSpeed = newSpeed;
    }

    public void scheduleLateUpdate() {
        addSchedule(20, Tasks.generatorLate);
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
