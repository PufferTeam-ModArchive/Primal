package net.pufferlab.primal.mixinplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.pufferlab.primal.Config;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.Primal;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;

@LateMixin
public class PrimalLateMixins implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.primal.late.json";
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
        if (Mods.bop.isLoaded(loadedMods)) {
            if (Config.bopPlantFix.getBoolean()) {
                mixins.add("bop.MixinBlockBOPCoral");
            }
        }
        Primal.LOG.info("Applied Late Mixins");
        return mixins;
    }
}
