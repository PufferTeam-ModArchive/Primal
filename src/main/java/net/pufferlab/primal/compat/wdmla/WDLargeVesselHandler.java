package net.pufferlab.primal.compat.wdmla;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.tileentities.TileEntityLargeVessel;
import net.pufferlab.primal.utils.RecipeUtils;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;

public class WDLargeVesselHandler implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final ResourceLocation resourceLocation = new ResourceLocation(
        Primal.MODID + ":wdlargevesselhandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        NBTTagCompound tag = accessor.getServerData();
        if (te instanceof TileEntityLargeVessel tef) {
            boolean isOpen = tag.getBoolean("isOpen");
            tooltip.child(new TextComponent(RecipeUtils.getStateTooltip(isOpen, "Open", "Sealed")));
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof TileEntityLargeVessel tef) {
            tef.writeToNBT(data);
        }
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
