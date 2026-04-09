package net.pufferlab.primal;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public enum Mods {

    tc(ID.tc),
    rple(ID.rple),
    fm(ID.fm),
    efr(ID.efr),
    nei(ID.nei),
    gtnhnei(ID.gtnhnei, ID.gtnhnei_class),
    bop(ID.bop),
    exbl(ID.exbl),
    wg(ID.wg),
    mt(ID.mt),
    waila(ID.waila),
    wdmla(ID.wdmla),
    baubles(ID.baubles),
    hp(ID.hp),
    dapi(ID.dapi),
    chrc(ID.chrc);

    public static class ID {

        public static final String tc = "Thaumcraft";
        public static final String rple = "rple";
        public static final String fm = "ForbiddenMagic";
        public static final String efr = "etfuturum";
        public static final String nei = "NotEnoughItems";
        public static final String gtnhnei = "NotEnoughItems";
        public static final String bop = "BiomesOPlenty";
        public static final String exbl = "ExtrabiomesXL";
        public static final String wg = "WitchingGadgets";
        public static final String mt = "MineTweaker3";
        public static final String waila = "Waila";
        public static final String wdmla = "wdmla";
        public static final String baubles = "Baubles|Expanded";
        public static final String hp = "hodgepodge";
        public static final String dapi = "DragonAPI";
        public static final String chrc = "ChromatiCraft";

        // extra for gtnhnei
        public static final String gtnhnei_class = "codechicken.nei.recipe.GuiRecipeTabs";
    }

    public final String MODID;
    private final String modClass;
    private boolean isLoaded;
    private boolean hasChecked;

    Mods(String modid) {
        this.MODID = modid;
        this.modClass = null;
    }

    Mods(String modid, String classDependency) {
        this.MODID = modid;
        this.modClass = classDependency;
    }

    public boolean isLoaded() {
        if (!hasChecked) {
            isLoaded = Loader.isModLoaded(this.MODID);
            if (this.modClass != null) {
                isLoaded = Utils.classExists(this.modClass);
            }
            hasChecked = true;
        }
        return isLoaded;
    }

    public boolean isLoaded(Set<String> loadedMods) {
        if (!hasChecked) {
            isLoaded = loadedMods.contains(this.MODID);
            if (this.modClass != null) {
                isLoaded = Utils.classExists(this.modClass);
            }
            hasChecked = true;
        }
        return isLoaded;
    }

    public Block getModBlock(String name) {
        return GameRegistry.findBlock(this.MODID, name);
    }

    public Item getModItem(String name) {
        return GameRegistry.findItem(this.MODID, name);
    }
}
