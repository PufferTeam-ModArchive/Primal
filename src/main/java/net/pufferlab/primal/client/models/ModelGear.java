package net.pufferlab.primal.client.models;

public class ModelGear extends ModelPrimal {

    public ModelGear() {
        super(64);
        bb_main.addBox(26, 17, -5.0F, 20.0F - 13F, -7.0F, 10, 2, 3, 0.0F);
        bb_main.addBox(26, 17, -5.0F, 20.0F - 13F, 4.0F, 10, 2, 3, 0.0F);
        bb_main.addBox(0, 10, 4.0F, 20.0F - 13F + 0.001F, -5.0F, 3, 2, 10, 0.0F);
        bb_main.addBox(0, 10, -7.0F, 20.0F - 13F + 0.001F, -5.0F, 3, 2, 10, 0.0F);
        bb_main.addBox(0, 0, -4.0F, 20.0F - 13F, -4.0F, 8, 2, 8, 0.0F);
        bb_main.addBox(0, 0, -6.0F, 22.0F - 13F, -4.0F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, 5.0F, 22.0F - 13F, -4.0F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, 3.0F, 22.0F - 13F, -6.0F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, 3.0F, 22.0F - 13F, 5.0F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, -4.0F, 22.0F - 13F, -6.0F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, -4.0F, 22.0F - 13F, 5.0F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, -0.5F, 22.0F - 13F, -7.0F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, -0.5F, 22.0F - 13F, 6.0F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, -6.0F, 22.0F - 13F, 3.0F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, 5.0F, 22.0F - 13F, 3.0F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, -7.0F, 22.0F - 13F, -0.5F, 1, 2, 1, 0.0F);
        bb_main.addBox(0, 0, 6.0F, 22.0F - 13F, -0.5F, 1, 2, 1, 0.0F);
    }

    @Override
    public String getName() {
        return "blocks/gear";
    }
}
