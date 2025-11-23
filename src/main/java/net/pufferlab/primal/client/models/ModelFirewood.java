package net.pufferlab.primal.client.models;

import net.minecraft.client.model.ModelBox;

public class ModelFirewood extends ModelPrimal {

    public ModelFirewood() {
        super(64);
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, 0.0F, -1.0F, -8.0F, 5, 5, 16, 0.0F));
    }

    @Override
    public String getName() {
        return "firewood";
    }

}
