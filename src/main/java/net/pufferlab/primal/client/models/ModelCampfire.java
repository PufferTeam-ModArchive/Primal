package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelBox;
import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelCampfire extends ModelPrimal {

    public ModelRenderer kindling;
    public ModelRenderer log1;
    public ModelRenderer log2;
    public ModelRenderer log3;
    public ModelRenderer log4;
    public ModelRenderer bottom;
    public ModelRenderer rocks;

    public ModelCampfire() {
        super(64);

        kindling = new ModelRenderer(this);
        kindling.setRotationPoint(0.0F, 0.0F, 1.0F);
        kindling.cubeList.add(new ModelBox(kindling, 1, 1, -1.0F, 0.0F, -3.0F, 2, 1, 1, 0.0F));
        kindling.cubeList.add(new ModelBox(kindling, 1, 1, -1.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F));
        kindling.cubeList.add(new ModelBox(kindling, 1, 0, 0.0F, 2.0F, -2.0F, 1, 1, 2, 0.0F));
        kindling.cubeList.add(new ModelBox(kindling, 1, 0, -2.0F, 0.0F, -2.0F, 4, 2, 2, 0.0F));
        bb_main.addChild(kindling);

        log1 = new ModelRenderer(this);
        log1.setRotationPoint(-0.5F, 1.0F, 0.0F);
        setRotationAngle(log1, 0.0F, -0.0491F, 0.7854F);
        log1.cubeList.add(new ModelBox(log1, 17, 16, -5.0F, 3.0F, -1.0F, 10, 2, 2, 0.0F));
        bb_main.addChild(log1);

        log2 = new ModelRenderer(this);
        log2.setRotationPoint(-1.0F, 1.0F, 0.0F);
        setRotationAngle(log2, 0.0F, 0.1491F, -0.7854F);
        log2.cubeList.add(new ModelBox(log2, 17, 16, -4.0F, 5.0F, -1.0F, 10, 2, 2, 0.0F));
        bb_main.addChild(log2);

        log3 = new ModelRenderer(this);
        log3.setRotationPoint(0.0F, 3.0F, 3.0F);
        setRotationAngle(log3, -0.7854F, 0.0F, 0.1309F);
        log3.cubeList.add(new ModelBox(log3, 9, 8, 0.5F, 7.0F, -6.0F, 2, 2, 10, 0.0F));
        bb_main.addChild(log3);

        log4 = new ModelRenderer(this);
        log4.setRotationPoint(0.0F, 3.0F, 2.0F);
        setRotationAngle(log4, 0.7854F, 0.0F, 0.1309F);
        log4.cubeList.add(new ModelBox(log4, 9, 8, 0.5F, 4.0F, -2.0F, 2, 2, 10, 0.0F));
        bb_main.addChild(log4);

        bottom = new ModelRenderer(this);
        bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
        bottom.cubeList.add(new ModelBox(bottom, -16, 40, -8.0F, 0.1F, -8.0F, 16, 0, 16, 0.0F));
        bb_main.addChild(bottom);

        rocks = new ModelRenderer(this);
        rocks.setRotationPoint(0.0F, 0.0F, 0.0F);
        rocks.cubeList.add(new ModelBox(rocks, 3, 30, 3.0F, 0.0F, -6.0F, 3, 1, 3, 0.0F));
        rocks.cubeList.add(new ModelBox(rocks, 5, 31, 4.0F, 0.0F, 4.0F, 2, 1, 2, 0.0F));
        rocks.cubeList.add(new ModelBox(rocks, 5, 31, 4.0F, 0.0F, 1.0F, 3, 1, 2, 0.0F));
        rocks.cubeList.add(new ModelBox(rocks, 5, 31, -6.0F, 0.0F, 4.0F, 2, 1, 2, 0.0F));
        rocks.cubeList.add(new ModelBox(rocks, 5, 31, -6.0F, 0.0F, -6.0F, 3, 1, 2, 0.0F));
        rocks.cubeList.add(new ModelBox(rocks, 5, 31, -4.0F, 0.0F, 5.0F, 2, 1, 2, 0.0F));
        rocks.cubeList.add(new ModelBox(rocks, 3, 31, 1.0F, 0.0F, 5.0F, 2, 1, 2, 0.0F));
        rocks.cubeList.add(new ModelBox(rocks, 3, 31, 5.0F, 0.0F, -2.0F, 2, 1, 2, 0.0F));
        rocks.cubeList.add(new ModelBox(rocks, 3, 31, -1.0F, 0.0F, -7.0F, 2, 1, 2, 0.0F));
        rocks.cubeList.add(new ModelBox(rocks, 3, 31, -7.0F, 0.0F, -1.0F, 2, 1, 2, 0.0F));
        bb_main.addChild(rocks);
    }
}
