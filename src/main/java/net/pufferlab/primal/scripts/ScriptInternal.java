package net.pufferlab.primal.scripts;

import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.items.ItemBucketCeramicModded;
import net.pufferlab.primal.utils.ItemUtils;

public class ScriptInternal implements IScript {

    public void run() {
        addLocalizations();
        addAutoOreDicts();
    }

    public void addLocalizations() {
        for (int i = 0; i < ItemBucketCeramicModded.fluidsString.length; i++) {
            String fluid = ItemBucketCeramicModded.fluidsString[i];
            Fluid fluidObj = ItemBucketCeramicModded.fluids[i];
            if (fluidObj != null) {
                addLocalization(
                    "item." + Primal.MODID + "." + fluid + "_ceramic_bucket_modded.name",
                    fluidObj.getLocalizedName(new FluidStack(fluidObj, 1000)) + " Ceramic Bucket");
            }
        }
    }

    public void addAutoOreDicts() {
        for (Map.Entry<String, ItemStack> entry : ItemUtils.getOreDictCache()) {
            addOreDict(entry.getKey(), entry.getValue());
        }
    }
}
