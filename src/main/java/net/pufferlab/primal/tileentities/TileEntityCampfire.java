package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.CampfireRecipe;

public class TileEntityCampfire extends TileEntityInventory implements IHeatable, IScheduledTile {

    public static int updateFuel = 0;
    public long nextUpdateFuel;
    public boolean hasUpdateFuel;
    public boolean needsUpdateFuel;

    public static int updateItem1 = 1;
    public long nextUpdateItem1;
    public boolean hasUpdateItem1;
    public boolean needsUpdateItem1;

    public static int updateItem2 = 2;
    public long nextUpdateItem2;
    public boolean hasUpdateItem2;
    public boolean needsUpdateItem2;

    public static int updateItem3 = 3;
    public long nextUpdateItem3;
    public boolean hasUpdateItem3;
    public boolean needsUpdateItem3;

    public static int updateItem4 = 4;
    public long nextUpdateItem4;
    public boolean hasUpdateItem4;
    public boolean needsUpdateItem4;

    public boolean isBuilt;
    public boolean hasSpit;
    public static int slotAsh = 0;
    public static int slotItem1 = 6;
    public static int slotItem2 = 7;
    public static int slotItem3 = 8;
    public static int slotItem4 = 9;

    public static int burnTime = Config.campfireBurnTime.getDefaultInt();

