package net.pufferlab.primal.compat.waila;

import static net.pufferlab.primal.tileentities.TileEntityPitKiln.*;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.TileEntityPitKiln;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

@SuppressWarnings("deprecation")
public class WLPitKilnHandler implements IWailaDataProvider {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityPitKiln tef) {
            NBTTagCompound tag = accessor.getNBTData();
            if (tag != null) {
                int timeToProcess = tef.getSmeltTime();
                boolean isFired = tag.getBoolean("isFired");
                if (isFired) {
                    long nextUpdate = tag.getLong("nextUpdateProcess");
                    currenttip
                        .add(Utils.getRecipeTooltip("Pottery", tef.getWorld(), nextUpdate, timeToProcess, "fired"));
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
        if (te instanceof TileEntityPitKiln tef) {
            tag.setBoolean("isFired", tef.isFired);
            tag.setLong("nextUpdateProcess", tef.nextUpdateProcess);
        }
        return tag;
    }
}
