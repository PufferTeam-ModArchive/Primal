package net.pufferlab.primal.compat.waila;

import static net.pufferlab.primal.tileentities.TileEntityCampfire.*;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.CampfireRecipe;
import net.pufferlab.primal.tileentities.TileEntityCampfire;

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
                int timeToProcess = smeltTime;
                boolean isFired = tag.getBoolean("isFired");
                if (isFired) {
                    ItemStack outputItem1 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem1));
                    if (outputItem1 != null) {
                        String inputName = Utils.getDisplayName(tef.getInventoryStack(slotItem1));
                        int timePassed = tag.getInteger("timePassedItem1");
                        currenttip.add(Utils.getRecipeTooltip(inputName, timePassed, timeToProcess, "cooked"));
                    }
                    ItemStack outputItem2 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem2));
                    if (outputItem2 != null) {
                        String inputName = Utils.getDisplayName(tef.getInventoryStack(slotItem2));
                        int timePassed = tag.getInteger("timePassedItem2");
                        currenttip.add(Utils.getRecipeTooltip(inputName, timePassed, timeToProcess, "cooked"));
                    }
                    ItemStack outputItem3 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem3));
                    if (outputItem3 != null) {
                        String inputName = Utils.getDisplayName(tef.getInventoryStack(slotItem3));
                        int timePassed = tag.getInteger("timePassedItem3");
                        currenttip.add(Utils.getRecipeTooltip(inputName, timePassed, timeToProcess, "cooked"));
                    }
                    ItemStack outputItem4 = CampfireRecipe.getOutput(tef.getInventoryStack(slotItem4));
                    if (outputItem4 != null) {
                        String inputName = Utils.getDisplayName(tef.getInventoryStack(slotItem4));
                        int timePassed = tag.getInteger("timePassedItem4");
                        currenttip.add(Utils.getRecipeTooltip(inputName, timePassed, timeToProcess, "cooked"));
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
            tag.setInteger("timePassedItem1", tef.timePassedItem1);
            tag.setInteger("timePassedItem2", tef.timePassedItem2);
            tag.setInteger("timePassedItem3", tef.timePassedItem3);
            tag.setInteger("timePassedItem4", tef.timePassedItem4);
        }
        return tag;
    }
}
