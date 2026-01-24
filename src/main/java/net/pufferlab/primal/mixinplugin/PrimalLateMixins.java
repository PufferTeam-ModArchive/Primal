package net.pufferlab.primal.mixinplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Mods;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

@LateMixin
public class PrimalLateMixins implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return Constants.mixinLate;
    }

    List<String> mixins = new ArrayList<>();

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        if (Mods.dapi.isLoaded(loadedMods)) {
            if (Config.dragonAPIPlantFix.getBoolean()) {
                mixins.add("dragonapi.MixinBlockHelper");
                if (Mods.chrc.isLoaded(loadedMods)) {
                    mixins.add("chromaticraft.MixinBlockDecoFlower");
                }
            }
        }
        return mixins;
    }
}
