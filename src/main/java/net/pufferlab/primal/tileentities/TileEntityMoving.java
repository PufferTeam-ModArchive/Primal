package net.pufferlab.primal.tileentities;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.pufferlab.primal.world.ChunkAllocator;
import net.pufferlab.primal.world.VirtualBlock;

public class TileEntityMoving extends TileEntityPrimal {

    public VirtualBlock virtualBlock = new VirtualBlock();

    public TileEntityMoving() {}

    public int targetX;
    public int targetY;
    public int targetZ;

    public void init() {
        if (!worldObj.isRemote) {
            virtualBlock = ChunkAllocator.allocateNewVirtualBlock(worldObj);
            targetX = virtualBlock.coordX;
            targetY = virtualBlock.coordY;
            targetZ = virtualBlock.coordZ;

            virtualBlock.placeBlock(getWorld(), Blocks.dirt, 0, null);
            updateTEState();
        }
    }

    public void createContraption() {

    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("xTarget", this.targetX);
        compound.setInteger("yTarget", this.targetY);
        compound.setInteger("zTarget", this.targetZ);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        tag.setInteger("xTarget", this.targetX);
        tag.setInteger("yTarget", this.targetY);
        tag.setInteger("zTarget", this.targetZ);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.targetX = compound.getInteger("xTarget");
        this.targetY = compound.getInteger("yTarget");
        this.targetZ = compound.getInteger("zTarget");

        this.virtualBlock = new VirtualBlock(targetX, targetY, targetZ);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        this.targetX = tag.getInteger("xTarget");
        this.targetY = tag.getInteger("yTarget");
        this.targetZ = tag.getInteger("zTarget");

        this.virtualBlock = new VirtualBlock(targetX, targetY, targetZ);
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return Double.MAX_VALUE;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }
}
