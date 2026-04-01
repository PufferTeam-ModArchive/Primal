package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.ScheduleManager;
import net.pufferlab.primal.world.Tasks;

public class TileEntityFarmland extends TileEntityPrimal implements IScheduledTile {

    public float moisture = 0.0F;
    public float potassium = 0.5F;
    public float nitrogen = 0.5F;
    public float phosphorus = 0.5F;

    public ScheduleManager manager = new ScheduleManager(Tasks.moisture, Tasks.nutrient);

    public TileEntityFarmland() {}

    @Override
    public void init() {
        addSchedule(0, Tasks.moisture);
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
    public void onScheduleTask(Tasks task) {
        IScheduledTile.super.onScheduleTask(task);

        if (task == Tasks.moisture) {
            setMoisture(getMoisture());
            addSchedule(200, Tasks.moisture);
        }
        if (task == Tasks.nutrient) {
            replenishNutrients();
            addSchedule(Config.farmlandReplenishment.getInt(), Tasks.nutrient);
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

    public float getMoisture() {
        float best = 0.0F;
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {
                int x2 = this.xCoord + x;
                int z2 = this.zCoord + z;
                Block block = this.worldObj.getBlock(x2, this.yCoord, z2);
                if (BlockUtils.isWaterBlock(block)) {
                    float dist = Math.max(Math.abs(x), Math.abs(z));

                    float moisture = 1.0F - (dist / 4.0F);

                    if (moisture > best) {
                        best = moisture;
                    }
                }
            }
        }
        return best;
    }

    public void replenishNutrients() {
        float rate = Config.farmlandReplenishmentRate.getFloat();
        setNutrients(
            Utils.clamp(0.0F, 0.5F, this.potassium + (this.potassium * rate)),
            Utils.clamp(0.0F, 0.5F, this.nitrogen + (this.nitrogen * rate)),
            Utils.clamp(0.0F, 0.5F, this.phosphorus + (this.phosphorus * rate)));
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

    @Override
    public boolean canUpdate() {
        return false;
    }
}
