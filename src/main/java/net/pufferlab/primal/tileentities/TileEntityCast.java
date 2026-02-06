package net.pufferlab.primal.tileentities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.recipes.CastingRecipe;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.ItemUtils;

public class TileEntityCast extends TileEntityFluidInventory implements IHeatable {

    public static final int slotCast = 0;
    public static final int slotOutput = 1;
    public static final int slotOutputSmall = 2;
    public int timeHeat;
    public int temperature;
    public int castIndex;

    public TileEntityCast() {
        super(432, 3);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.timeHeat = tag.getInteger("timeHeat");
        this.temperature = tag.getInteger("temperature");
        this.castIndex = tag.getInteger("castIndex");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setInteger("timeHeat", this.timeHeat);
        tag.setInteger("temperature", this.temperature);
        tag.setInteger("castIndex", this.castIndex);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        this.readFromNBT(tag);
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        this.writeToNBT(tag);
    }

    @Override
    public Item getLastItem() {
        return getInventoryStack(slotCast).getItem();
    }

    @Override
    public int getLastItemMeta() {
        return getInventoryStack(slotCast).getItemDamage();
    }

    @Override
    public void updateEntity() {
        timeHeat--;

        if (timeHeat < -3) {
            timeHeat = 0;
            if (this.temperature > 0) {
                this.temperature--;
            }
        }

        if (CastingRecipe.hasRecipe(getInventoryStack(slotCast), getFluidStack())) {
            CastingRecipe recipe = CastingRecipe.getRecipe(getInventoryStack(slotCast), getFluidStack());
            ItemStack output = recipe.output;
            FluidStack input = recipe.input;
            if (HeatUtils.hasImpl(output)) {
                IHeatableItem impl = HeatUtils.getImpl(output);
                int melting = impl.getMeltingTemperature(output);
                impl.updateHeat(output, this.worldObj, -1.0F, this.temperature, 1300);
                if (this.temperature < melting) {
                    if (getInventoryStack(slotOutput) == null && getInventoryStack(slotOutputSmall) == null
                        && drain(ForgeDirection.UP, input, false) != null) {
                        drain(ForgeDirection.UP, input, true);
                        if (ItemUtils.isValidMetal(output)) {
                            setInventorySlotContentsUpdate(slotOutputSmall, output);
                        } else {
                            setInventorySlotContentsUpdate(slotOutput, output);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public boolean canBeFired() {
        return false;
    }

    @Override
    public void setFired(boolean state) {}

    @Override
    public boolean isFired() {
        return false;
    }

    @Override
    public int getMaxTemperature() {
        return 2000;
    }

    @Override
    public int getTemperature() {
        return this.temperature;
    }

    @Override
    public boolean isHeatProvider() {
        return false;
    }
}
