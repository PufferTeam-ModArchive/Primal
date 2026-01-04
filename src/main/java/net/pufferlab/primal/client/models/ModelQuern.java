package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelQuad;

public class ModelQuern extends ModelPrimal {

    public ModelQuern() {
        super(64);

        // spotless:off
        bb_main.addBox(0, 0, -8.0F, 0.0F, -8.0F, 16, 10, 16, 0.0F);
        bb_main.addBox(24, 43, -7.0F, 10.0F, -8.0F, 14, 1, 1, 0.0F, new ModelQuad().setDown(false).setEast(false).setWest(false));
        bb_main.addBox(24, 43, -7.0F, 10.0F, 7.0F, 14, 1, 1, 0.0F, new ModelQuad().setDown(false).setEast(false).setWest(false));
        bb_main.addBox(24, 26, 7.0F, 10.0F, -8.0F, 1, 1, 16, 0.0F, new ModelQuad().setDown(false));
        bb_main.addBox(24, 26, -8.0F, 10.0F, -8.0F, 1, 1, 16, 0.0F, new ModelQuad().setDown(false));
        bb_main.addBox(0, 26, -5.0F, 10.0F, -5.0F, 10, 1, 10, 0.0F, new ModelQuad().setDown(false));
        // spotless:on

    }

    @Override
    public String getName() {
        return "blocks/quern";
    }
}
