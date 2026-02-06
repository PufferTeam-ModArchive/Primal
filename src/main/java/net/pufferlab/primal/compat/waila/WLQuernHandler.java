package net.pufferlab.primal.compat.waila;

import static net.pufferlab.primal.tileentities.TileEntityQuern.*;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.tileentities.TileEntityQuern;
import net.pufferlab.primal.utils.RecipeUtils;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

@SuppressWarnings("deprecation")
public class WLQuernHandler implements IWailaDataProvider {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityQuern tef) {
            NBTTagCompound tag = accessor.getNBTData();
            if (tag != null) {
                int timeToProcess = tef.getGrindTime();
                boolean isMoving = tag.getBoolean("isMoving");
                if (isMoving && tef.getInventoryStack(slotInput) != null) {
                    int timePassed = tag.getInteger("timeGround");
                    String name = RecipeUtils.getDisplayName(tef.getInventoryStack(slotInput));
                    currenttip.add(RecipeUtils.getRecipeTooltip(name, timePassed, timeToProcess, "processed"));
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
        if (te instanceof TileEntityQuern tef) {
            tag.setInteger("timeGround", tef.timeGround);
            tag.setBoolean("isMoving", tef.isMoving);
        }
        return tag;
    }
}