    public TileEntityCampfire() {
        super(10);
        this.isBuilt = false;

        burnTime = Config.campfireBurnTime.getInt();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.nextUpdateFuel = compound.getLong("nextUpdateFuel");
        this.hasUpdateFuel = compound.getBoolean("hasUpdateFuel");
        this.needsUpdateFuel = compound.getBoolean("needsUpdateFuel");

        this.nextUpdateItem1 = compound.getLong("nextUpdateItem1");
        this.hasUpdateItem1 = compound.getBoolean("hasUpdateItem1");
        this.needsUpdateItem1 = compound.getBoolean("needsUpdateItem1");

        this.nextUpdateItem2 = compound.getLong("nextUpdateItem2");
        this.hasUpdateItem2 = compound.getBoolean("hasUpdateItem2");
        this.needsUpdateItem2 = compound.getBoolean("needsUpdateItem2");

        this.nextUpdateItem3 = compound.getLong("nextUpdateItem3");
        this.hasUpdateItem3 = compound.getBoolean("hasUpdateItem3");
        this.needsUpdateItem3 = compound.getBoolean("needsUpdateItem3");

        this.nextUpdateItem4 = compound.getLong("nextUpdateItem4");
        this.hasUpdateItem4 = compound.getBoolean("hasUpdateItem4");
        this.needsUpdateItem4 = compound.getBoolean("needsUpdateItem4");

        this.isBuilt = compound.getBoolean("isBuilt");
        this.hasSpit = compound.getBoolean("hasSpit");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setLong("nextUpdateFuel", this.nextUpdateFuel);
        compound.setBoolean("hasUpdateFuel", this.hasUpdateFuel);
        compound.setBoolean("needsUpdateFuel", this.needsUpdateFuel);

        compound.setLong("nextUpdateItem1", this.nextUpdateItem1);
        compound.setBoolean("hasUpdateItem1", this.hasUpdateItem1);
        compound.setBoolean("needsUpdateItem1", this.needsUpdateItem1);

        compound.setLong("nextUpdateItem2", this.nextUpdateItem2);
        compound.setBoolean("hasUpdateItem2", this.hasUpdateItem2);
        compound.setBoolean("needsUpdateItem2", this.needsUpdateItem2);

        compound.setLong("nextUpdateItem3", this.nextUpdateItem3);
        compound.setBoolean("hasUpdateItem3", this.hasUpdateItem3);
        compound.setBoolean("needsUpdateItem3", this.needsUpdateItem3);

        compound.setLong("nextUpdateItem4", this.nextUpdateItem4);
        compound.setBoolean("hasUpdateItem4", this.hasUpdateItem4);
        compound.setBoolean("needsUpdateItem4", this.needsUpdateItem4);

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
    public void updateEntity() {
        if (isFired) {
            if (!hasUpdateFuel) {
                addSchedule(burnTime, updateFuel);
            }
            if (this.blockMetadata == 0) {
                setFired(false);
            }
            if (canProcess(slotItem1) && !hasUpdateItem1) {
                addSchedule(getSmeltTime(), updateItem1);
            }
            if (canProcess(slotItem2) && !hasUpdateItem2) {
                addSchedule(getSmeltTime(), updateItem2);
            }
            if (canProcess(slotItem3) && !hasUpdateItem3) {
                addSchedule(getSmeltTime(), updateItem3);
            }
            if (canProcess(slotItem4) && !hasUpdateItem4) {
                addSchedule(getSmeltTime(), updateItem4);
            }
            if (needsUpdateItem1) {
                needsUpdateItem1 = false;
                setOutput(slotItem1);
            }
            if (needsUpdateItem2) {
                needsUpdateItem2 = false;
                setOutput(slotItem2);
            }
            if (needsUpdateItem3) {
                needsUpdateItem3 = false;
                setOutput(slotItem3);
            }
            if (needsUpdateItem4) {
                needsUpdateItem4 = false;
                setOutput(slotItem4);
            }
            if (needsUpdateFuel) {
                needsUpdateFuel = false;
                updateFuel();
            }
        }
    }

    @Override
    public void onSlotUpdated(int index) {
        if (index < 6) {
            removeSchedule(updateFuel);
        }
        if (index == slotItem1) {
            removeSchedule(updateItem1);
        }
        if (index == slotItem2) {
            removeSchedule(updateItem2);
        }
        if (index == slotItem3) {
            removeSchedule(updateItem3);
        }
        if (index == slotItem4) {
            removeSchedule(updateItem4);
        }
    }

    @Override
    public void addSchedule(int inTime, int type) {
        IScheduledTile.super.addSchedule(inTime, type);

        long time = getWorldTime(inTime);
        if (type == updateFuel) {
            nextUpdateFuel = time;
            hasUpdateFuel = true;
        }
        if (type == updateItem1) {
            nextUpdateItem1 = time;
            hasUpdateItem1 = true;
        }
        if (type == updateItem2) {
            nextUpdateItem2 = time;
            hasUpdateItem2 = true;
        }
        if (type == updateItem3) {
            nextUpdateItem3 = time;
            hasUpdateItem3 = true;
        }
        if (type == updateItem4) {
            nextUpdateItem4 = time;
            hasUpdateItem4 = true;
        }
    }

    @Override
    public void removeSchedule(int type) {
        IScheduledTile.super.removeSchedule(type);

        if (type == updateFuel) {
            hasUpdateFuel = false;
        }
        if (type == updateItem1) {
            hasUpdateItem1 = false;
        }
        if (type == updateItem2) {
            hasUpdateItem2 = false;
        }
        if (type == updateItem3) {
            hasUpdateItem3 = false;
        }
        if (type == updateItem4) {
            hasUpdateItem4 = false;
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();

        removeAllSchedule();
    }

    @Override
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        if (type == updateFuel) {
            needsUpdateFuel = true;
            hasUpdateFuel = false;
        }
        if (type == updateItem1) {
            needsUpdateItem1 = true;
            hasUpdateItem1 = false;
        }
        if (type == updateItem2) {
            needsUpdateItem2 = true;
            hasUpdateItem2 = false;
        }
        if (type == updateItem3) {
            needsUpdateItem3 = true;
            hasUpdateItem3 = false;
        }
        if (type == updateItem4) {
            needsUpdateItem4 = true;
            hasUpdateItem4 = false;
        }
    }

    public void updateFuel() {
        int i = findLastFuel();
        int meta = this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord);
        if (i != -1) {
            if (meta > 0) {
                setInventorySlotContentsUpdate(i);
                setInventorySlotContentsUpdate(slotAsh, Utils.getModItem("ash", 1));
                this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, meta - 1, 2);
                markDirty();
            }
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
        return true;
    }

    @Override
    public void setFired(boolean state) {
        if (this.isFired != state) {
            this.isFired = state;
            this.updateTEState();
        }
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
