package net.pufferlab.primal.compat.wdmla;

import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.*;

import com.gtnewhorizons.wdmla.api.IWDMlaClientRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaCommonRegistration;
import com.gtnewhorizons.wdmla.api.IWDMlaPlugin;
import com.gtnewhorizons.wdmla.api.WDMlaPlugin;

@WDMlaPlugin(uid = Primal.MODID)
public class WDCompat implements IWDMlaPlugin {

    public static final WDFluidHandler fluidHandler = new WDFluidHandler();
    public static final WDHeatHandler heatHandler = new WDHeatHandler();
    public static final WDCampfireHandler campfireHandler = new WDCampfireHandler();
    public static final WDQuernHandler quernHandler = new WDQuernHandler();
    public static final WDBarrelHandler barrelHandler = new WDBarrelHandler();
    public static final WDPitKilnHandler pitKilnHandler = new WDPitKilnHandler();

    @Override
    public void register(IWDMlaCommonRegistration registration) {
        registration.registerBlockDataProvider(heatHandler, BlockCrucible.class);
        registration.registerBlockDataProvider(heatHandler, BlockForge.class);
        registration.registerBlockDataProvider(heatHandler, BlockCast.class);
        registration.registerBlockDataProvider(campfireHandler, BlockCampfire.class);
        registration.registerBlockDataProvider(quernHandler, BlockQuern.class);
        registration.registerBlockDataProvider(barrelHandler, BlockBarrel.class);
        registration.registerBlockDataProvider(pitKilnHandler, BlockPitKiln.class);
        registration.registerFluidStorage(fluidHandler, BlockBarrel.class);
        registration.registerFluidStorage(fluidHandler, BlockCrucible.class);
        registration.registerFluidStorage(fluidHandler, BlockCast.class);
    }

    @Override
    public void registerClient(IWDMlaClientRegistration registration) {
        registration.registerBlockComponent(heatHandler, BlockCrucible.class);
        registration.registerBlockComponent(heatHandler, BlockForge.class);
        registration.registerBlockComponent(heatHandler, BlockCast.class);
        registration.registerBlockComponent(campfireHandler, BlockCampfire.class);
        registration.registerBlockComponent(quernHandler, BlockQuern.class);
        registration.registerBlockComponent(barrelHandler, BlockBarrel.class);
        registration.registerBlockComponent(pitKilnHandler, BlockPitKiln.class);
        registration.registerFluidStorageClient(fluidHandler);
    }

}
