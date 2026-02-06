package net.pufferlab.primal.compat.waila;

import static net.pufferlab.primal.tileentities.TileEntityCampfire.*;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.recipes.CampfireRecipe;
import net.pufferlab.primal.tileentities.TileEntityCampfire;
import net.pufferlab.primal.utils.RecipeUtils;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

@SuppressWarnings("deprecation")
public class WLCampfireHandler implements IWailaDataProvider {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityCampfire tef) {
            NBTTagCompound tag = accessor.getNBTData();
            if (tag != null) {
                boolean isFired = tag.getBoolean("isFired");
                if (isFired) {
                    ItemStack outputItem1 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem1));
                    if (outputItem1 != null) {
                        String inputName = RecipeUtils.getDisplayName(tef.getInventoryStack(slotItem1));
                        long nextUpdate = tef.taskItem1.getNextUpdate();
                        int timeToProcess = tef.taskItem1.getTime();
                        currenttip.add(
                            RecipeUtils
                                .getRecipeTooltip(inputName, tef.getWorld(), nextUpdate, timeToProcess, "cooked"));
                    }
                    ItemStack outputItem2 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem2));
                    if (outputItem2 != null) {
                        String inputName = RecipeUtils.getDisplayName(tef.getInventoryStack(slotItem2));
                        long nextUpdate = tef.taskItem2.getNextUpdate();
                        int timeToProcess = tef.taskItem2.getTime();
                        currenttip.add(
                            RecipeUtils
                                .getRecipeTooltip(inputName, tef.getWorld(), nextUpdate, timeToProcess, "cooked"));
                    }
                    ItemStack outputItem3 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem3));
                    if (outputItem3 != null) {
                        String inputName = RecipeUtils.getDisplayName(tef.getInventoryStack(slotItem3));
                        long nextUpdate = tef.taskItem3.getNextUpdate();
                        int timeToProcess = tef.taskItem3.getTime();
                        currenttip.add(
                            RecipeUtils
                                .getRecipeTooltip(inputName, tef.getWorld(), nextUpdate, timeToProcess, "cooked"));
                    }
                    ItemStack outputItem4 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem4));
                    if (outputItem4 != null) {
                        String inputName = RecipeUtils.getDisplayName(tef.getInventoryStack(slotItem4));
                        long nextUpdate = tef.taskItem4.getNextUpdate();
                        int timeToProcess = tef.taskItem4.getTime();
                        currenttip.add(
                            RecipeUtils
                                .getRecipeTooltip(inputName, tef.getWorld(), nextUpdate, timeToProcess, "cooked"));
                    }
                }
            }
        }

        return currenttip;
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        return tooltip;
    }

    @Override
    public List<String> getWailaTail(ItemStack stack, List<String> tooltip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        return tooltip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
        int y, int z) {
        if (te instanceof TileEntityCampfire tef) {
            tag.setBoolean("isFired", tef.isFired);
        }
        return tag;
    }
}
