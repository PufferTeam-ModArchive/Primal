package net.pufferlab.primal.tileentities;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.BoundingBox;
import net.pufferlab.primal.world.ChunkAllocator;
import net.pufferlab.primal.world.VirtualBlock;

public class TileEntityMoving extends TileEntityPrimal implements IMoving {

    public VirtualBlock virtualBlock = new VirtualBlock();

    public TileEntityMoving() {}

    public int targetX;
    public int targetY;
    public int targetZ;

    public int rotAxis = 0;
    public float rotSpeed = 1.0F;
    public float rotAngle;

    public int scheduledAction = -1;

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

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (!worldObj.isRemote) {

            this.rotAngle = (this.rotAngle + this.rotSpeed) % 360.0F;
            if (this.scheduledAction == 0) {
                this.scheduledAction = -1;
                this.rotAngle = 0.0F;
                this.virtualBlock.copy(
                    worldObj,
                    this.xCoord - 1,
                    this.yCoord,
                    this.zCoord,
                    this.xCoord + 1,
                    this.yCoord + 2,
                    this.zCoord + 1);
            }
            Primal.proxy.packet.sendMovingRotationPacket(this);

            Primal.proxy.packet.sendDebugBB(
                0,
                BoundingBox
                    .getBoundingBox(
                        this.xCoord,
                        this.yCoord,
                        this.zCoord,
                        this.xCoord + 1,
                        this.yCoord + 1,
                        this.zCoord + 1)
                    .setRotation(0, this.rotAngle, 0));

        }

    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        writeToNBTInfo(compound);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        writeToNBTInfo(tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        readFromNBTInfo(compound);

        this.virtualBlock = new VirtualBlock(targetX, targetY, targetZ);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        readFromNBTInfo(tag);

        this.virtualBlock = new VirtualBlock(targetX, targetY, targetZ);
    }

    public void writeToNBTInfo(NBTTagCompound tag) {
        tag.setInteger("xTarget", this.targetX);
        tag.setInteger("yTarget", this.targetY);
        tag.setInteger("zTarget", this.targetZ);

        tag.setInteger("rotAxis", this.rotAxis);
        tag.setFloat("rotAngle", this.rotAngle);
        tag.setFloat("rotSpeed", this.rotSpeed);

        tag.setByte("scheduledAction", (byte) this.scheduledAction);
    }

    public void readFromNBTInfo(NBTTagCompound tag) {
        this.targetX = tag.getInteger("xTarget");
        this.targetY = tag.getInteger("yTarget");
        this.targetZ = tag.getInteger("zTarget");

        this.rotAxis = tag.getInteger("rotAxis");
        this.rotAngle = tag.getFloat("rotAngle");
        this.rotSpeed = tag.getFloat("rotSpeed");

        this.scheduledAction = tag.getByte("scheduledAction");
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return Double.MAX_VALUE;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public int getMovingAxis() {
        return rotAxis;
    }

    @Override
    public float getMovingSpeed() {
        return rotSpeed;
    }

    @Override
    public float getMovingAngle() {
        return rotAngle;
    }

    @Override
    public void setMovingSpeed(float speed) {
        this.rotSpeed = speed;
    }

    @Override
    public void setMovingAngle(float angle) {
        this.rotAngle = angle;
    }

    @Override
    public void setMovingAxis(int axis) {
        this.rotAxis = axis;
    }
}
