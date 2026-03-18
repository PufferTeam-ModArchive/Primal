package net.pufferlab.primal.client.renderer.entities;

import net.pufferlab.primal.client.models.entities.ModelAccessory;
import net.pufferlab.primal.client.models.entities.ModelStrawCoat;
import net.pufferlab.primal.client.models.entities.ModelStrawCoatLegs;

public class ItemStrawCoatRenderer implements IAccessoryRenderer {

    ModelStrawCoat model = new ModelStrawCoat();
    ModelStrawCoatLegs model2 = new ModelStrawCoatLegs();
    ModelAccessory[] models = { model, model2 };

    @Override
    public ModelAccessory[] getModel() {
        return models;
    }
}
