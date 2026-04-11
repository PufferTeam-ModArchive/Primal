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
import net.pufferlab.primal.world.Tasks;

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
                        long nextUpdate = tef.manager.getTimeScheduled(Tasks.item1);
                        int timeToProcess = tef.manager.getTime(Tasks.item1);
                        currenttip.add(
                            RecipeUtils
                                .getRecipeTooltip(inputName, tef.getWorld(), nextUpdate, timeToProcess, "cooked"));
                    }
                    ItemStack outputItem2 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem2));
                    if (outputItem2 != null) {
                        String inputName = RecipeUtils.getDisplayName(tef.getInventoryStack(slotItem2));
                        long nextUpdate = tef.manager.getTimeScheduled(Tasks.item2);
                        int timeToProcess = tef.manager.getTime(Tasks.item2);
                        currenttip.add(
                            RecipeUtils
                                .getRecipeTooltip(inputName, tef.getWorld(), nextUpdate, timeToProcess, "cooked"));
                    }
                    ItemStack outputItem3 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem3));
                    if (outputItem3 != null) {
                        String inputName = RecipeUtils.getDisplayName(tef.getInventoryStack(slotItem3));
                        long nextUpdate = tef.manager.getTimeScheduled(Tasks.item3);
                        int timeToProcess = tef.manager.getTime(Tasks.item3);
                        currenttip.add(
                            RecipeUtils
                                .getRecipeTooltip(inputName, tef.getWorld(), nextUpdate, timeToProcess, "cooked"));
                    }
                    ItemStack outputItem4 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem4));
                    if (outputItem4 != null) {
                        String inputName = RecipeUtils.getDisplayName(tef.getInventoryStack(slotItem4));
                        long nextUpdate = tef.manager.getTimeScheduled(Tasks.item4);
                        int timeToProcess = tef.manager.getTime(Tasks.item4);
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
