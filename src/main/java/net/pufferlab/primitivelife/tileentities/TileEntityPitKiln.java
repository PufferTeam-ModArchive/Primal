package net.pufferlab.primitivelife.tileentities;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.pufferlab.primitivelife.Utils;
import net.pufferlab.primitivelife.recipes.PitKilnRecipes;

public class TileEntityPitKiln extends TileEntityInventory {

    public int timePassed;
    public boolean isFired;
    int timeToSmelt = 20 * 30;
    public int lastSlot;

    public TileEntityPitKiln() {
        super(4);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.timePassed = compound.getInteger("timePassed");
        this.isFired = compound.getBoolean("isFired");
        this.lastSlot = compound.getInteger("lastSlot");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("timePassed", this.timePassed);
        compound.setBoolean("isFired", this.isFired);
        compound.setInteger("lastSlot", this.lastSlot);
    }

    @Override
    public void updateEntity() {
        Block blockAbove = this.worldObj.getBlock(this.xCoord, this.yCoord + 1, this.zCoord);
        if (blockAbove.getMaterial() == Material.fire) {
            isFired = true;
        }
        if (this.blockMetadata < 8) {
            isFired = false;
        }
        if (isFired) {
            if (blockAbove.getMaterial() == Material.air || blockAbove.getMaterial() == Material.fire) {
                this.worldObj.setBlock(this.xCoord, this.yCoord + 1, this.zCoord, Blocks.fire);
            } else {
                isFired = false;
                timePassed = 0;
            }
            TileEntity te = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord + 1);
            if (te instanceof TileEntityPitKiln tef) {
                tef.isFired = true;
            }
            TileEntity te2 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord + 1);
            if (te2 instanceof TileEntityPitKiln tef) {
                tef.isFired = true;
            }
            TileEntity te3 = this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord - 1);
            if (te3 instanceof TileEntityPitKiln tef) {
                tef.isFired = true;
            }
            TileEntity te4 = this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord - 1);
            if (te4 instanceof TileEntityPitKiln tef) {
                tef.isFired = true;
            }
            this.timePassed++;
        }
        boolean reset = false;
        if (this.timePassed > timeToSmelt) {
            reset = true;
            for (int i = 0; i < getSizeInventory(); i++) {
                ItemStack slot = this.getStackInSlot(i);
                String key = Utils.getItemKey(slot);
                Map<String, ItemStack> map = PitKilnRecipes.getRecipeMap();

                if (map.containsKey(key)) {
                    this.setInventorySlotContents(
                        i,
                        map.get(key)
                            .copy());
                }
            }
        }
        if (reset) {
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord + 1, this.zCoord);
            this.timePassed = 0;
            this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 2);
        }
    }

    @Override
    public boolean addInventorySlotContentsUpdate(int index, EntityPlayer player) {
        if (getInventoryStack(index) == null && player.getCurrentEquippedItem() != null) {
            this.lastSlot = index;
        }
        return super.addInventorySlotContentsUpdate(index, player);
    }

    public Item getLastItem() {
        ItemStack itemstack = getInventoryStack(this.lastSlot);
        if (itemstack == null) {
            return Items.flint;
        }
        return getInventoryStack(this.lastSlot).getItem();
    }

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
