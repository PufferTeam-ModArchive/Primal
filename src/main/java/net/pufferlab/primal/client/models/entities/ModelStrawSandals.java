package net.pufferlab.primal.client.models.entities;

import net.pufferlab.primal.Constants;

public class ModelStrawSandals extends ModelArmor {

    public static final ModelStrawSandals instance = new ModelStrawSandals();

    public ModelStrawSandals() {
        super(Constants.boots);
    }

    @Override
    public String getName() {
        return "models/straw_sandals";
    }
}
