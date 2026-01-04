package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelQuad;

public class ModelForge extends ModelPrimal {

    public ModelForge() {
        super(64);

        // spotless:off
        bb_main.addBox(0, 0, -7.0F, 0.0F, 4.0F, 14, 16, 3, 0.0F);
        bb_main.addBox(0, 19, -7.0F, 0.0F, -7.0F, 14, 16, 3, 0.0F);
        bb_main.addBox(34, 0, 4.0F, 0.0F, -4.0F, 3, 16, 8, 0.0F, new ModelQuad().setSouth(false).setNorth(false));
        bb_main.addBox(34, 24, -7.0F, 0.0F, -4.0F, 3, 16, 8, 0.0F, new ModelQuad().setSouth(false).setNorth(false));
        bb_main.addBox(0, 38, -4.0F, 0.0F, -4.0F, 8, 2, 8, 0.0F, new ModelQuad(true).setDown(true));
        bb_main.addBox(0, 38, -4.0F, 6.0F, -4.0F, 8, 2, 8, 0.0F, new ModelQuad(true).setUp(true));
        // spotless:on
    }

    @Override
    public String getName() {
        return "blocks/forge";
    }
}
