package net.pufferlab.primal.tileentities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Utils;

public class TileEntityFaucet extends TileEntityMetaFacing {

    public boolean isActive = false;

    public int timePassed;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.isActive = tag.getBoolean("isActive");
        this.timePassed = tag.getInteger("timePassed");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("isActive", this.isActive);
        tag.setInteger("timePassed", this.timePassed);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.isActive = tag.getBoolean("isActive");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setBoolean("isActive", this.isActive);
    }

    public void toggleValve() {
        this.isActive = !this.isActive;
        this.updateTE();
    }

    public TileEntity getExtractTile() {
        ForgeDirection dir = Utils.getDirectionFromFacing(this.facingMeta);
        int x = this.xCoord + dir.offsetX;
        int y = this.yCoord + dir.offsetY;
        int z = this.zCoord + dir.offsetZ;
        TileEntity te = this.worldObj.getTileEntity(x, y, z);
        if (te instanceof TileEntityBarrel tef) {
            if (!tef.isFloorBarrel) {
                return null;
            }
        }
        return te;
    }

    public TileEntity getInputTile() {
        TileEntity te = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if (te instanceof TileEntityBarrel tef) {
            if (tef.isFloorBarrel) {
                return null;
            }
        }
        return te;
    }

    public FluidStack getFluidStack() {
        TileEntity adjTE = getExtractTile();
        if (adjTE instanceof TileEntityBarrel tef) {
            return tef.getFluidStack();
        }
        return null;
    }

    public void updateTE() {
        this.markDirty();
        this.worldObj.func_147453_f(this.xCoord, this.yCoord, this.zCoord, this.blockType);
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.worldObj.markBlockRangeForRenderUpdate(
            this.xCoord,
            this.yCoord,
            this.zCoord,
            this.xCoord,
            this.yCoord,
            this.zCoord);
    }

    @Override
    public void updateEntity() {
        if (this.isActive) {
            this.timePassed++;
        }
        if (this.timePassed > 40) {
            TileEntity teAdj = getExtractTile();
            TileEntity teBel = getInputTile();
            if (teAdj instanceof TileEntityBarrel tef) {
                if (teBel instanceof TileEntityBarrel tef2) {
                    FluidStack fluid = tef.drain(Utils.getDirectionFromFacing(this.facingMeta), 100, true);
                    tef2.fill(ForgeDirection.UP, fluid, true);
                    this.updateTE();
                    this.timePassed = 0;
                }
            }
        }

    }
}
