package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelBox;

public class ModelForge extends ModelPrimal {

    public ModelForge() {
        super(64);
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -7.0F, 0.0F, 4.0F, 14, 16, 3, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 19, -7.0F, 0.0F, -7.0F, 14, 16, 3, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 34, 0, 4.0F, 0.0F, -4.0F, 3, 16, 8, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 34, 24, -7.0F, 0.0F, -4.0F, 3, 16, 8, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 38, -4.0F, 0.0F, -4.0F, 8, 2, 8, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 38, -4.0F, 6.0F, -4.0F, 8, 2, 8, 0.0F));
    }

    @Override
    public String getName() {
        return "blocks/forge";
    }
}
