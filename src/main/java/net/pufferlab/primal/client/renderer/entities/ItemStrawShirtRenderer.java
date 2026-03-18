package net.pufferlab.primal.client.renderer.entities;

import net.pufferlab.primal.client.models.entities.ModelAccessory;
import net.pufferlab.primal.client.models.entities.ModelStrawShirt;

public class ItemStrawShirtRenderer implements IAccessoryRenderer {

    ModelStrawShirt model = new ModelStrawShirt();
    ModelAccessory[] models = { model };

    @Override
    public ModelAccessory[] getModel() {
        return models;
    }
}
