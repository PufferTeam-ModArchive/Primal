package net.pufferlab.primal.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.ItemUtils;

public class TileEntityAxle extends TileEntityMotion {

    public boolean hasGearPos;
    public boolean hasGearNeg;
    public boolean hasBracket;

    public TileEntityAxle() {}

    public TileEntityAxle(int facingMeta, int axisMeta) {
        super(facingMeta, axisMeta);
    }

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
        ItemStack gear = ItemUtils.getModItem("gear", 1);
        if (BlockUtils.isSidePositive(side)) {
            this.hasGearPos = !this.hasGearPos;
            if (this.hasGearPos) {
                BlockUtils.playSound(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.log);
                player.getHeldItem().stackSize--;
            } else {
                player.inventory.addItemStackToInventory(gear.copy());
            }
        } else {
            this.hasGearNeg = !this.hasGearNeg;
            if (this.hasGearNeg) {
                BlockUtils.playSound(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.log);
                player.getHeldItem().stackSize--;
            } else {
                player.inventory.addItemStackToInventory(gear.copy());
            }
        }
        this.scheduleStrongUpdate();
    }

    public boolean setBracket(int side, EntityPlayer player) {
        ItemStack bracket = ItemUtils.getModItem("bracket", 1);
        int facing = BlockUtils.getFacingMeta(side, this.axisMeta);
        if (facing != 0) {
            this.hasBracket = !this.hasBracket;
            this.facingMeta = facing;
            if (this.hasBracket) {
                BlockUtils.playSound(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.log);
                player.getHeldItem().stackSize--;
            } else {
                player.inventory.addItemStackToInventory(bracket.copy());
            }
            this.updateTEState();
            this.scheduleUpdate();
            return true;
        }
        return false;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public boolean hasGear(int side) {
        if (BlockUtils.isSidePositive(side)) {
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
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.hasGearPos || this.hasGearNeg) {
            return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 1, yCoord + 1, zCoord + 1);
        }
        return super.getRenderBoundingBox();
    }

    @Override
    public float getGeneratedSpeed() {
        return 0;
    }

    @Override
    public boolean hasFacing() {
        return true;
    }
}
