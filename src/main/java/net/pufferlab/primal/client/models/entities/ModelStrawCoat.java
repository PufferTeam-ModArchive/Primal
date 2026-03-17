package net.pufferlab.primal.client.models.entities;

import net.pufferlab.primal.Constants;

public class ModelStrawCoat extends ModelArmor {

    public static final ModelStrawCoat instance = new ModelStrawCoat();

    public ModelStrawCoat() {
        super(Constants.chestplate);
    }

    @Override
    public String getName() {
        return "models/straw_coat";
    }
}
