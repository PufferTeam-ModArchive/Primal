package net.pufferlab.primal.compat.wdmla;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.IPrimalBlock;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.plugin.vanilla.GrowthRateProvider;

public class WDCropHandler implements IBlockComponentProvider {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdcrophandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        if (accessor.getBlock() instanceof IPrimalBlock block2) {
            float maxGrowth = block2.getMaxMeta();
            float growthValue = (accessor.getMetadata() / maxGrowth);
            tooltip.replaceChildWithTag(
                GrowthRateProvider.INSTANCE.getUid(),
                ThemeHelper.INSTANCE.growthValue(growthValue));
            MovingObjectPosition mop = accessor.getHitResult();
            if (mop != null) {
                int blockX = mop.blockX;
                int blockY = mop.blockY - 1;
                int blockZ = mop.blockZ;
                TileEntity te = accessor.getWorld()
                    .getTileEntity(blockX, blockY, blockZ);
                WDCompat.farmlandHandler.appendNutrientTooltip(tooltip, te);
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
