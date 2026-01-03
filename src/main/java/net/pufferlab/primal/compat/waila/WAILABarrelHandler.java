package net.pufferlab.primal.compat.waila;

import java.util.List;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.recipes.BarrelRecipe;
import net.pufferlab.primal.tileentities.TileEntityBarrel;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;

@SuppressWarnings("deprecation")
public class WAILABarrelHandler implements IWailaDataProvider {

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
        IWailaConfigHandler config) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityBarrel tef) {
            NBTTagCompound tag = accessor.getNBTData();
            if (tag != null) {
                boolean canProcess = tag.getBoolean("canProcess");
                if (canProcess) {
                    BarrelRecipe recipe = BarrelRecipe.getRecipe(tef.getInventoryStack(0), tef.getFluidStack());
                    if (recipe != null) {
                        String name = "Nothing";
                        ItemStack outputItem = recipe.output;
                        if (outputItem != null) {
                            name = outputItem.getDisplayName();
                        }
                        FluidStack outputLiquid = recipe.outputLiquid;
                        if (outputLiquid != null) {
                            name = outputLiquid.getLocalizedName();
                        }
                        currenttip.add("Making " + name);
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
            tag.setInteger("timePassed", tef.timePassed);
        }
        return tag;
    }
}
