package net.pufferlab.primal;

import cpw.mods.fml.common.Loader;

public enum Mods {

    efr("etfuturum"),
    nei("NotEnoughItems"),
    bop("BiomesOPlenty"),
    wg("WitchingGadgets"),
    mt("MineTweaker3"),
    wl("Waila");

    public String MODID;

    Mods(String modid) {
        this.MODID = modid;
    }

    public boolean isLoaded() {
        return Loader.isModLoaded(this.MODID);
    }
}
