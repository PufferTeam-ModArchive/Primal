package net.pufferlab.primal.client.models;

public class ModelGear extends ModelPrimal {

    public ModelGear() {
        super(64);
        bb_main.addBox(28, 17, -3.0F, 20.0F - 13F, -7.0F, 6, 2, 3, 0.0F);
        bb_main.addBox(28, 17, 3.0F, 20.001F - 13F, -6.0F, 2, 2, 3, 0.0F);
        bb_main.addBox(29, 18, 5.0F, 20.001F - 13F, -5.0F, 1, 2, 2, 0.0F);
        bb_main.addBox(29, 18, 3.0F, 20.001F - 13F, 3.0F, 3, 2, 2, 0.0F);
        bb_main.addBox(30, 19, 3.0F, 20.001F - 13F, 5.0F, 2, 2, 1, 0.0F);
        bb_main.addBox(28, 17, -5.0F, 20.001F - 13F, 3.0F, 2, 2, 3, 0.0F);
        bb_main.addBox(30, 18, -6.0F, 20.001F - 13F, 3.0F, 1, 2, 2, 0.0F);
        bb_main.addBox(28, 18, -6.0F, 20.001F - 13F, -5.0F, 3, 2, 2, 0.0F);
        bb_main.addBox(30, 19, -5.0F, 20.001F - 13F, -6.0F, 2, 2, 1, 0.0F);
        bb_main.addBox(30, 17, -3.0F, 20.0F - 13F, 4.0F, 6, 2, 3, 0.0F);
        bb_main.addBox(4, 14, 4.0F, 20.0F - 13F, -3.0F, 3, 2, 6, 0.0F);
        bb_main.addBox(4, 14, -7.0F, 20.0F - 13F, -3.0F, 3, 2, 6, 0.0F);
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
