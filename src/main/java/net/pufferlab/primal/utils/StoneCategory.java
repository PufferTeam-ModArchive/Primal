package net.pufferlab.primal.utils;

import net.pufferlab.primal.Primal;

public class StoneCategory {

    String category;
    String langKey;

    public StoneCategory(String name) {
        this.category = name;
        this.langKey = "stone." + Primal.MODID + "." + name + ".name";
    }

    public String getTranslatedName() {
        return Utils.translate(this.langKey);
    }
}
