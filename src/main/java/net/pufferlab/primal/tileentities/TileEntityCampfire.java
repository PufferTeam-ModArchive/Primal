package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.recipes.CampfireRecipe;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.ScheduleManager;

public class TileEntityCampfire extends TileEntityInventory implements IHeatable, IScheduledTile {

    public static int updateFuel = 0;
    public static int updateItem1 = 1;
    public static int updateItem2 = 2;
    public static int updateItem3 = 3;
    public static int updateItem4 = 4;

    public ScheduleManager manager = new ScheduleManager(
        updateFuel,
        updateItem1,
        updateItem2,
        updateItem3,
        updateItem4);

    public boolean isBuilt;
    public boolean hasSpit;
    public static int slotAsh = 0;
    public static int slotItem1 = 6;
    public static int slotItem2 = 7;
    public static int slotItem3 = 8;
    public static int slotItem4 = 9;

    public TileEntityCampfire() {
        super(10);
        this.isBuilt = false;
    }

    @Override
    public ScheduleManager getManager() {
        return manager;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        manager.readFromNBT(compound);

        this.isBuilt = compound.getBoolean("isBuilt");
        this.hasSpit = compound.getBoolean("hasSpit");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        manager.writeToNBT(compound);

        compound.setBoolean("isBuilt", this.isBuilt);
        compound.setBoolean("hasSpit", this.hasSpit);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        this.writeToNBT(tag);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.readFromNBT(tag);
        updateTELight();
    }

    @Override
    public void updateEntity() {}

    @Override
    public void onSlotUpdate(int index) {
        if (index < 6) {
            removeSchedule(updateFuel);
        }
        if (index == slotItem1) {
            if (canProcess(slotItem1)) {
                addSchedule(getSmeltTime(), updateItem1);
            } else {
                removeSchedule(updateItem1);
            }
        }
        if (index == slotItem2) {
            if (canProcess(slotItem2)) {
                addSchedule(getSmeltTime(), updateItem2);
            } else {
                removeSchedule(updateItem2);
            }
        }
        if (index == slotItem3) {
            if (canProcess(slotItem3)) {
                addSchedule(getSmeltTime(), updateItem3);
            } else {
                removeSchedule(updateItem3);
            }
        }
        if (index == slotItem4) {
            if (canProcess(slotItem4)) {
                addSchedule(getSmeltTime(), updateItem4);
            } else {
                removeSchedule(updateItem4);
            }
        }
        updateTEState();
    }

    @Override
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        IScheduledTile.super.onSchedule(world, x, y, z, type, id);

        if (type == updateFuel) {
            updateFuel();
        }
        if (type == updateItem1) {
            setOutput(slotItem1);
        }
        if (type == updateItem2) {
            setOutput(slotItem2);
        }
        if (type == updateItem3) {
            setOutput(slotItem3);
        }
        if (type == updateItem4) {
            setOutput(slotItem4);
        }
    }

    public void updateFuel() {
        int i = findLastFuel();
        int meta = getMeta();
        if (i != -1) {
            if (meta > 0) {
                setInventorySlotContentsUpdate(i);
                setInventorySlotContentsUpdate(slotAsh, ItemUtils.getModItem("ash", 1));
                this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, meta - 1, 2);
                markDirty();
            }
        }
        sendFuelUpdate();
    }

    public void sendFuelUpdate() {
        if (getMeta() == 0) {
            setFired(false);
            removeSchedule(updateItem1);
            removeSchedule(updateItem2);
            removeSchedule(updateItem3);
            removeSchedule(updateItem4);
        } else {
            addSchedule(Config.campfireBurnTime.getInt(), updateFuel);
        }
    }

    public int findLastFuel() {
        int last = -1;

        for (int i = 0; i < 6; i++) {
            ItemStack stack = getInventoryStack(i);
            if (stack != null && !Utils.containsOreDict(stack, "ash")) {
                last = i;
            }
        }

        return last;
    }

    public void setOutput(int slot) {
        ItemStack input = this.getInventoryStack(slot);
        ItemStack output = CampfireRecipe.getOutput(input);
        if (output == null) return;
        this.setInventorySlotContentsUpdate(slot, output.copy());
    }

    public boolean canProcess(int slot) {
        if (!this.isFired()) return false;
        ItemStack item = this.getInventoryStack(slot);
        return item != null && CampfireRecipe.hasRecipe(item);
    }

    public boolean canBePlaced(int slot) {
        ItemStack item = this.getInventoryStack(slot);
        return item != null;
    }

    public void updateSpit() {
        boolean slot1 = canBePlaced(slotItem1);
        boolean slot2 = canBePlaced(slotItem2);
        boolean slot3 = canBePlaced(slotItem3);
        boolean slot4 = canBePlaced(slotItem4);
        if (!slot1 && !slot2 && !slot3 && !slot4) {
            setSpit(false);
        } else {
            setSpit(true);
        }
    }

    public void setSpit(boolean state) {
        if (this.hasSpit != state) {
            this.hasSpit = state;
            this.updateTEState();
        }
    }

    public int getSmeltTime() {
        return Config.campfireSmeltTime.getInt();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean canBeFired() {
        if (this.blockMetadata != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void setFired(boolean state) {
        if (this.isFired != state) {
            this.isFired = state;
            this.sendFuelUpdate();
            this.onSlotUpdate(slotItem1);
            this.onSlotUpdate(slotItem2);
            this.onSlotUpdate(slotItem3);
            this.onSlotUpdate(slotItem4);
            this.updateTEState();
        }
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord, yCoord + 1, zCoord);
    }

    @Override
    public boolean isFired() {
        return this.isFired;
    }

    @Override
    public int getMaxTemperature() {
        return 200;
    }

    @Override
    public int getTemperature() {
        return 0;
    }

    @Override
    public boolean isHeatProvider() {
        return true;
    }
}
