package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.recipes.PitKilnRecipe;
import net.pufferlab.primal.world.UpdateTask;

public class TileEntityPitKiln extends TileEntityInventory implements IHeatable, IScheduledTile {

    public static int updateProcess = 1;
    public UpdateTask taskProcess = new UpdateTask(updateProcess);
    public static int slotItem1 = 0;
    public static int slotItem2 = 1;
    public static int slotItem3 = 2;
    public static int slotItem4 = 3;
    public static int slotItemLarge = 4;

    public TileEntityPitKiln() {
        super(13);

    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        UpdateTask.readFromNBT(tag, taskProcess);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        UpdateTask.writeToNBT(tag, taskProcess);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        this.writeToNBT(tag);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.readFromNBT(tag);
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
            }
        }
    }

    public void smeltContent() {
        for (int i = 0; i < getSizeInventory(); i++) {
            ItemStack input = this.getStackInSlot(i);
            ItemStack output = PitKilnRecipe.getOutput(input);
            if (output == null) continue;
            this.setInventorySlotContentsUpdate(i, output.copy());
        }
        this.worldObj.setBlockToAir(this.xCoord, this.yCoord + 1, this.zCoord);
        this.worldObj.setBlockMetadataWithNotify(this.xCoord, this.yCoord, this.zCoord, 0, 2);
        for (int x = 5; x < this.getSizeInventory(); x++) {
            setInventorySlotContentsUpdate(x);
        }
        this.markDirty();
    }

    public void sendContentUpdate() {
        if (!taskProcess.hasSentUpdate()) {
            addSchedule(getSmeltTime(), updateProcess);
        }
    }

    public void spreadFire() {
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
    }

    @Override
    public void onSlotUpdate(int index) {}

    @Override
    public void addSchedule(int inTime, int type) {
        IScheduledTile.super.addSchedule(inTime, type);

        if (type == updateProcess) {
            taskProcess.addUpdate(this.worldObj, inTime);
        }
    }

    @Override
    public void removeSchedule(int type) {
        IScheduledTile.super.removeSchedule(type);

        if (type == updateProcess) {
            taskProcess.removeUpdate(this.worldObj);
        }
    }

    @Override
    public void onSchedule(World world, int x, int y, int z, int type, int id) {
        if (type == updateProcess) {
            taskProcess.onUpdate(this.worldObj);
            smeltContent();
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();

        removeAllSchedule();
    }

    @Override
    public void onCoordChange(int oldX, int oldY, int oldZ) {
        super.onCoordChange(oldX, oldY, oldZ);

        moveAllSchedule(getWorldObj(), oldX, oldY, oldZ);
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean canBeFired() {
        return true;
    }

    @Override
    public void setFired(boolean state) {
        if (this.isFired != state) {
            this.isFired = state;
            sendContentUpdate();
            spreadFire();
            this.updateTEState();
        }
    }

    public int getSmeltTime() {
        return Config.pitKilnSmeltTime.getInt();
    }

    @Override
    public boolean isFired() {
        return this.isFired;
    }

    @Override
    public int getMaxTemperature() {
        return 200;
    }

    @Override
    public int getTemperature() {
        return 0;
    }

    @Override
    public boolean isHeatProvider() {
        return true;
    }
}
