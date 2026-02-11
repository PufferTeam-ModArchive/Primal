package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.utils.FacingUtils;

public class TileEntityWaterwheel extends TileEntityMotion {

    public static boolean restrictBiome = Config.waterwheelRestrictBiome.getDefaultBoolean();
    public static float defaultSpeed = Config.waterwheelDefaultSpeed.getDefaultFloat();

    public boolean isExtension;
    public int baseXCoord;
    public int baseYCoord;
    public int baseZCoord;
    public boolean needsFlowUpdate;
    public float generatedSpeed;

    public TileEntityWaterwheel() {
        this.scheduleFlowUpdate();

        restrictBiome = Config.waterwheelRestrictBiome.getBoolean();
        defaultSpeed = Config.windmillDefaultSpeed.getFloat();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.isExtension = tag.getBoolean("isExtension");
        this.baseXCoord = tag.getInteger("baseX");
        this.baseYCoord = tag.getInteger("baseY");
        this.baseZCoord = tag.getInteger("baseZ");
        this.needsFlowUpdate = tag.getBoolean("needsFlowUpdate");
        this.generatedSpeed = tag.getFloat("generatedSpeed");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setBoolean("isExtension", this.isExtension);
        tag.setInteger("baseX", this.baseXCoord);
        tag.setInteger("baseY", this.baseYCoord);
        tag.setInteger("baseZ", this.baseZCoord);
        tag.setBoolean("needsFlowUpdate", this.needsFlowUpdate);
        tag.setFloat("generatedSpeed", this.generatedSpeed);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);

        this.isExtension = tag.getBoolean("isExtension");
        this.generatedSpeed = tag.getFloat("generatedSpeed");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);

        tag.setBoolean("isExtension", this.isExtension);
        tag.setFloat("generatedSpeed", this.generatedSpeed);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (this.needsFlowUpdate) {
            this.needsFlowUpdate = false;
            float newSpeed = getSpeedFromFlow();
            if (this.generatedSpeed != newSpeed) {
                this.generatedSpeed = newSpeed;
                this.speed = newSpeed;
                this.updateTEState();
                this.scheduleUpdate();
            }
        }
    }

    @Override
    public boolean hasConnection(int side) {
        if (!isExtension) {
            return super.hasConnection(side);
        }
        return false;
    }

    @Override
    public float getGeneratedSpeed() {
        return this.generatedSpeed;
    }

    @Override
    public float getTorque() {
        return 10.0F;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord - 2, yCoord - 2, zCoord - 2, xCoord + 2, yCoord + 2, zCoord + 2);
    }

    public float getSpeedFromFlow() {
        if (restrictBiome && !Utils.isRiverBiome(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) return 0.0F;

        float totalSpeed = 0.0F;
        if (axisMeta == 1) {
            if (hasLiquid(ForgeDirection.WEST)) {
                totalSpeed = totalSpeed + defaultSpeed;
            }
            if (hasLiquid(ForgeDirection.EAST)) {
                totalSpeed = totalSpeed - defaultSpeed;
            }
        }
        if (axisMeta == 2) {
            if (hasLiquid(ForgeDirection.SOUTH)) {
                totalSpeed = totalSpeed + defaultSpeed;
            }
            if (hasLiquid(ForgeDirection.NORTH)) {
                totalSpeed = totalSpeed - defaultSpeed;
            }
        }
        return totalSpeed;
    }

    public boolean hasLiquid(ForgeDirection facing) {
        for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
            if (FacingUtils.getAxis(direction.ordinal()) != axisMeta
                && FacingUtils.getAxis(direction.ordinal()) != FacingUtils.getAxis(facing.ordinal())) {
                Block block = worldObj.getBlock(
                    this.xCoord + (facing.offsetX * 2) + direction.offsetX,
                    this.yCoord + (facing.offsetY * 2) + direction.offsetY,
                    this.zCoord + (facing.offsetZ * 2) + direction.offsetZ);
                if (!(block instanceof BlockLiquid)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void scheduleFlowUpdate() {
        this.needsFlowUpdate = true;
    }
}
