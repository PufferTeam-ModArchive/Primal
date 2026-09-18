package net.pufferlab.primal;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public enum Mods {

    fml(ID.fml, new String[] { "cpw.mods.fml" }),
    forge(ID.forge, new String[] { "net.minecraftforge" }),
    tc(ID.tc, new String[] { "thaumcraft" }),
    rple(ID.rple, new String[] { "com.falsepattern.rple" }),
    forbiddenmagic(ID.forbiddenmagic, new String[] { "fox.spiteful.forbidden" }),
    efr(ID.efr, new String[] { "ganymedes01.etfuturum" }),
    nei(ID.nei, new String[] { "codechicken.nei" }),
    gtnhnei(ID.gtnhnei, new String[] { "codechicken.nei" }, "codechicken.nei.recipe.GuiRecipeTabs"),
    angelica(ID.angelica, new String[] { "com.gtnewhorizon.angelica", "com.gtnewhorizons.angelica" }),
    gtnhlib(ID.gtnhlib, new String[] { "com.gtnewhorizon.gtnhlib", "com.gtnewhorizons.gtnhlib" }),
    bop(ID.bop, new String[] { "biomesoplenty" }),
    exbl(ID.exbl, new String[] { "extrabiomes" }),
    witchinggadgets(ID.witchinggadgets, new String[] { "witchinggadgets" }),
    minetweaker(ID.minetweaker, new String[] { "minetweaker" }),
    applecore(ID.applecore, new String[] { "squeek.applecore" }),
    waila(ID.waila, new String[] { "mcp.mobius.waila" }),
    wdmla(ID.wdmla, new String[] { "com.gtnewhorizons.wdmla" }),
    baubles(ID.baubles, new String[] { "baubles" }),
    hodgepodge(ID.hodgepodge, new String[] { "com.mitchej123.hodgepodge" }),
    dapi(ID.dapi, new String[] { "Reika.DragonAPI" }),
    chrc(ID.chrc, new String[] { "Reika.ChromatiCraft" });

    public static final Mods[] values = values();

    public static class ID {

        public static final String fml = "FML";
        public static final String forge = "Forge";
        public static final String tc = "Thaumcraft";
        public static final String rple = "rple";
        public static final String forbiddenmagic = "ForbiddenMagic";
        public static final String efr = "etfuturum";
        public static final String nei = "NotEnoughItems";
        public static final String gtnhnei = "NotEnoughItems";
        public static final String angelica = "angelica";
        public static final String gtnhlib = "gtnhlib";
        public static final String bop = "BiomesOPlenty";
        public static final String exbl = "ExtrabiomesXL";
        public static final String witchinggadgets = "WitchingGadgets";
        public static final String minetweaker = "MineTweaker3";
        public static final String applecore = "AppleCore";
        public static final String waila = "Waila";
        public static final String wdmla = "wdmla";
        public static final String baubles = "Baubles|Expanded";
        public static final String hodgepodge = "hodgepodge";
        public static final String dapi = "DragonAPI";
        public static final String chrc = "ChromatiCraft";
    }

    public final String MODID;

    private final String[] classNamespace;
    private final String modClass;
    private boolean isLoaded;
    private boolean hasChecked;

    Mods(String modid, String[] classStart) {
        this(modid, classStart, null);
    }

    Mods(String modid, String[] classStart, String classDependency) {
        this.MODID = modid;
        this.classNamespace = classStart;
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

    public static String getModIDFromClass(Class<?> cl) {
        for (Mods mod : values) {
            if (!mod.isLoaded()) continue;
            for (String namespace : mod.classNamespace) {
                if (cl.getName()
                    .startsWith(namespace)) {
                    return mod.MODID;
                }
            }
        }
        return null;
    }
}
