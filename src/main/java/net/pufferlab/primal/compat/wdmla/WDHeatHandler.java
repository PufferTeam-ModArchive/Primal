package net.pufferlab.primal.compat.wdmla;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.tileentities.IHeatable;

import com.gtnewhorizons.wdmla.api.accessor.BlockAccessor;
import com.gtnewhorizons.wdmla.api.provider.IBlockComponentProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerDataProvider;
import com.gtnewhorizons.wdmla.api.ui.ITooltip;
import com.gtnewhorizons.wdmla.impl.ui.component.TextComponent;

public class WDHeatHandler implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdheathandler");

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor) {
        int temperature = accessor.getServerData()
            .getInteger("temperature");
        boolean isFired = accessor.getServerData()
            .getBoolean("isFired");
        boolean isHeatProvider = accessor.getServerData()
            .getBoolean("isHeatProvider");

        if (isHeatProvider) {
            tooltip.child(new TextComponent(Utils.getStateTooltip(isFired, "Fired", "Unfired")));
        }
        if (temperature > Config.temperatureCap.getInt()) {
            tooltip.child(new TextComponent(Utils.getTemperatureTooltip(temperature)));
        }
    }

    @Override
    public void appendServerData(NBTTagCompound data, BlockAccessor accessor) {
        TileEntity te = accessor.getTileEntity();
        if (te instanceof IHeatable handler) {
            data.setInteger("temperature", handler.getTemperature());
            data.setBoolean("isFired", handler.isFired());
            data.setBoolean("isHeatProvider", handler.isHeatProvider());
        }
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
