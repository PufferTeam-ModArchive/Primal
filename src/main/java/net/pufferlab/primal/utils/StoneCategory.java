package net.pufferlab.primal.utils;

import net.pufferlab.primal.Primal;

public class StoneCategory implements IPrimalType {

    String category;
    String langKey;

    public StoneCategory(String name) {
        this.category = name;
        this.langKey = "stone." + Primal.MODID + "." + name + ".name";
    }

    @Override
    public String getName() {
        return category;
    }

    public String getTranslatedName() {
        return Utils.translate(this.langKey);
    }
}
