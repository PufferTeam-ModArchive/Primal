package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Utils;

public class TileEntityCampfire extends TileEntityInventory {

    public TileEntityCampfire() {
        super(8);
        this.isBuilt = false;
    }

    public int timePassed;
    public boolean isBuilt;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.timePassed = compound.getInteger("timePassed");
        this.isBuilt = compound.getBoolean("isBuilt");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("timePassed", this.timePassed);
        compound.setBoolean("isBuilt", this.isBuilt);
    }

    public void getDescriptionPacketExtra(NBTTagCompound dataTag) {
        dataTag.setBoolean("isBuilt", this.isBuilt);
    }

    public void onDataPacketExtra(NBTTagCompound nbtData) {
        this.isBuilt = nbtData.getBoolean("isBuilt");
    }

    public int burnTime = 20 * 60;

    @Override
    public void updateEntity() {
        if (isFired) {
            timePassed++;
            if (this.blockMetadata == 0) {
                isFired = false;
                this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
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
                    setInventorySlotContents(0, Utils.getModItem("misc", "item", "ash", i2));
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

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
