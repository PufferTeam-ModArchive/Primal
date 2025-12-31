package net.pufferlab.primal.items;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;

public class ItemBucketCeramic extends ItemBucketMeta implements IFluidContainerItem {

    public ItemBucketCeramic(String type) {
        super(type);
        this.maxStackSize = 1;
        setBlacklist(Constants.none);
    }

    @Override
    public ItemStack getEmptyBucket() {
        return new ItemStack(this, 1, 0);
    }

    @Override
    public FluidStack getFluid(ItemStack container) {
        int meta = container.getItemDamage();
        Fluid fluid = Registry.fluidsObjects[meta];
        if (fluid == null) {
            return null;
        }
        return new FluidStack(fluid, 1000);
    }

    public int getCapacity(ItemStack container) {
        return 1000;
    }

    public int getFluidMeta(FluidStack fluidStack) {
        return Utils.getIndex(Registry.fluidsObjects, fluidStack.getFluid());
    }

    public int fill(ItemStack container, FluidStack resource, boolean doFill) {
        int meta = container.getItemDamage();
        Fluid fluid = Registry.fluidsObjects[meta];
        if (fluid == null) {
            Fluid inputFluid = resource.getFluid();
            int inputMeta = Utils.getIndex(Registry.fluidsObjects, inputFluid);
            if (doFill) {
                container.setItemDamage(inputMeta);
            }
            return 1000;
        }
        return 0;
    }

    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
        int meta = container.getItemDamage();
        Fluid fluid = Registry.fluidsObjects[meta];
        if (fluid != null) {
            if (doDrain) {
                container.setItemDamage(0);
            }
            return new FluidStack(fluid, 1000);
        }
        return null;
    }
}
