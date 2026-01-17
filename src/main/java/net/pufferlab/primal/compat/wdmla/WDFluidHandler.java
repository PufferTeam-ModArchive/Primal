package net.pufferlab.primal.compat.wdmla;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.pufferlab.primal.Primal;

import com.gtnewhorizons.wdmla.api.accessor.Accessor;
import com.gtnewhorizons.wdmla.api.provider.IClientExtensionProvider;
import com.gtnewhorizons.wdmla.api.provider.IServerExtensionProvider;
import com.gtnewhorizons.wdmla.api.ui.MessageType;
import com.gtnewhorizons.wdmla.api.view.ClientViewGroup;
import com.gtnewhorizons.wdmla.api.view.FluidView;
import com.gtnewhorizons.wdmla.api.view.ViewGroup;

public class WDFluidHandler
    implements IServerExtensionProvider<FluidView.Data>, IClientExtensionProvider<FluidView.Data, FluidView> {

    public static final ResourceLocation resourceLocation = new ResourceLocation(Primal.MODID + ":wdfluidhandler");

    @Override
    public List<ClientViewGroup<FluidView>> getClientGroups(Accessor accessor, List<ViewGroup<FluidView.Data>> groups) {
        return ClientViewGroup.map(groups, FluidView::readDefault, (group, clientGroup) -> {
            if (group.id != null) {
                clientGroup.title = group.id;
                for (FluidView view : clientGroup.views) {
                    view.hasScale = true;
                }
            }
            clientGroup.messageType = MessageType.SUCCESS;
        });
    }

    @Override
    public List<ViewGroup<FluidView.Data>> getGroups(Accessor accessor) {
        List<ViewGroup<FluidView.Data>> list = new ArrayList<>();
        TileEntity te = (TileEntity) accessor.getTarget();
        if (te instanceof IFluidHandler handler) {
            FluidTankInfo[] info = handler.getTankInfo(ForgeDirection.UP);
            for (FluidTankInfo in : info) {
                list.add(getFluidTank(in));
            }
        }
        return list;
    }

    public ViewGroup<FluidView.Data> getFluidTank(FluidTankInfo info) {
        ViewGroup<FluidView.Data> data = new ViewGroup<>(
            Collections.singletonList(new FluidView.Data(info.fluid, info.capacity)));
        return data;
    }

    @Override
    public ResourceLocation getUid() {
        return resourceLocation;
    }
}
