package net.pufferlab.primal.mixins.late.wdmla;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.gtnewhorizons.wdmla.WDMla;

@Mixin(WDMla.class)
public class WDMlaFix {

    @Overwrite(remap = false)
    public static boolean isDevEnv() {
        return false;
    }
}
