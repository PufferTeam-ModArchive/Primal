package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Utils;

public class TileEntityFaucet extends TileEntityMetaFacing {

    public boolean isActive = false;

    public int timePassed;
    public int flowLevel;

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.isActive = tag.getBoolean("isActive");
        this.timePassed = tag.getInteger("timePassed");
        this.flowLevel = tag.getInteger("flowLevel");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("isActive", this.isActive);
        tag.setInteger("timePassed", this.timePassed);
        tag.setInteger("flowLevel", this.flowLevel);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.isActive = tag.getBoolean("isActive");
        this.flowLevel = tag.getInteger("flowLevel");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        tag.setBoolean("isActive", this.isActive);
        tag.setInteger("flowLevel", this.flowLevel);
    }

    public void toggleValve() {
        this.isActive = !this.isActive;
        this.updateTEState();
    }

    public TileEntity getExtractTile() {
        ForgeDirection dir = Utils.getDirectionFromFacing(this.facingMeta);
        int x = this.xCoord + dir.offsetX;
        int y = this.yCoord + dir.offsetY;
        int z = this.zCoord + dir.offsetZ;
        TileEntity te = this.worldObj.getTileEntity(x, y, z);
        if (te instanceof TileEntityBarrel tef) {
            return tef;
        }
        return null;
    }

    public TileEntity getInputTile() {
        TileEntity te = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord);
        if (te instanceof TileEntityBarrel tef) {
            if (tef.isFloorBarrel) {
                return null;
            }
            this.flowLevel = 1;
            return te;
        }
        Block block = this.worldObj.getBlock(this.xCoord, this.yCoord - 1, this.zCoord);
        if (block.getMaterial() == Material.air) {
            TileEntity te2 = this.worldObj.getTileEntity(this.xCoord, this.yCoord - 2, this.zCoord);
            if (te2 instanceof TileEntityBarrel tef2) {
                if (tef2.isFloorBarrel) {
                    return null;
                }
                this.flowLevel = 2;
                return te2;
            }
        }
        return null;
    }

    public FluidStack getFluidStack() {
        TileEntity adjTE = getExtractTile();
        if (adjTE instanceof TileEntityBarrel tef) {
            return tef.getFluidStackRelative();
        }
        return null;
    }

    public FluidStack getFluidStackInput() {
        TileEntity adjTE = getInputTile();
        if (adjTE instanceof TileEntityBarrel tef) {
            return tef.getFluidStack();
        }
        return null;
    }

    @Override
    public void updateEntity() {
        if (this.isActive) {
            this.timePassed++;
        }
        if (this.timePassed > 10) {
            TileEntity teAdj = getExtractTile();
            TileEntity teBel = getInputTile();
            if (teAdj instanceof TileEntityBarrel tef) {
                if (teBel instanceof TileEntityBarrel tef2) {
                    if ((Utils.containsStack(tef.getFluidStackRelative(), tef2.getFluidStackRelative()))
                        || tef2.getFluidStack() == null) {
                        FluidStack fluid = tef.drain(Utils.getDirectionFromFacing(this.facingMeta), 100, true);
                        tef2.fill(ForgeDirection.UP, fluid, true);
                        this.updateTEState();
                        this.timePassed = 0;
                    }
                }
            }
        }

    }
}
