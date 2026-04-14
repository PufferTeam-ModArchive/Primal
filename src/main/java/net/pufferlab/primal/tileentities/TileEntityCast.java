package net.pufferlab.primal.tileentities;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.recipes.CastingRecipe;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.world.HeatInfo;
import net.pufferlab.primal.world.ScheduleManager;
import net.pufferlab.primal.world.Tasks;

public class TileEntityCast extends TileEntityFluidInventory implements IHeatable, IScheduledTile {

    public ScheduleManager manager = new ScheduleManager(Tasks.heat);
    public HeatInfo heat = new HeatInfo(1300);

    public static final int slotCast = 0;
    public static final int slotOutput = 1;
    public static final int slotOutputSmall = 2;
    public int castIndex;

    public TileEntityCast() {
        super(Config.metalIngotValue.getInt() * 3, 3);
    }

    @Override
    public HeatInfo getHeatInfo() {
        return heat;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        heat.readFromNBT(tag);
        this.castIndex = tag.getInteger("castIndex");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        heat.writeToNBT(tag);
        tag.setInteger("castIndex", this.castIndex);
    }

    @Override
    public void readFromNBTPacket(NBTTagCompound tag) {
        super.readFromNBTPacket(tag);
        heat.readFromNBT(tag);
        this.castIndex = tag.getInteger("castIndex");
    }

    @Override
    public void writeToNBTPacket(NBTTagCompound tag) {
        super.writeToNBTPacket(tag);
        heat.writeToNBT(tag);
        tag.setInteger("castIndex", this.castIndex);
    }

    @Override
    public Item getLastItem() {
        return getInventoryStack(slotCast).getItem();
    }

    @Override
    public int getLastItemMeta() {
        return getInventoryStack(slotCast).getItemDamage();
    }

    public void updateMold() {
        if (CastingRecipe.hasRecipe(getInventoryStack(slotCast), getFluidStack())) {
            CastingRecipe recipe = CastingRecipe.getRecipe(getInventoryStack(slotCast), getFluidStack());
            ItemStack output = recipe.output;
            FluidStack input = recipe.input;
            if (HeatUtils.hasImpl(output)) {
                IHeatableItem impl = HeatUtils.getImpl(output);
                int melting = impl.getMeltingTemperature(output);
                impl.updateHeat(output, this.worldObj, -1.0F, this.getTemperature(), 1300);
                if (this.getTemperature() < melting) {
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
    public void onScheduleTask(Tasks task) {
        IScheduledTile.super.onScheduleTask(task);

        if (task == Tasks.heat) {
            updateMold();
            addSchedule(40, Tasks.heat);
        }
    }

    @Override
    public boolean canUpdate() {
        return false;
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
    public boolean isHeatProvider() {
        return false;
    }

    @Override
    public boolean hasFacing() {
        return false;
    }
}
