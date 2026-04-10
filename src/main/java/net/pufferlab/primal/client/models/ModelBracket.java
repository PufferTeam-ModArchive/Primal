package net.pufferlab.primal.client.models;

public class ModelBracket extends ModelPrimal {

    public ModelBracket() {
        super(64);
        // spotless:off
        bb_main.addBound(-5.0F, 3.0F, -7.0F, -5.0F + 10, 3.0F + 10, 8);

        bb_main.addBox(0, 17, -5.0F, 3.0F, 6.0F, 10, 10, 2, 0.0F);
        bb_main.addBox(0, 0, -4.0F, 5.0F, -5.0F, 1, 6, 11, 0.0F);
        bb_main.addBox(24, 17, -2.0F, 5.0F, -7.0F, 4, 6, 2, 0.0F);
        bb_main.addBox(7, 0, 3.0F, 5.0F, -5.0F, 1, 6, 11, 0.0F);
        bb_main.addBox(9, 8, -3.0F, 5.0F, 3.0F, 6, 6, 3, 0.0F);
        bb_main.addBox(10, 9, -3.0F, 5.0F, -5.0F, 6, 6, 2, 0.0F);
        // spotless:on
    }

    @Override
    public String getName() {
        return "blocks/bracket";
    }
}
