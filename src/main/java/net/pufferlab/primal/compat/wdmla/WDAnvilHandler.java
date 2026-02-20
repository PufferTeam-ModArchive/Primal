package net.pufferlab.primal.compat.wdmla;

import static net.pufferlab.primal.tileentities.TileEntityAnvil.*;

import java.util.Collections;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.AnvilRecipe;
import net.pufferlab.primal.tileentities.TileEntityAnvil;
import net.pufferlab.primal.utils.Utils;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;

public class WDAnvilHandler implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdcampfirehandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityAnvil tef) {
            AnvilRecipe recipe = tef.getRecipe();
            if (recipe != null) {
                List<ItemStack> inputs = Collections.singletonList(tef.getInventoryStack(slotInput));
                List<ItemStack> outputs = Collections.singletonList(recipe.output);

                boolean hasMetalLevel = tef.hasMetalLevel();
                boolean isHotEnough = tef.isInputHotEnough();
                IComponent progress;
                if (!hasMetalLevel || !isHotEnough) {
                    progress = CustomThemeHelper.INSTANCE.furnaceLikeProgressFail(inputs, outputs, 0, 10, false);
                } else {
                    progress = ThemeHelper.INSTANCE.furnaceLikeProgress(inputs, outputs, 0, 10, false);
                }
                tooltip.child(progress.tag(resourceLocation));

                if (!hasMetalLevel) {
                    tooltip.child(
                        new TextComponent(Utils.translate("metal." + Primal.MODID + ".forging.wrong_tier.name")));
                } else if (!isHotEnough) {
                    tooltip
                        .child(new TextComponent(Utils.translate("metal." + Primal.MODID + ".forging.too_cold.name")));
                }

            }
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityAnvil tef) {
            tef.writeToNBT(data);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
