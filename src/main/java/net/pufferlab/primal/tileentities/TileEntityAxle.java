package net.pufferlab.primal.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.Utils;

public class TileEntityAxle extends TileEntityMotion {

    public boolean hasGearPos;
    public boolean hasGearNeg;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.hasGearPos = tag.getBoolean("hasGearPos");
        this.hasGearNeg = tag.getBoolean("hasGearNeg");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("hasGearPos", this.hasGearPos);
        tag.setBoolean("hasGearNeg", this.hasGearNeg);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        this.hasGearPos = tag.getBoolean("hasGearPos");
        this.hasGearNeg = tag.getBoolean("hasGearNeg");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        tag.setBoolean("hasGearPos", this.hasGearPos);
        tag.setBoolean("hasGearNeg", this.hasGearNeg);
    }

    public void setGear(boolean side, EntityPlayer player) {
        if (side) {
            this.hasGearPos = !this.hasGearPos;
            if (this.hasGearPos) {
                player.getHeldItem().stackSize--;
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(this.blockType, 1, 1));
            }
        } else {
            this.hasGearNeg = !this.hasGearNeg;
            if (this.hasGearNeg) {
                player.getHeldItem().stackSize--;
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(this.blockType, 1, 1));
            }
        }
        this.scheduleStrongUpdate();
    }

    @Override
    public boolean hasGear(int side) {
        if (Utils.isSidePositive(side)) {
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
