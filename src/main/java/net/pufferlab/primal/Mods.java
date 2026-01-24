package net.pufferlab.primal;

import java.util.Set;

import cpw.mods.fml.common.Loader;

public enum Mods {

    efr("etfuturum"),
    nei("NotEnoughItems"),
    bop("BiomesOPlenty"),
    wg("WitchingGadgets"),
    mt("MineTweaker3"),
    wl("Waila"),
    wd("wdmla"),
    dapi("DragonAPI"),
    chrc("ChromatiCraft");

    public String MODID;

    Mods(String modid) {
        this.MODID = modid;
    }

    public boolean isLoaded() {
        return Loader.isModLoaded(this.MODID);
    }

    public boolean isLoaded(Set<String> loadedMods) {
        return loadedMods.contains(this.MODID);
    }
}
