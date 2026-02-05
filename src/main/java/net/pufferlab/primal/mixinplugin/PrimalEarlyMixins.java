package net.pufferlab.primal.mixinplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.pufferlab.primal.Config;

import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name("PrimalEarlyMixins")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class PrimalEarlyMixins implements IFMLLoadingPlugin, IEarlyMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.primal.early.json";
    }

    List<String> mixins = new ArrayList<>();

    @Override
    public List<String> getMixins(Set<String> loadedCoreMods) {
        boolean isClient = FMLLaunchHandler.side()
            .isClient();
        if (isClient) {
            mixins.add("minecraft.client.MixinEntityDiggingFX");
        }
        mixins.add("minecraft.MixinEntityAIEatGrass");
        mixins.add("minecraft.MixinBlockBush");

        if (Config.destructiveFallingBlocks.getBoolean()) {
            mixins.add("minecraft.MixinBlockFalling");
            mixins.add("minecraft.MixinEntityFallingBlock");
        }
        if (Config.sidewayFallingBlocks.getBoolean()) {
            mixins.add("minecraft.MixinBlockFalling_SideFall");
        }
        return mixins;
    }

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
