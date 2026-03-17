package net.pufferlab.primal.client.models.entities;

import net.pufferlab.primal.Constants;

public class ModelStrawPants extends ModelArmor {

    public static final ModelStrawPants instance = new ModelStrawPants();

    public ModelStrawPants() {
        super(Constants.leggings);
    }

    @Override
    public String getName() {
        return "models/straw_pants";
    }
}
