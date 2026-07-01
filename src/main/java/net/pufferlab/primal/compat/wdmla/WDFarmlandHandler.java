package net.pufferlab.primal.compat.wdmla;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityFarmland;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.HPanelComponent;
import com.gtnewhorizons.wdmla.util.FormatUtil;

public class WDFarmlandHandler implements IBlockComponentProvider {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdfarmlandhandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        appendNutrientTooltip(tooltip, accessor.getTileEntity());
    }

    public void appendNutrientTooltip(ITooltip tooltip, TileEntity te) {
        if (te instanceof TileEntityFarmland tef) {
            boolean K = false;
            boolean N = false;
            boolean P = false;
            char nutrient = tef.getCropNutrient();
            if (nutrient == 'K') {
                K = true;
            } else if (nutrient == 'N') {
                N = true;
            } else if (nutrient == 'P') {
                P = true;
            }
            tooltip.child(moistureValue(tef.moisture, "moisture", false));
            tooltip.child(moistureValue(tef.potassium, "potassium", K));
            tooltip.child(moistureValue(tef.nitrogen, "nitrogen", N));
            tooltip.child(moistureValue(tef.phosphorus, "phosphorus", P));
        }
    }

    public IComponent moistureValue(float moistureValue, String name, boolean line) {
        String suffix = "";
        if (line) {
            suffix = " [-]";
        }

        return value(StatCollector.translateToLocal("hud.primal.info." + name), moistureValue, suffix).tag(getUid());
    }

    public IComponent value(String entry, float value, String debuff) {
        ITooltip co = new HPanelComponent().text(String.format("%s: ", entry))
            .child(ThemeHelper.INSTANCE.info(FormatUtil.PERCENTAGE_STANDARD.format(value)));
        if (debuff != null) {
            co.child(ThemeHelper.INSTANCE.failure(debuff));
        }
        return co;
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
