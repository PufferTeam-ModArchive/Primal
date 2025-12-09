package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.CampfireRecipe;

public class TileEntityCampfire extends TileEntityInventory {

    private static final int[] blacklistedSlots = new int[] { 0, 1, 2, 3, 4, 5 };

    public TileEntityCampfire() {
        super(10);
        this.isBuilt = false;
        setBlacklistedSlots(blacklistedSlots);
    }

    public int timePassed;
    public boolean isBuilt;
    public boolean hasSpit;
    public int timePassedItem1;
    public int timePassedItem2;
    public int timePassedItem3;
    public int timePassedItem4;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.timePassed = compound.getInteger("timePassed");
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

        compound.setInteger("timePassed", this.timePassed);
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
        tag.setBoolean("isBuilt", this.isBuilt);
        tag.setBoolean("hasSpit", this.hasSpit);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.isBuilt = tag.getBoolean("isBuilt");
        this.hasSpit = tag.getBoolean("hasSpit");
    }

    public int burnTime = 20 * 60;
    public int smeltTime = 20 * 60;

    @Override
    public void updateEntity() {
        if (isFired) {
            timePassed++;
            if (this.blockMetadata == 0) {
                setFired(false);
            }
            if (canProcess(6)) {
                timePassedItem1++;
            }
            if (canProcess(7)) {
                timePassedItem2++;
            }
            if (canProcess(8)) {
                timePassedItem3++;
            }
            if (canProcess(9)) {
                timePassedItem4++;
            }
            if (timePassedItem1 > smeltTime) {
                timePassedItem1 = 0;
                setOutput(6);
            }
            if (timePassedItem2 > smeltTime) {
                timePassedItem2 = 0;
                setOutput(7);
            }
            if (timePassedItem3 > smeltTime) {
                timePassedItem3 = 0;
                setOutput(8);
            }
            if (timePassedItem4 > smeltTime) {
                timePassedItem4 = 0;
                setOutput(9);
            }
        }

        if (timePassed > burnTime) {
            timePassed = 0;
            int i = findLastFuel();
            if (i != -1) {
                if (blockMetadata > 0) {
                    markDirty();
                    setInventorySlotContentsUpdate(i);
                    int i2 = 1;
                    if (getInventoryStack(0) != null) {
                        if (getInventoryStack(0).stackSize < 10) {
                            i2 = getInventoryStack(0).stackSize++;
                        }
                    }
                    setInventorySlotContentsUpdate(0, Utils.getModItem("misc", "item", "ash", i2));
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
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
