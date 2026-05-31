package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelPipe extends ModelPrimal {

    public ModelRenderer[] tube;
    public ModelRenderer middle;

    public ModelPipe() {
        super(32);

        facingOffsetY = 0.5f;

        tube = new ModelRenderer[6];
        for (int i = 0; i < 6; i++) {
            tube[i] = new ModelRenderer(this).mirror();
            tube[i].setRotationPoint(0.0F, 0.0F, 0.0F);
            tube[i].isHidden = true;
            if (i <= 3) {
                tube[i].rotateAngleY = (float) Math.toRadians(90 * i);
            } else {
                tube[i].setRotationPoint(0.0F, 16.0F, 0.0F);
                tube[i].offsetY = -1.0F;
                if (i == 4) {
                    tube[i].rotateAngleX = (float) Math.toRadians(90);
                } else {
                    tube[i].rotateAngleX = (float) Math.toRadians(-90);
                }
            }
            tube[i].addBox(16, 15, -3.0F, -10.0F + 7, -8.0F, 1, 6, 2, 0.0F);
            tube[i].addBox(16, 15, 2.0F, -10.0F + 7, -8.0F, 1, 6, 2, 0.0F);
            tube[i].addBox(16, 12, -2.0F, -10.0F + 7, -8.0F, 4, 1, 2, 0.0F);
            tube[i].addBox(16, 12, -2.0F, -5.0F + 7, -8.0F, 4, 1, 2, 0.0F);
            tube[i].addBox(0, 0, -2.0F, -9.0F + 7, -7.0F, 4, 4, 5, 0.0F);
            tube[i].addBound(-3.0F, -10.0F + 7, -8.0F, 6, 6, 5);
            bb_main.addChild(tube[i]);
        }

        middle = new ModelRenderer(this).mirror();
        middle.setRotationPoint(0.0F, 0.0F, 0.0F);
        middle.addBox(0, 9, -2.0F, -9.0F + 7, -2.0F, 4, 4, 4, 0.0F);
        middle.addBound(-2.0F - 1, -9.0F + 7 - 1, -2.0F - 1, 4 + 2, 4 + 2, 4 + 2);
        bb_main.addChild(middle);
    }

    @Override
    public String getName() {
        return "blocks/pipe";
    }
}
