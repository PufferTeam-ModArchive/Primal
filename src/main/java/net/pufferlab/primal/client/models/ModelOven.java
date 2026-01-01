package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelQuad;

public class ModelOven extends ModelPrimal {

    public ModelOven() {
        super(128);
        bb_main.addBox(
            0,
            17,
            -8.0F,
            1.0F,
            -8.0F,
            2,
            14,
            16,
            0.0F,
            new ModelQuad().setUp(false)
                .setDown(false));
        bb_main.addBox(
            36,
            17,
            6.0F,
            1.0F,
            -8.0F,
            2,
            14,
            16,
            0.0F,
            new ModelQuad().setUp(false)
                .setDown(false));
        bb_main.addBox(
            0,
            64,
            -6.0F,
            1.0F,
            -8.0F,
            12,
            14,
            2,
            0.0F,
            new ModelQuad(true).setSouth(true)
                .setNorth(true));
        bb_main.addBox(0, 47, -8.0F, 0.0F, -8.0F, 16, 1, 16, 0.0F);
        bb_main.addBox(20, 17, -8.0F, 0.0F, 8.0F, 16, 16, 0, 0.0F, new ModelQuad(true).setSouth(true));
        bb_main.addBox(0, 0, -8.0F, 15.0F, -8.0F, 16, 1, 16, 0.0F);
    }

    @Override
    public String getName() {
        return "blocks/oven";
    }
}
