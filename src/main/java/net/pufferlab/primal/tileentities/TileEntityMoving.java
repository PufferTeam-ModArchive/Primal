package net.pufferlab.primal.tileentities;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.world.VirtualBlock;

public class TileEntityMoving extends TileEntityPrimal {
    public Block block;
    public int meta;
    public NBTTagCompound nbt;
    public VirtualBlock virtualBlock = new VirtualBlock();

    public TileEntityMoving() {
    }

    public void syncBlock(World worldIn, int x, int y, int z) {
        Block block = worldIn.getBlock(x, y - 1, z);
        int meta = worldIn.getBlockMetadata(x, y - 1, z);
        TileEntity te = worldIn.getTileEntity(x, y - 1, z);
        NBTTagCompound tagCompound = null;
        if(te != null) {
            tagCompound = new NBTTagCompound();
            te.writeToNBT(tagCompound);
            tagCompound.removeTag("x");
            tagCompound.removeTag("y");
            tagCompound.removeTag("z");
            tagCompound.removeTag("xCached");
            tagCompound.removeTag("yCached");
            tagCompound.removeTag("zCached");
        }
        setBlock(block, meta, tagCompound);
    }

    public void setBlock(Block block, int meta, NBTTagCompound nbt) {
        this.block = block;
        this.meta = meta;
        this.nbt = nbt;
    }

    public void place(World world) {
        virtualBlock.placeBlock(world, block, meta, nbt);
    }

    public void restore(World world) {
        virtualBlock.restoreBlock(world);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("block", Block.getIdFromBlock(block));
        compound.setInteger("meta", meta);
        compound.setTag("nbt", nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        block = Block.getBlockById(compound.getInteger("block"));
        meta = compound.getInteger("meta");
        nbt = compound.getCompoundTag("nbt");
    }
}
