package net.pufferlab.primal.mixinplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Primal;

import com.gtnewhorizon.gtnhmixins.IEarlyMixinLoader;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.Name(Primal.MODNAME + "EarlyMixins")
@IFMLLoadingPlugin.MCVersion("1.7.10")
public class PrimalEarlyMixins implements IFMLLoadingPlugin, IEarlyMixinLoader {

    @Override
    public String getMixinConfig() {
        return Constants.mixinEarly;
    }

    List<String> mixins = new ArrayList<>();

    @Override
    public List<String> getMixins(Set<String> loadedCoreMods) {
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
