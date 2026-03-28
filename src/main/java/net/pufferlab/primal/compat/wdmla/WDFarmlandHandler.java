package net.pufferlab.primal.compat.wdmla;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityFarmland;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.util.FormatUtil;

public class WDFarmlandHandler implements IBlockComponentProvider {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdfarmlandhandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        if (accessor.getTileEntity() instanceof TileEntityFarmland tef) {
            tooltip.child(moistureValue(tef.moisture, "moisture"));
            tooltip.child(moistureValue(tef.potassium, "potassium"));
            tooltip.child(moistureValue(tef.nitrogen, "nitrogen"));
            tooltip.child(moistureValue(tef.phosphorus, "phosphorus"));
        }
    }

    public IComponent moistureValue(float moistureValue, String name) {
        return ThemeHelper.INSTANCE
            .value(
                StatCollector.translateToLocal("hud.primal.info." + name),
                FormatUtil.PERCENTAGE_STANDARD.format(moistureValue))
            .tag(getUid());
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
