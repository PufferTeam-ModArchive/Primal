package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.recipes.PitKilnRecipe;

public class TileEntityPitKiln extends TileEntityInventory {

    public int timePassed;
    int timeToSmelt = 20 * 30;

    public TileEntityPitKiln() {
        super(12);
    }

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

    @Override
    public void updateEntity() {
        Block blockAbove = this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        if (blockAbove.getMaterial() == Material.fire) {
            isFired = true;
        }
        if (this.blockMetadata < 8) {
            isFired = false;
            markDirty();
        }
        if (isFired) {
            if (blockAbove.getMaterial() == Material.air || blockAbove.getMaterial() == Material.fire) {
                this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, Blocks.fire);
            } else {
                isFired = false;
                markDirty();
                timePassed = 0;
            }
            TileEntity te = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord + 1);
            if (te instanceof TileEntityPitKiln tef) {
                if (!tef.isFired) {
                    tef.isFired = true;
                    tef.markDirty();
                }
            }
            TileEntity te2 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord + 1);
            if (te2 instanceof TileEntityPitKiln tef) {
                if (!tef.isFired) {
                    tef.isFired = true;
                    tef.markDirty();
                }
            }
            TileEntity te3 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord - 1);
            if (te3 instanceof TileEntityPitKiln tef) {
                if (!tef.isFired) {
                    tef.isFired = true;
                    tef.markDirty();
                }
            }
            TileEntity te4 = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord - 1);
            if (te4 instanceof TileEntityPitKiln tef) {
                if (!tef.isFired) {
                    tef.isFired = true;
                    tef.markDirty();
                }
            }
            this.timePassed++;
        }
        boolean reset = false;
        if (this.timePassed > timeToSmelt) {
            reset = true;
            for (int i = 0; i < getSizeInventory(); i++) {
                ItemStack input = this.getStackInSlot(i);
                ItemStack output = PitKilnRecipe.getOutput(input);
                if (output == null) continue;
                this.setInventorySlotContents(i, output.copy());
            }
        }
        if (reset) {
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord + 1, this.zCoord);
            this.timePassed = 0;
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 2);
            for (int x = 4; x < this.getSizeInventory(); x++) {
                setInventorySlotContentsUpdate(x);
            }
            this.markDirty();
        }
    }

    @Override
    public Item getLastItem() {
        ItemStack itemstack = getInventoryStack(this.lastSlot);
        if (itemstack == null) {
            return Items.flint;
        }
        return getInventoryStack(this.lastSlot).getItem();
    }

    @Override
    public int getLastItemMeta() {
        ItemStack itemstack = getInventoryStack(this.lastSlot);
        if (itemstack == null) {
            return 0;
        }
        return getInventoryStack(this.lastSlot).getItemDamage();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
