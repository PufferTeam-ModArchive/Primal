package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.network.NetworkMoisture;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.ScheduleManager;
import net.pufferlab.primal.world.Tasks;

public class TileEntityFarmland extends TileEntityPrimal implements IScheduledTile {

    public float moisture = 0.0F;
    public float potassium = 0.5F;
    public float nitrogen = 0.5F;
    public float phosphorus = 0.5F;

    public ScheduleManager manager = new ScheduleManager(Tasks.network, Tasks.nutrient);

    public TileEntityFarmland() {}

    @Override
    public void init() {
        scheduleUpdate();
        addSchedule(Config.farmlandReplenishment.getInt(), Tasks.nutrient);
    }

    @Override
    public ScheduleManager getManager() {
        return manager;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.moisture = tag.getFloat("moisture");
        this.potassium = tag.getFloat("potassium");
        this.nitrogen = tag.getFloat("nitrogen");
        this.phosphorus = tag.getFloat("phosphorus");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setFloat("moisture", this.moisture);
        tag.setFloat("potassium", this.potassium);
        tag.setFloat("nitrogen", this.nitrogen);
        tag.setFloat("phosphorus", this.phosphorus);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        this.moisture = tag.getFloat("moisture");
        this.potassium = tag.getFloat("potassium");
        this.nitrogen = tag.getFloat("nitrogen");
        this.phosphorus = tag.getFloat("phosphorus");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        tag.setFloat("moisture", this.moisture);
        tag.setFloat("potassium", this.potassium);
        tag.setFloat("nitrogen", this.nitrogen);
        tag.setFloat("phosphorus", this.phosphorus);
    }

    @Override
    public void onScheduleTask(Tasks task, long taskTime) {
        IScheduledTile.super.onScheduleTask(task, taskTime);

        if (task == Tasks.network) {
            NetworkMoisture.generateNetwork(this);
        }
        if (task == Tasks.nutrient) {
            replenishNutrients();
            addSchedule(taskTime, Config.farmlandReplenishment.getInt(), Tasks.nutrient);
        }
    }

    public void setMoisture(float moisture) {
        this.moisture = Utils.clamp(0, 1, moisture);
        updateTEState();
    }

    public void setNutrients(float potassium, float nitrogen, float phosphorus) {
        this.potassium = Utils.clamp(0, 1, potassium);
        this.nitrogen = Utils.clamp(0, 1, nitrogen);
        this.phosphorus = Utils.clamp(0, 1, phosphorus);
        updateTEState();
    }

    public void replenishNutrients() {
        float rate = Config.farmlandReplenishmentRate.getFloat();
        setNutrients(
            Utils.clamp(0.0F, 0.5F, this.potassium + (rate)),
            Utils.clamp(0.0F, 0.5F, this.nitrogen + (rate)),
            Utils.clamp(0.0F, 0.5F, this.phosphorus + (rate)));
    }

    public float getGrowthSpeed(char nutrient) {
        return Math.max(0.01F, (getGrowthSpeedMoisture() + getGrowthSpeedNutrients(nutrient)) / 2.0F);
    }

    public float getGrowthSpeedMoisture() {
        return (float) Math.pow(Math.max(0.01, (this.moisture * 100) / 70 - 0.0143), 0.35);
    }

    public float getGrowthSpeedNutrients(char nutrientType) {
        float nutrientAmount = 0.0F;
        if (nutrientType == 'N') {
            nutrientAmount = this.nitrogen;
        } else if (nutrientType == 'K') {
            nutrientAmount = this.potassium;
        } else if (nutrientType == 'P') {
            nutrientAmount = this.phosphorus;
        }
        return -0.0001644F * (nutrientAmount * nutrientAmount) + 0.02582F * nutrientAmount + 0.10F;
    }

    public void consumeNutrient(char nutrientType, float consume) {
        float potassium = this.potassium;
        float nitrogen = this.nitrogen;
        float phosphorus = this.phosphorus;
        if (nutrientType == 'N') {
            nitrogen -= consume;
        } else if (nutrientType == 'K') {
            potassium -= consume;
        } else if (nutrientType == 'P') {
            phosphorus -= consume;
        }
        setNutrients(potassium, nitrogen, phosphorus);
    }

    public void scheduleUpdate() {
        addSchedule(0, Tasks.network);
    }

    @Override
    public boolean canUpdate() {
        return false;
    }
}
