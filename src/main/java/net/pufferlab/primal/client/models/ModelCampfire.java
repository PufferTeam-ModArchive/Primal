package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelCampfire extends ModelPrimal {

    public ModelRenderer kindling;
    public ModelRenderer bottom;
    public ModelRenderer rocks;
    public ModelRenderer log1;
    public ModelRenderer log2;
    public ModelRenderer log3;
    public ModelRenderer log4;

    public ModelCampfire() {
        super(64);

        // spotless:off
        kindling = new ModelRenderer(this);
        kindling.setRotationPoint(0.0F, 0.0F, 1.0F);
        kindling.addBox(1, 1, -1.0F, 0.0F - 8F, -3.0F, 2, 1, 1, 0.0F);
        kindling.addBox(1, 1, -1.0F, 0.0F - 8F, 0.0F, 2, 1, 1, 0.0F);
        kindling.addBox(1, 0, 0.0F, 2.0F - 8F, -2.0F, 1, 1, 2, 0.0F);
        kindling.addBox(1, 0, -2.0F, 0.0F - 8F, -2.0F, 4, 2, 2, 0.0F);
        bb_main.addChild(kindling);

        bottom = new ModelRenderer(this);
        bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        bottom.addBox(-16, 40, -8.0F, 0.1F - 8F, -8.0F, 16, 0, 16, 0.0F);
        bb_main.addChild(bottom);

        rocks = new ModelRenderer(this);
        rocks.setRotationPoint(0.0F, 0.0F, 0.0F);
        rocks.addBox(3, 30, 3.0F, -8.0F, -6.0F, 3, 1, 3, 0.0F);
        rocks.addBox(5, 31, 4.0F, -8.0F, 4.0F, 2, 1, 2, 0.0F);
        rocks.addBox(5, 31, 4.0F, -8.0F, 1.0F, 3, 1, 2, 0.0F);
        rocks.addBox(5, 31, -6.0F, -8.0F, 4.0F, 2, 1, 2, 0.0F);
        rocks.addBox(5, 31, -6.0F, -8.0F, -6.0F, 3, 1, 2, 0.0F);
        rocks.addBox(5, 31, -4.0F, -8.0F, 5.0F, 2, 1, 2, 0.0F);
        rocks.addBox(3, 31, 1.0F, -8.0F, 5.0F, 2, 1, 2, 0.0F);
        rocks.addBox(3, 31, 5.0F, -8.0F, -2.0F, 2, 1, 2, 0.0F);
        rocks.addBox(3, 31, -1.0F, -8.0F, -7.0F, 2, 1, 2, 0.0F);
        rocks.addBox(3, 31, -7.0F, -8.0F, -1.0F, 2, 1, 2, 0.0F);
        bb_main.addChild(rocks);


        log1 = new ModelRenderer(this);
        log1.setRotationPoint(-4.0F, 13.0F, 0.0F);
        bb_main.addChild(log1);
        setRotationAngle(log1, 0.7418F, -1.5272F, 3.14159F);
        log1.addBox(9, 8, -1.0F, 0F, -10.5F, 2, 2, 10, 0.0F);

        log3 = new ModelRenderer(this);
        log3.setRotationPoint(-1.0F, 13.0F, 3.0F);
        bb_main.addChild(log3);
        setRotationAngle(log3, 0.7418F, -3.098F, 3.14159F);
        log3.addBox(9, 8, -1.0F, 0.5F, -10.5F, 2, 2, 10, 0.0F);

        log2 = new ModelRenderer(this);
        log2.setRotationPoint(2.0F, 13.0F, 1.0F);
        bb_main.addChild(log2);
        setRotationAngle(log2, 0.7418F, 1.4835F, 3.14159F);
        log2.addBox(9, 8, -1.0F, 0.0F, -10.0F, 2, 2, 10, 0.0F);

        log4 = new ModelRenderer(this);
        log4.setRotationPoint(0.0F, 13.0F, -3.0F);
        bb_main.addChild(log4);
        setRotationAngle(log4, 0.8727F, 0.0182F, 3.14159F);
        log4.addBox(9, 8, -1.0F, 0.0F, -10.0F, 2, 2, 10, 0.0F);

        // spotless:on
    }
}
