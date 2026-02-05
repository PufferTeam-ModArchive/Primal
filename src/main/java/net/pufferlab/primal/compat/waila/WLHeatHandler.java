package net.pufferlab.primal.compat.waila;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.IHeatable;
import net.pufferlab.primal.utils.TemperatureUtils;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

@SuppressWarnings("deprecation")
public class WLHeatHandler implements IWailaDataProvider {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof IHeatable tef) {
            NBTTagCompound tag = accessor.getNBTData();
            if (tag != null) {
                boolean isHeatProvider = tef.isHeatProvider();
                if (isHeatProvider) {
                    boolean isFired = tag.getBoolean("isFired");
                    currenttip.add(Utils.getStateTooltip(isFired, "Fired", "Unfired"));
                }
                int temperature = tag.getInteger("temperature");
                if (temperature > Config.temperatureCap.getInt()) {
                    currenttip.add(TemperatureUtils.getTemperatureTooltip(temperature));
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
        if (te instanceof IHeatable tef) {
            tag.setBoolean("isFired", tef.isFired());
            tag.setInteger("temperature", tef.getTemperature());
        }
        return tag;
    }
}
