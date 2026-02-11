package net.pufferlab.primal.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.utils.FacingUtils;

public class TileEntityAxle extends TileEntityMotion {

    public boolean hasGearPos;
    public boolean hasGearNeg;
    public boolean hasBracket;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.hasGearPos = tag.getBoolean("hasGearPos");
        this.hasGearNeg = tag.getBoolean("hasGearNeg");
        this.hasBracket = tag.getBoolean("hasBracket");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("hasGearPos", this.hasGearPos);
        tag.setBoolean("hasGearNeg", this.hasGearNeg);
        tag.setBoolean("hasBracket", this.hasBracket);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        this.hasGearPos = tag.getBoolean("hasGearPos");
        this.hasGearNeg = tag.getBoolean("hasGearNeg");
        this.hasBracket = tag.getBoolean("hasBracket");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        tag.setBoolean("hasGearPos", this.hasGearPos);
        tag.setBoolean("hasGearNeg", this.hasGearNeg);
        tag.setBoolean("hasBracket", this.hasBracket);
    }

    public void setGear(int side, EntityPlayer player) {
        if (FacingUtils.isSidePositive(side)) {
            this.hasGearPos = !this.hasGearPos;
            if (this.hasGearPos) {
                Utils.playSound(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.log);
                player.getHeldItem().stackSize--;
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(this.blockType, 1, 1));
            }
        } else {
            this.hasGearNeg = !this.hasGearNeg;
            if (this.hasGearNeg) {
                Utils.playSound(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.log);
                player.getHeldItem().stackSize--;
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(this.blockType, 1, 1));
            }
        }
        this.scheduleStrongUpdate();
    }

    public boolean setBracket(int side, EntityPlayer player) {
        int facing = FacingUtils.getFacingMeta(side, this.axisMeta);
        if (facing != 0) {
            this.hasBracket = !this.hasBracket;
            this.facingMeta = facing;
            if (this.hasBracket) {
                Utils.playSound(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.log);
                player.getHeldItem().stackSize--;
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(this.blockType, 1, 2));
            }
            this.updateTEState();
            this.scheduleUpdate();
            return true;
        }
        return false;
    }

    @Override
    public boolean hasGear(int side) {
        if (FacingUtils.isSidePositive(side)) {
            if (hasGearPos) {
                return true;
            }
        } else {
            if (hasGearNeg) {
                return true;
            }
        }
        return false;
    }

    @Override
    public float getGeneratedSpeed() {
        return 0;
    }
}
