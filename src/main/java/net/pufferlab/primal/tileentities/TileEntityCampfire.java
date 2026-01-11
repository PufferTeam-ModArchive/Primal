package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.CampfireRecipe;

public class TileEntityCampfire extends TileEntityInventory implements IHeatable {

    public TileEntityCampfire() {
        super(10);
        this.isBuilt = false;
    }

    public int timeFired;
    public boolean isBuilt;
    public boolean hasSpit;
    public int timePassedItem1;
    public int timePassedItem2;
    public int timePassedItem3;
    public int timePassedItem4;
    public static int slotAsh = 0;
    public static int slotItem1 = 6;
    public static int slotItem2 = 7;
    public static int slotItem3 = 8;
    public static int slotItem4 = 9;

    public static int burnTime = 20 * 60;
    public static int smeltTime = 20 * 60;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.timeFired = compound.getInteger("timeFired");
        this.timePassedItem1 = compound.getInteger("timePassedItem1");
        this.timePassedItem2 = compound.getInteger("timePassedItem2");
        this.timePassedItem3 = compound.getInteger("timePassedItem3");
        this.timePassedItem4 = compound.getInteger("timePassedItem4");
        this.isBuilt = compound.getBoolean("isBuilt");
        this.hasSpit = compound.getBoolean("hasSpit");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("timeFired", this.timeFired);
        compound.setInteger("timePassedItem1", this.timePassedItem1);
        compound.setInteger("timePassedItem2", this.timePassedItem2);
        compound.setInteger("timePassedItem3", this.timePassedItem3);
        compound.setInteger("timePassedItem4", this.timePassedItem4);
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
        if (getInventoryStack(slotItem1) == null) {
            this.timePassedItem1 = 0;
        }
        if (getInventoryStack(slotItem2) == null) {
            this.timePassedItem2 = 0;
        }
        if (getInventoryStack(slotItem3) == null) {
            this.timePassedItem3 = 0;
        }
        if (getInventoryStack(slotItem4) == null) {
            this.timePassedItem4 = 0;
        }
        if (isFired) {
            timeFired++;
            if (this.blockMetadata == 0) {
                setFired(false);
            }
            if (canProcess(slotItem1)) {
                this.timePassedItem1++;
            }
            if (canProcess(slotItem2)) {
                this.timePassedItem2++;
            }
            if (canProcess(slotItem3)) {
                this.timePassedItem3++;
            }
            if (canProcess(slotItem4)) {
                this.timePassedItem4++;
            }
            if (timePassedItem1 > smeltTime) {
                timePassedItem1 = 0;
                setOutput(slotItem1);
            }
            if (timePassedItem2 > smeltTime) {
                timePassedItem2 = 0;
                setOutput(slotItem2);
            }
            if (timePassedItem3 > smeltTime) {
                timePassedItem3 = 0;
                setOutput(slotItem3);
            }
            if (timePassedItem4 > smeltTime) {
                timePassedItem4 = 0;
                setOutput(slotItem4);
            }
        }

        if (timeFired > burnTime) {
            timeFired = 0;
            int i = findLastFuel();
            if (i != -1) {
                if (blockMetadata > 0) {
                    markDirty();
                    setInventorySlotContentsUpdate(i);
                    int i2 = 1;
                    if (getInventoryStack(slotAsh) != null) {
                        if (getInventoryStack(slotAsh).stackSize < 10) {
                            i2 = getInventoryStack(slotAsh).stackSize++;
                        }
                    }
                    setInventorySlotContentsUpdateIgnoreMax(slotAsh, Utils.getModItem("ash", i2));
                    this.worldObj
                        .setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.blockMetadata - 1, 2);
                    markDirty();
                }

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
        ItemStack input = this.getStackInSlot(slot);
        ItemStack output = CampfireRecipe.getOutput(input);
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
        boolean slot1 = canBePlaced(6);
        boolean slot2 = canBePlaced(7);
        boolean slot3 = canBePlaced(8);
        boolean slot4 = canBePlaced(9);
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
