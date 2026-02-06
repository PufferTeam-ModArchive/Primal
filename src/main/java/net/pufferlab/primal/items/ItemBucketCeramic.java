package net.pufferlab.primal.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.*;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;
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
        if (meta >= getFluidObjects().length) return null;
        Fluid fluid = getFluidObjects()[meta];
        if (fluid == null) {
            return null;
        }
        return new FluidStack(fluid, 1000);
    }

    public int getCapacity(ItemStack container) {
        return 1000;
    }

    public int getFluidMeta(FluidStack fluidStack) {
        return Utils.getIndex(getFluidObjects(), fluidStack.getFluid());
    }

    public int fill(ItemStack container, FluidStack resource, boolean doFill) {
        int meta = container.getItemDamage();
        Fluid fluid = getFluidObjects()[meta];
        if (fluid == null) {
            Fluid inputFluid = resource.getFluid();
            int inputMeta = Utils.getIndex(getFluidObjects(), inputFluid);
            if (doFill) {
                container.setItemDamage(inputMeta);
            }
            return 1000;
        }
        return 0;
    }

    public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain) {
        int meta = container.getItemDamage();
        Fluid fluid = getFluidObjects()[meta];
        if (fluid != null) {
            if (doDrain) {
                container.setItemDamage(0);
            }
            return new FluidStack(fluid, 1000);
        }
        return null;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        bucketIcons = new IIcon[7];

        bucketIcons[0] = register.registerIcon(Primal.MODID + ":ceramic_bucket");
        bucketIcons[1] = register.registerIcon(Primal.MODID + ":bucket_mask");
        bucketIcons[2] = register.registerIcon(Primal.MODID + ":ceramic_bucket_hot");
        bucketIcons[3] = register.registerIcon(Primal.MODID + ":bucket_hot_mask");
        bucketIcons[4] = register.registerIcon(Primal.MODID + ":empty_ceramic_bucket");
        bucketIcons[5] = register.registerIcon(Primal.MODID + ":water_ceramic_bucket");
        bucketIcons[6] = register.registerIcon(Primal.MODID + ":lava_ceramic_bucket");
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
        IIcon icon = super.getIconFromDamageForRenderPass(meta, pass);
        if (meta == 0) {
            icon = bucketIcons[4];
        }
        if (meta == 1) {
            icon = bucketIcons[5];
        }
        if (meta == 2) {
            icon = bucketIcons[6];
        }
        return icon;
    }

    @Override
    public int getRenderPasses(int metadata) {
        if (metadata == 0 || metadata == 1 || metadata == 2) {
            return 1;
        }
        return 2;
    }

    @Override
    public boolean isBreakable(ItemStack itemStack) {
        if (itemStack.getItemDamage() >= Constants.fluidsBreak.length) return false;
        return Constants.fluidsBreak[itemStack.getItemDamage()];
    }
}
