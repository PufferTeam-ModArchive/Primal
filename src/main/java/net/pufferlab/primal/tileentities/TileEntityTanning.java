package net.pufferlab.primal.tileentities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.pufferlab.primal.recipes.TanningRecipe;
import net.pufferlab.primal.utils.SoundType;

public class TileEntityTanning extends TileEntityInventory {

    public TileEntityTanning() {
        super(1);
    }

    public int cooldown = 5;
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
        if (TanningRecipe.hasRecipe(item)) {
            if (timePassed > cooldown) {
                timePassed = 0;
                ItemStack output = TanningRecipe.getOutput(item);
                if (output != null) {
                    decrStackSize(0, 1);
                    playSound(SoundType.soundScraping);
                    setInventorySlotContentsUpdate(0, output);
                    return true;
                }
            }
        }
        return false;

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
