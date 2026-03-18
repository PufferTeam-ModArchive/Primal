package net.pufferlab.primal.client.renderer.entities;

import net.pufferlab.primal.client.models.entities.ModelAccessory;
import net.pufferlab.primal.client.models.entities.ModelStrawSandals;

public class ItemStrawSandalsRenderer implements IAccessoryRenderer {

    ModelStrawSandals model = new ModelStrawSandals();
    ModelAccessory[] models = { model };

    @Override
    public ModelAccessory[] getModel() {
        return models;
    }
}
