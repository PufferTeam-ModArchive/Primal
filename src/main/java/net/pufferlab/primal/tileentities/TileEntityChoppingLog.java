package net.pufferlab.primal.tileentities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.pufferlab.primal.recipes.ChoppingLogRecipe;
import net.pufferlab.primal.utils.BlockUtils;

public class TileEntityChoppingLog extends TileEntityInventory {

    public TileEntityChoppingLog() {
        super(1);
    }

    public int cooldown = 10;
    public int timePassed;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        this.timePassed = compound.getInteger("timePassed");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setInteger("timePassed", this.timePassed);
    }

    public boolean process() {
        ItemStack item = getInventoryStack(0);
        if (ChoppingLogRecipe.hasRecipe(item)) {
            if (timePassed > cooldown) {
                timePassed = 0;
                ItemStack output = ChoppingLogRecipe.getOutput(item);
                if (output != null) {
                    decrStackSize(0, 1);
                    BlockUtils.playSound(this.worldObj, this.xCoord, this.yCoord, this.zCoord, Blocks.log);
                    spawnEntity(
                        this.worldObj,
                        new EntityItem(
                            this.worldObj,
                            this.xCoord + 0.5,
                            this.yCoord + 1,
                            this.zCoord + 0.5,
                            output.copy()));
                    return true;
                }
            }
        }
        return false;

    }

    public void spawnEntity(World world, Entity entityItem) {
        if (!world.isRemote) {
            world.spawnEntityInWorld((Entity) entityItem);
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (timePassed <= cooldown) {
            timePassed++;
        }

    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }
}
