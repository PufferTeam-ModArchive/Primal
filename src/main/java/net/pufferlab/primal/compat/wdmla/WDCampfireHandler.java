package net.pufferlab.primal.compat.wdmla;

import static net.pufferlab.primal.tileentities.TileEntityCampfire.*;
import static net.pufferlab.primal.tileentities.TileEntityCampfire.slotItem2;
import static net.pufferlab.primal.tileentities.TileEntityCampfire.slotItem3;
import static net.pufferlab.primal.tileentities.TileEntityCampfire.slotItem4;

import java.util.Collections;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.CampfireRecipe;
import net.pufferlab.primal.tileentities.TileEntityCampfire;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.IComponent;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.ThemeHelper;

public class WDCampfireHandler implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdcampfirehandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        NBTTagCompound tag = accessor.getServerData();
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityCampfire tef) {
            boolean isFired = tag.getBoolean("isFired");
            if (isFired) {
                ItemStack outputItem1 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem1));
                if (outputItem1 != null) {
                    ItemStack inputItem = tef.getInventoryStack(slotItem1);
                    ItemStack outputItem = outputItem1.copy();
                    outputItem.stackSize = 0;
                    long nextUpdate = tef.taskItem1.getNextUpdate();
                    int timeToProcess = tef.taskItem1.getTime();
                    int timeRemaining = Utils.getCurrentProgress(tef.getWorld(), nextUpdate, timeToProcess);
                    IComponent progress = ThemeHelper.INSTANCE.furnaceLikeProgress(
                        Collections.singletonList(inputItem),
                        Collections.singletonList(outputItem),
                        timeRemaining,
                        timeToProcess,
                        false);
                    tooltip.child(progress.tag(resourceLocation));
                }
                ItemStack outputItem2 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem2));
                if (outputItem2 != null) {
                    ItemStack inputItem = tef.getInventoryStack(slotItem2);
                    ItemStack outputItem = outputItem2.copy();
                    outputItem.stackSize = 0;
                    long nextUpdate = tef.taskItem2.getNextUpdate();
                    int timeToProcess = tef.taskItem2.getTime();
                    int timeRemaining = Utils.getCurrentProgress(tef.getWorld(), nextUpdate, timeToProcess);
                    IComponent progress = ThemeHelper.INSTANCE.furnaceLikeProgress(
                        Collections.singletonList(inputItem),
                        Collections.singletonList(outputItem),
                        timeRemaining,
                        timeToProcess,
                        false);
                    tooltip.child(progress.tag(resourceLocation));
                }
                ItemStack outputItem3 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem3));
                if (outputItem3 != null) {
                    ItemStack inputItem = tef.getInventoryStack(slotItem3);
                    ItemStack outputItem = outputItem3.copy();
                    outputItem.stackSize = 0;
                    long nextUpdate = tef.taskItem3.getNextUpdate();
                    int timeToProcess = tef.taskItem3.getTime();
                    int timeRemaining = Utils.getCurrentProgress(tef.getWorld(), nextUpdate, timeToProcess);
                    IComponent progress = ThemeHelper.INSTANCE.furnaceLikeProgress(
                        Collections.singletonList(inputItem),
                        Collections.singletonList(outputItem),
                        timeRemaining,
                        timeToProcess,
                        false);
                    tooltip.child(progress.tag(resourceLocation));
                }
                ItemStack outputItem4 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem4));
                if (outputItem4 != null) {
                    ItemStack inputItem = tef.getInventoryStack(slotItem4);
                    ItemStack outputItem = outputItem4.copy();
                    outputItem.stackSize = 0;
                    long nextUpdate = tef.taskItem4.getNextUpdate();
                    int timeToProcess = tef.taskItem4.getTime();
                    int timeRemaining = Utils.getCurrentProgress(tef.getWorld(), nextUpdate, timeToProcess);
                    IComponent progress = ThemeHelper.INSTANCE.furnaceLikeProgress(
                        Collections.singletonList(inputItem),
                        Collections.singletonList(outputItem),
                        timeRemaining,
                        timeToProcess,
                        false);
                    tooltip.child(progress.tag(resourceLocation));
                }
            }
        }

    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityCampfire tef) {
            tef.writeToNBT(data);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
