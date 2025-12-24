package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelBox;

public class ModelMold extends ModelPrimal {

    public ModelMold() {
        super(64);
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -5.0F, 0.0F, -5.0F, 10, 1, 10, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 11, -6.0F, 0.0F, -6.0F, 1, 2, 12, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 11, 5.0F, 0.0F, -6.0F, 1, 2, 12, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 14, 11, -5.0F, 0.0F, 5.0F, 10, 2, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 14, 14, -5.0F, 0.0F, -6.0F, 10, 2, 1, 0.0F));
    }
}
