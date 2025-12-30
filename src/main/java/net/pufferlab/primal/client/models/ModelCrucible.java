package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelBox;

public class ModelCrucible extends ModelPrimal {

    public ModelCrucible() {
        super(32);
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 19, -3.0F, 6.0F, -3.0F, 6, 1, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -3.0F, 0.0F, -3.0F, 1, 6, 6, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, 2.0F, 0.0F, -3.0F, 1, 6, 6, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 12, -2.0F, 0.0F, 2.0F, 4, 6, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 12, -2.0F, 0.0F, -3.0F, 4, 6, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 19, -3.0F, 6.0F, 1.0F, 6, 1, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 10, 14, -3.0F, 6.0F, -1.0F, 2, 1, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 10, 14, 1.0F, 6.0F, -1.0F, 2, 1, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 10, 23, 1.0F, 7.0F, -1.0F, 1, 1, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 10, 23, -2.0F, 7.0F, -1.0F, 1, 1, 2, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 24, -2.0F, 7.0F, -2.0F, 4, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 24, -2.0F, 7.0F, 1.0F, 4, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 8, 0, -2.0F, 0.0F, -2.0F, 4, 1, 4, 0.0F));
    }

    @Override
    public String getName() {
        if (type == 1) {
            return "items/clay_crucible";
        }
        return "blocks/crucible";
    }
}
