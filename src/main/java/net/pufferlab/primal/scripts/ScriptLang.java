package net.pufferlab.primal.scripts;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.items.ItemBucketCeramicModded;

public class ScriptLang implements IScript {

    public void run() {
        addLocalizations();
    }

    public void addLocalizations() {
        for (int i = 0; i < ItemBucketCeramicModded.fluidsString.length; i++) {
            String fluid = ItemBucketCeramicModded.fluidsString[i];
            Fluid fluidObj = ItemBucketCeramicModded.fluids[i];
            if (fluidObj != null) {
                addLocalization(
                    "item.primal." + fluid + "_ceramic_bucket_modded.name",
                    fluidObj.getLocalizedName(new FluidStack(fluidObj, 1000)) + " Ceramic Bucket");
            }
        }
    }
}
