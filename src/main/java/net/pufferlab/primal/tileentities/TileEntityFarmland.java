package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.world.ScheduleManager;

public class TileEntityFarmland extends TileEntityPrimal implements IScheduledTile {

    public float moisture = 0.0F;
    public float potassium = 0.0F;
    public float nitrogen = 0.0F;
    public float phosphorus = 0.0F;

    public static int updateMoisture = 0;
    public static int updatePotassium = 1;
    public static int updateNitrogen = 2;
    public static int updatePhosphorus = 3;

    public ScheduleManager manager = new ScheduleManager(
        updateMoisture,
        updatePotassium,
        updateNitrogen,
        updatePhosphorus);

    public TileEntityFarmland() {}

    @Override
    public void init() {
        addSchedule(10, updateMoisture);
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
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        IScheduledTile.super.onSchedule(world, x, y, z, type, id);

        if (type == updateMoisture) {
            setMoisture(getMoisture());
            addSchedule(200, updateMoisture);
        }
    }

    public void setMoisture(float moisture) {
        this.moisture = moisture;
        updateTEState();
    }

    public void setNutrients(float potassium, float nitrogen, float phosphorus) {
        this.potassium = potassium;
        this.nitrogen = nitrogen;
        this.phosphorus = phosphorus;
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

    @Override
    public boolean canUpdate() {
        return false;
    }
}
