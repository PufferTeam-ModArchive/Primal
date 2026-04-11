package net.pufferlab.primal.compat.wdmla;

import static net.pufferlab.primal.tileentities.TileEntityBarrel.slotInput;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.recipes.BarrelRecipe;
import net.pufferlab.primal.tileentities.TileEntityBarrel;
import net.pufferlab.primal.utils.RecipeUtils;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.world.Tasks;

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
            tooltip.child(new TextComponent(RecipeUtils.getStateTooltip(isOpen, "Open", "Sealed")));
            boolean canProcess = tag.getBoolean("canProcess");
            long nextUpdate = tef.manager.getTimeScheduled(Tasks.process);
            if (canProcess) {
                BarrelRecipe recipe = tef.getRecipe();
                if (recipe != null) {
                    List<ItemStack> inputs = Utils.asList(tef.getInventoryStack(slotInput), recipe.inputLiquidBlock);
                    List<ItemStack> outputs = Utils.asList(recipe.output, recipe.outputLiquidBlock);

                    int timeToProcess = tef.manager.getTime(Tasks.process);
                    int timeRemaining = RecipeUtils.getCurrentProgress(tef.getWorld(), nextUpdate, timeToProcess);
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
