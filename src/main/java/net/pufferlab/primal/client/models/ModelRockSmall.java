package net.pufferlab.primal.client.models;

public class ModelRockSmall extends ModelPrimal {

    public int type;

    public ModelRockSmall() {
        super(16);

        // spotless:off
        bb_main.addBox(1, 0, 3.0F, 1.0F, 0.0F, 1, 1, 2, 0.0F);
        bb_main.addBox(3, 0, -3.0F, 0.0F, 3.0F, 2, 1, 2, 0.0F);
        bb_main.addBox(2, 1, -4.0F, 0.0F, -6.0F, 3, 1, 3, 0.0F);
        bb_main.addBox(0, 0, -1.0F, 0.0F, -6.0F, 1, 1, 2, 0.0F);
        bb_main.addBox(1, 1, -3.0F, 0.0F, -7.0F, 2, 1, 1, 0.0F);
        bb_main.addBox(0, 0, -3.0F, 1.0F, -6.0F, 2, 1, 2, 0.0F);
        bb_main.addBox(1, 1, 3.0F, 0.0F, 2.0F, 1, 1, 1, 0.0F);
        bb_main.addBox(3, 1, -2.0F, 1.0F, 3.0F, 1, 1, 1, 0.0F);
        bb_main.addBox(1, 1, -2.0F, 0.0F, 2.0F, 2, 1, 1, 0.0F);
        bb_main.addBox(1, 1, -1.0F, 0.0F, 3.0F, 1, 1, 1, 0.0F);
        bb_main.addBox(0, 0, 2.0F, 0.0F, 0.0F, 3, 1, 2, 0.0F);
        // spotless:on

    }
}
