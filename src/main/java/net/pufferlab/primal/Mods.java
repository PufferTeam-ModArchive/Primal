package net.pufferlab.primal;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public enum Mods {

    tc("Thaumcraft"),
    rple("rple"),
    fm("ForbiddenMagic"),
    efr("etfuturum"),
    nei("NotEnoughItems"),
    gtnhnei("NotEnoughItems", "codechicken.nei.recipe.GuiRecipeTabs"),
    bop("BiomesOPlenty"),
    exbl("ExtrabiomesXL"),
    wg("WitchingGadgets"),
    mt("MineTweaker3"),
    waila("Waila"),
    wdmla("wdmla"),
    baubles("Baubles|Expanded"),
    hp("hodgepodge"),
    dapi("DragonAPI"),
    chrc("ChromatiCraft");

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
