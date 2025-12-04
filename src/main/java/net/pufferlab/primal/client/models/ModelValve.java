package net.pufferlab.primal.client.models;

import net.minecraft.client.model.ModelBox;

public class ModelValve extends ModelPrimal {

    public ModelValve() {
        super(32);
        bb_main.cubeList.add(new ModelBox(bb_main, 2, 2, 2.0F, 14.0F, -2.0F, 1, 1, 4, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 2, 2, -3.0F, 14.0F, -2.0F, 1, 1, 4, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 14, 0, -2.0F, 14.0F, -3.0F, 4, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 14, 0, -2.0F, 14.0F, 2.0F, 4, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 14, 2, -1.0F, 14.0F, -1.0F, 2, 1, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 14, 5, -0.5F, 14.0F, -2.0F, 1, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 14, 5, -0.5F, 14.0F, 1.0F, 1, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 14, 5, -2.0F, 14.0F, -0.5F, 1, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 14, 5, 1.0F, 14.0F, -0.5F, 1, 1, 1, 0.0F));
    }

    @Override
    public String getName() {
        return "blocks/valve";
    }
}
