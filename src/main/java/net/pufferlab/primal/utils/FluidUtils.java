package net.pufferlab.primal.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

public class FluidUtils {

    public static FluidStack drainFluidFromNBT(NBTTagCompound nbt, int amount) {
        if (nbt.hasKey("Tank")) {
            NBTTagCompound tank = nbt.getCompoundTag("Tank");
            if (!tank.hasKey("Empty")) {
                FluidStack fluid = FluidStack.loadFluidStackFromNBT(tank);
                fluid.amount -= amount;
                fluid.writeToNBT(tank);
                FluidStack fluid2 = fluid.copy();
                fluid2.amount = amount;
                return fluid2;
            }
        }
        return null;
    }

    public static String getFluidInfoFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("Tank")) {
            NBTTagCompound tank = nbt.getCompoundTag("Tank");
            if (!tank.hasKey("Empty")) {
                FluidStack fluid = FluidStack.loadFluidStackFromNBT(tank);
                return fluid.getLocalizedName() + " " + fluid.amount + " mB";
            }
        }
        return null;
    }

    public static String getFluidInfoOutputFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("TankOutput")) {
            NBTTagCompound tank = nbt.getCompoundTag("TankOutput");
            if (!tank.hasKey("Empty")) {
                FluidStack fluid = FluidStack.loadFluidStackFromNBT(tank);
                return fluid.getLocalizedName() + " " + fluid.amount + " mB";
            }
        }
        return null;
    }

    public static FluidStack getFluidFromStack(ItemStack stack) {
        if (stack == null) return null;
        ItemStack stack2 = stack.copy();
        stack2.stackSize = 1;

        if (stack2.getItem() instanceof IFluidContainerItem item) {
            return item.getFluid(stack2);
        }

        return FluidContainerRegistry.getFluidForFilledItem(stack2);
    }

    public static ItemStack getStackFromFluid(ItemStack emptyContainer, FluidStack fluid) {
        if (emptyContainer == null || fluid == null) return null;

        if (emptyContainer.getItem() instanceof IFluidContainerItem) {
            ItemStack filled = emptyContainer.copy();
            IFluidContainerItem item = (IFluidContainerItem) filled.getItem();

            int filledAmount = item.fill(filled, fluid, true);
            if (filledAmount > 0) {
                filled.stackSize = 1;
                return filled;
            }

            return null;
        }

        ItemStack stack = FluidContainerRegistry.fillFluidContainer(fluid, emptyContainer);
        stack.stackSize = 1;
        return stack;
    }

    public static ItemStack getEmptyContainer(ItemStack filled) {
        if (filled == null) return null;

        if (Primal.BOPLoaded) {
            ItemStack bopEmptyBucket = Utils.getItem("BiomesOPlenty:bopBucket:*:1");
            ItemStack emptyBucket = Utils.getItem("minecraft:bucket:0:1");
            if (Utils.equalsStack(filled, bopEmptyBucket)) {
                return emptyBucket.copy();
            }
        }

        if (Primal.WGLoaded) {
            ItemStack capsule = Utils.getItem("WitchingGadgets:item.WG_CrystalFlask:*:1");
            if (Utils.equalsStack(filled, capsule)) {
                capsule.stackTagCompound = null;
                capsule.setItemDamage(0);
                capsule.stackSize = 1;
                return capsule;
            }
        }

        if (filled.getItem() instanceof IFluidContainerItem) {
            ItemStack copy = filled.copy();
            IFluidContainerItem item = (IFluidContainerItem) copy.getItem();
            item.drain(copy, Integer.MAX_VALUE, true);
            copy.stackSize = 1;
            return copy;
        }

        ItemStack drained = FluidContainerRegistry.drainFluidContainer(filled);
        if (drained != null) {
            drained.stackSize = 1;
            return drained;
        }

        return null;
    }

    public static boolean isFluidContainer(ItemStack stack) {
        if (stack == null) return false;

        if (stack.getItem() instanceof IFluidContainerItem) {
            return true;
        }

        if (FluidContainerRegistry.isFilledContainer(stack)) {
            return true;
        }

        if (FluidContainerRegistry.isEmptyContainer(stack)) {
            return true;
        }

        return false;
    }

    public static boolean isEmptyFluidContainer(ItemStack stack) {
        if (stack == null) return false;

        if (stack.getItem() instanceof IFluidContainerItem item) {
            FluidStack fluid = item.getFluid(stack);
            return fluid == null || fluid.amount <= 0;
        }

        return FluidContainerRegistry.isEmptyContainer(stack);
    }
}
