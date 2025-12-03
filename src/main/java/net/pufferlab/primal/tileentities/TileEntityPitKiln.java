package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primal.recipes.PitKilnRecipe;

public class TileEntityPitKiln extends TileEntityInventory {

    public int timePassed;
    int timeToSmelt = 20 * 30;

    public TileEntityPitKiln() {
        super(13);
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
            setFired(true);
        }
        if (this.blockMetadata < 8) {
            setFired(false);
        }
        if (isFired) {
            if (blockAbove.getMaterial() == Material.air || blockAbove.getMaterial() == Material.fire) {
                this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, Blocks.fire);
            } else {
                setFired(false);
                timePassed = 0;
            }
            TileEntity te = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord + 1);
            if (te instanceof TileEntityPitKiln tef) {
                tef.setFired(true);
            }
            TileEntity te2 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord + 1);
            if (te2 instanceof TileEntityPitKiln tef) {
                tef.setFired(true);
            }
            TileEntity te3 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord - 1);
            if (te3 instanceof TileEntityPitKiln tef) {
                tef.setFired(true);
            }
            TileEntity te4 = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord - 1);
            if (te4 instanceof TileEntityPitKiln tef) {
                tef.setFired(true);
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
            for (int x = 5; x < this.getSizeInventory(); x++) {
                setInventorySlotContentsUpdate(x);
            }
            this.markDirty();
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
