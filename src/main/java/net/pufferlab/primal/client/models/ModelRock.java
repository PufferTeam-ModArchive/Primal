package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelBox;

public class ModelRock extends ModelPrimal {

    public int type;

    public ModelRock() {
        super(16);
        bb_main.cubeList.add(new ModelBox(bb_main, 4, 7, -2.0F, 0.0F, -2.0F, 3, 2, 3, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 8, 12, -2.0F, 0.0F, -3.0F, 3, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 6, 1.0F, 0.0F, -2.0F, 2, 1, 3, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 5, -2.0F, 0.0F, 1.0F, 4, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -3.0F, 0.0F, -2.0F, 1, 1, 3, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -1.0F, 1.0F, 1.0F, 2, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 4, 7, -2.0F, 0.0F, -2.0F, 3, 2, 3, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 4, 2, 1.0F, 1.0F, -2.0F, 1, 1, 3, 0.0F));
    }
}
