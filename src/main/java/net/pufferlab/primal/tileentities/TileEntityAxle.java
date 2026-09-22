package net.pufferlab.primal.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.HashUtils;
import net.pufferlab.primal.utils.ItemUtils;

public class TileEntityAxle extends TileEntityMotion {

    public static final int flagGearPos = 0;
    public static final int flagGearNeg = 1;
    public static final int flagBracket = 2;
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

        int flags = tag.getInteger("flags");
        readFlag(flags);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        int flags = writeFlag();
        tag.setInteger("flags", flags);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        int flags = tag.getInteger("flags");
        readFlag(flags);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        int flags = writeFlag();
        tag.setInteger("flags", flags);
    }

    public void readFlag(int flags) {
        this.hasGearPos = HashUtils.unpackBool(flags, flagGearPos);
        this.hasGearNeg = HashUtils.unpackBool(flags, flagGearNeg);
        this.hasBracket = HashUtils.unpackBool(flags, flagBracket);
    }

    public int writeFlag() {
        int flags = 0;
        flags = HashUtils.packBool(flags, flagGearPos, this.hasGearPos);
        flags = HashUtils.packBool(flags, flagGearNeg, this.hasGearNeg);
        flags = HashUtils.packBool(flags, flagBracket, this.hasBracket);
        return flags;
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
        return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 1, yCoord + 1, zCoord + 1);
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
