package net.pufferlab.primal.client.models;

public class ModelMold extends ModelPrimal {

    public ModelMold() {
        super(64);

        // spotless:off
        bb_main.addBox(0, 0, -5.0F, 0.0F, -5.0F, 10, 1, 10, 0.0F);
        bb_main.addBox(0, 11, -6.0F, 0.0F, -6.0F, 1, 2, 12, 0.0F);
        bb_main.addBox(0, 11, 5.0F, 0.0F, -6.0F, 1, 2, 12, 0.0F);
        bb_main.addBox(14, 11, -5.0F, 0.0F, 5.0F, 10, 2, 1, 0.0F);
        bb_main.addBox(14, 14, -5.0F, 0.0F, -6.0F, 10, 2, 1, 0.0F);
        // spotless:on

    }
}
