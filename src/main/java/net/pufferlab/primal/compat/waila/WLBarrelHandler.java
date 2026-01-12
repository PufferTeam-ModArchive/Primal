package net.pufferlab.primal.compat.waila;

import static net.pufferlab.primal.tileentities.TileEntityBarrel.*;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.recipes.BarrelRecipe;
import net.pufferlab.primal.tileentities.TileEntityBarrel;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

@SuppressWarnings("deprecation")
public class WLBarrelHandler implements IWailaDataProvider {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityBarrel tef) {
            NBTTagCompound tag = accessor.getNBTData();
            if (tag != null) {
                boolean isOpen = tag.getBoolean("isOpen");
                currenttip.add(Utils.getStateTooltip(isOpen, "Open", "Sealed"));
                boolean canProcess = tag.getBoolean("canProcess");
                long nextUpdate = tag.getLong("nextUpdateProcess");
                if (canProcess) {
                    BarrelRecipe recipe = BarrelRecipe.getRecipe(tef.getInventoryStack(slotInput), tef.getFluidStack());
                    if (recipe != null) {
                        String outputName = Utils.getDisplayName(recipe.output, recipe.outputLiquid);
                        String inputName = Utils.getDisplayName(tef.getInventoryStack(slotInput), tef.getFluidStack());
                        currenttip.add("Making " + outputName);
                        int timeToProcess = tag.getInteger("timeToProcess");
                        if (timeToProcess > 0) {
                            currenttip.add(
                                Utils.getRecipeTooltip(
                                    inputName,
                                    tef.getWorld(),
                                    nextUpdate,
                                    timeToProcess,
                                    "processed"));
                        }
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
        if (te instanceof TileEntityBarrel tef) {
            tag.setBoolean("canProcess", tef.canProcess);
            tag.setLong("nextUpdateProcess", tef.nextUpdateProcess);
            tag.setInteger("timeToProcess", tef.timeToProcess);
            tag.setBoolean("isOpen", tef.isOpen);
        }
        return tag;
    }
}
