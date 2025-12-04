package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityBarrel extends TileEntityFluidInventory {

    public static final FluidStack waterFluidStack = new FluidStack(FluidRegistry.WATER, 5);
    public boolean isFloorBarrel = false;

    public int timePassed;

    public TileEntityBarrel() {
        super(16000);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.isFloorBarrel = tag.getBoolean("isFloorBarrel");
        this.timePassed = tag.getInteger("timePassed");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("isFloorBarrel", this.isFloorBarrel);
        tag.setInteger("timePassed", this.timePassed);
    }

    public void setFloorBarrel(boolean meta) {
        this.isFloorBarrel = meta;
        this.worldObj.notifyBlockChange(this.xCoord, this.yCoord, this.zCoord, this.blockType);
        this.markDirty();
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setBoolean("isFloorBarrel", this.isFloorBarrel);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.isFloorBarrel = tag.getBoolean("isFloorBarrel");
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRaining() && worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord)) {
            this.timePassed++;
            if (this.timePassed > 20) {
                fill(ForgeDirection.UP, waterFluidStack, true);
                this.timePassed = 0;
            }
        }
    }
}
