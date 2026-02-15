package net.pufferlab.primal.mixinplugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.pufferlab.primal.Config;
import net.pufferlab.primal.Mods;

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
                mixins.add("bop.MixinBlockBOPMushroom");
                mixins.add("bop.MixinBlockBOPFlower");
                mixins.add("bop.MixinBlockBOPFlower2");
            }
        }
        if (Mods.exbl.isLoaded(loadedMods)) {
            if (Config.exblPlantFix.getBoolean()) {
                mixins.add("exbl.MixinBlockCustomFlower");
            }
        }
        if (Mods.hp.isLoaded(loadedMods)) {
            if (Config.hodgepodgeBatcherDisabler.getBoolean()) {
                mixins.add("hodgepodge.MixinTileEntityDescriptionBatcher");
            }
        }
        return mixins;
    }
}
