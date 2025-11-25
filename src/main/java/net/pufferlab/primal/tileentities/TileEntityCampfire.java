package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityCampfire extends TileEntityInventory {

    public TileEntityCampfire() {
        super(8);
    }

    public int timePassed;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.timePassed = compound.getInteger("timePassed");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("timePassed", this.timePassed);
    }

    public int burnTime = 20 * 10;

    @Override
    public void updateEntity() {
        if (isFired) {
            timePassed++;
        }
        if (timePassed > burnTime) {
            timePassed = 0;
            int i = findLastFuel();
            setInventorySlotContentsUpdate(i);
            if (i == 0) {
                isFired = false;
            } else {
                this.worldObj
                    .setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, this.blockMetadata - 1, 2);
            }
        }
    }

    public int findLastFuel() {
        for (int i = 0; i < getSizeInventory(); i++) {
            int i2 = (getSizeInventory() - 1) - i;
            ItemStack item = getInventoryStack(i2);
            if (item != null) {
                return i2;
            }
        }
        return 0;
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }
}
