package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;

public class TileEntityLogPile extends TileEntityInventory {

    public int timePassed;
    public boolean isFired;
    public boolean hasConverted;
    public int coalAmount;
    int timeToSmelt = 20 * 30;

    public TileEntityLogPile() {
        super(9);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.timePassed = compound.getInteger("timePassed");
        this.coalAmount = compound.getInteger("coalAmount");
        this.isFired = compound.getBoolean("isFired");
        this.hasConverted = compound.getBoolean("hasConverted");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("timePassed", this.timePassed);
        compound.setInteger("coalAmount", this.coalAmount);
        compound.setBoolean("isFired", this.isFired);
        compound.setBoolean("hasConverted", this.hasConverted);
    }

    @Override
    public int getLayerAmount() {
        return 3;
    }

    @Override
    public void updateEntity() {
        Block blockAbove = this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        if (blockAbove.getMaterial() == Material.fire) {
            isFired = true;
        }
        if (isFired) {
            for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
                TileEntity te = worldObj
                    .getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
                if (te instanceof TileEntityLogPile tef) {
                    tef.isFired = true;
                }
            }
            if (!hasConverted) {
                int logs = getAmountItemInPile() / 2;
                this.coalAmount = Math.min(logs, 8);
                hasConverted = true;
            }
            TileEntity below = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
            if (below instanceof TileEntityLogPile tef) {
                int spaceBelow = 8 - tef.coalAmount;
                int movable = Math.min(this.coalAmount, spaceBelow);

                if (movable > 0) {
                    this.coalAmount -= movable;
                    tef.coalAmount += movable;
                }
            }
            this.timePassed++;
        }
        boolean reset = false;
        if (!worldObj.isRemote) {
            if (this.timePassed > timeToSmelt) {
                this.syncMetaWithAmount();
                for (int i = 0; i < getSizeInventory(); i++) {
                    this.setInventorySlotContentsUpdate(i);
                }
                reset = true;
            }
        }

        if (reset) {
            if (blockAbove.getMaterial() == Material.fire) {
                this.worldObj.setBlockToAir(this.xCoord, this.yCoord + 1, this.zCoord);
            }
            this.timePassed = 0;
            int x = this.xCoord;
            int y = this.yCoord;
            int z = this.zCoord;
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
            if (coalAmount > 0) {
                this.worldObj.setBlock(x, y, z, Registry.charcoal_pile, 0, 4);
                TileEntity te = this.worldObj.getTileEntity(x, y, z);
                if (te instanceof TileEntityCharcoalPile tef) {
                    for (int i = 0; i < this.coalAmount; i++) {
                        tef.setInventorySlotContents(
                            i,
                            Utils.getItem("minecraft:coal:1:1")
                                .copy());
                    }
                    tef.syncMetaWithAmount();
                }
            }
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
