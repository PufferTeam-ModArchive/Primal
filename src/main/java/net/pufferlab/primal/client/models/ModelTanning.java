package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelBox;

public class ModelTanning extends ModelPrimal {

    public ModelTanning() {
        super(64);
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, 6.1F, 0.0F, -4.0F, 2, 15, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -8.1F, 0.0F, -4.0F, 2, 15, 2, 0.0F));
    }

    @Override
    public String getName() {
        return "blocks/tanning_frame";
    }
}
