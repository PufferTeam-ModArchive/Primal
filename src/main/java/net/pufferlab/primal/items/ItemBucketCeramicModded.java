package net.pufferlab.primal.items;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.ItemUtils;

public class ItemBucketCeramicModded extends ItemBucketCeramic {

    public static String[] fluidsList;
    public static Fluid[] fluids;
    public static Block[] blocks;
    public static String[] fluidsString;
    public static String[] blacklist;

    public ItemBucketCeramicModded(String type) {
        super(type);
        fluidsList = Config.ceramicBucketLiquids.getStringList();
    }

    public void registerModdedLiquids() {
        if (fluidsList != null) {
            if (fluidsList.length > 0) {
                fluids = new Fluid[fluidsList.length];
                blocks = new Block[fluidsList.length];
                blacklist = new String[fluidsList.length];
                fluidsString = new String[fluidsList.length];
                for (int i = 0; i < fluidsList.length; i++) {
                    String fluid = fluidsList[i];
                    FluidStack fluidStack = ItemUtils.getFluid(fluid, 1);
                    if (fluidStack != null) {
                        Fluid fluidObj = fluidStack.getFluid();
                        Block fluidBlock = fluidStack.getFluid()
                            .getBlock();
                        if (fluidObj != null) {
                            fluids[i] = fluidObj;
                        } else {
                            blacklist[i] = fluid;
                        }
                        if (fluidBlock != null) {
                            blocks[i] = fluidBlock;
                        }
                    } else {
                        blacklist[i] = fluid;
                    }
                    fluidsString[i] = fluid;
                }
                this.setBlacklist(blacklist);
                this.setMaterials(fluidsString);
            }
        }
    }

    @Override
    public void registerIcons(IIconRegister register) {
        bucketIcons = new IIcon[4];

        bucketIcons[0] = register.registerIcon(Primal.MODID + ":ceramic_bucket");
        bucketIcons[1] = register.registerIcon(Primal.MODID + ":bucket_mask");
        bucketIcons[2] = register.registerIcon(Primal.MODID + ":ceramic_bucket_hot");
        bucketIcons[3] = register.registerIcon(Primal.MODID + ":bucket_hot_mask");
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return bucketIcons[0];
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int meta, int pass) {
        if (isHotLiquid(meta)) {
            if (pass == 0) {
                return bucketIcons[2];
            }
            if (pass == 1) {
                return bucketIcons[3];
            }
        } else {
            if (pass == 0) {
                return bucketIcons[0];
            }
            if (pass == 1) {
                return bucketIcons[1];
            }
        }

        return bucketIcons[0];
    }

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 2;
    }

    @Override
    public Block[] getFluidBlocks() {
        return blocks;
    }

    @Override
    public Fluid[] getFluidObjects() {
        return fluids;
    }

    @Override
    public boolean isBreakable(ItemStack itemStack) {
        return fluids[itemStack.getItemDamage()].getTemperature() > Config.ceramicBucketLiquidsHotCap.getInt();
    }

    @Override
    public boolean isHotLiquid(int meta) {
        return fluids[meta].getTemperature() > Config.ceramicBucketLiquidsHotCap.getInt();
    }

    @Override
    public ItemStack getEmptyBucket() {
        return new ItemStack(Registry.ceramic_bucket, 1, 0);
    }
}
