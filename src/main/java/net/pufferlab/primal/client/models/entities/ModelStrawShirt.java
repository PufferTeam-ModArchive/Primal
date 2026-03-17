package net.pufferlab.primal.client.models.entities;

import net.pufferlab.primal.Constants;

public class ModelStrawShirt extends ModelArmor {

    public static final ModelStrawShirt instance = new ModelStrawShirt();

    public ModelStrawShirt() {
        super(Constants.chestplate);
    }

    @Override
    public String getName() {
        return "models/straw_shirt";
    }
}
