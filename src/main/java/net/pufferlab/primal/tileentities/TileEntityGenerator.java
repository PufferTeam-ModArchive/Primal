package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityGenerator extends TileEntityMotion {

    public float generatedSpeed;
    public boolean needsGeneratorUpdate;
    public float newGeneratedSpeed;

    public TileEntityGenerator() {
        this.scheduleGeneratorUpdate(10.0F);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.generatedSpeed = tag.getFloat("generatedSpeed");
        this.needsGeneratorUpdate = tag.getBoolean("needsGeneratorUpdate");
        this.newGeneratedSpeed = tag.getFloat("newGeneratedSpeed");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("generatedSpeed", this.generatedSpeed);
        tag.setBoolean("needsGeneratorUpdate", this.needsGeneratorUpdate);
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
    public void updateEntity() {
        super.updateEntity();

        if (this.needsGeneratorUpdate) {
            this.needsGeneratorUpdate = false;
            this.generatedSpeed = this.newGeneratedSpeed;
            this.speed = this.newGeneratedSpeed;
            this.newGeneratedSpeed = 0.0F;
            this.updateTEState();
            this.scheduleUpdate();
        }
    }

    public void scheduleGeneratorUpdate(float newSpeed) {
        this.needsGeneratorUpdate = true;
        this.newGeneratedSpeed = newSpeed;
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
