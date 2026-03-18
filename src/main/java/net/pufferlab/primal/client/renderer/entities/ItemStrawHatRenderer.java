package net.pufferlab.primal.client.renderer.entities;

import net.pufferlab.primal.client.models.entities.ModelAccessory;
import net.pufferlab.primal.client.models.entities.ModelStrawHat;

public class ItemStrawHatRenderer implements IAccessoryRenderer {

    ModelStrawHat model = new ModelStrawHat();
    ModelAccessory[] models = { model };

    @Override
    public ModelAccessory[] getModel() {
        return models;
    }
}
