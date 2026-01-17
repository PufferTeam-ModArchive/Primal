package net.pufferlab.primal.compat.wdmla;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.PitKilnRecipe;
import net.pufferlab.primal.tileentities.TileEntityPitKiln;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public class WDPitKilnHandler implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdpitkilnhandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        NBTTagCompound tag = accessor.getServerData();
        if (te instanceof TileEntityPitKiln tef) {
            boolean isFired = tef.isFired;
            if (isFired) {
                List<ItemStack> inputs = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    ItemStack stack = tef.getInventoryStack(i);
                    if (stack != null) {
                        inputs.add(stack);
                    }
                }
                List<ItemStack> outputs = new ArrayList<>();
                for (int i = 0; i < inputs.size(); i++) {
                    ItemStack stack = inputs.get(i);
                    if (stack != null) {
                        ItemStack output = PitKilnRecipe.getOutput(stack);
                        if (output != null) {
                            outputs.add(output);
                        }
                    }
                }
                int timeToProcess = tef.getSmeltTime();
                long nextUpdate = tag.getLong("nextUpdateProcess");
                int timeRemaining = Utils.getCurrentProgress(tef.getWorld(), nextUpdate, timeToProcess);
                IComponent progress = ThemeHelper.INSTANCE
                    .furnaceLikeProgress(inputs, outputs, timeRemaining, timeToProcess, false);
                tooltip.child(progress.tag(resourceLocation));
            }
        }

    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityPitKiln tef) {
            tef.writeToNBT(data);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
