package net.pufferlab.primal.compat.wdmla;

import static net.pufferlab.primal.tileentities.TileEntityBarrel.slotInput;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.BarrelRecipe;
import net.pufferlab.primal.tileentities.TileEntityBarrel;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;

public class WDBarrelHandler implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdbarrelhandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        NBTTagCompound tag = accessor.getServerData();
        if (te instanceof TileEntityBarrel tef) {
            boolean isOpen = tag.getBoolean("isOpen");
            tooltip.child(new TextComponent(Utils.getStateTooltip(isOpen, "Open", "Sealed")));
            boolean canProcess = tag.getBoolean("canProcess");
            long nextUpdate = tag.getLong("nextUpdateProcess");
            if (canProcess) {
                BarrelRecipe recipe = BarrelRecipe.getRecipe(tef.getInventoryStack(slotInput), tef.getFluidStack());
                if (recipe != null) {
                    List<ItemStack> inputs = Arrays.asList(tef.getInventoryStack(slotInput), recipe.inputLiquidBlock);
                    List<ItemStack> outputs = Arrays.asList(recipe.output, recipe.outputLiquidBlock);

                    int timeToProcess = tag.getInteger("timeToProcess");
                    int timeRemaining = Utils.getCurrentProgress(tef.getWorld(), nextUpdate, timeToProcess);
                    IComponent progress = ThemeHelper.INSTANCE
                        .furnaceLikeProgress(inputs, outputs, timeRemaining, timeToProcess, false);
                    tooltip.child(progress.tag(resourceLocation));
                }
            }
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityBarrel tef) {
            tef.writeToNBT(data);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
