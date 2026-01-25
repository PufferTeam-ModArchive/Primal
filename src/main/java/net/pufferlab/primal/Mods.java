package net.pufferlab.primal;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public enum Mods {

    tc("Thaumcraft"),
    fm("ForbiddenMagic"),
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

    public Block getModBlock(String name) {
        return GameRegistry.findBlock(this.MODID, name);
    }

    public Item getModItem(String name) {
        return GameRegistry.findItem(this.MODID, name);
    }
}
