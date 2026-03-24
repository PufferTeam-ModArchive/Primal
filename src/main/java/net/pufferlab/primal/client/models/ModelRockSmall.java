package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelRockSmall extends ModelPrimal {

    public ModelRenderer cube_r1;
    public ModelRenderer cube_r2;
    public ModelRenderer cube_r3;

    public ModelRockSmall() {
        super(16);

        // spotless:off

        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(-7.0F, 0.0F, 6.0F);
        bb_main.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, -1.9199F, 0.0F);
        cube_r1.addBox(0, 0, -6.0F, 0.0F, -6.0F, 4, 2, 4, 0.0F);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(-1.0F, 0.0F, 1.0F);
        bb_main.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 1.9199F, 0.0F);
        cube_r2.addBox(0, 3, -5.0F, 0.0F, -6.0F, 3, 1, 3, 0.0F);

        cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(0.0F, 0.0F, 0.0F);
        bb_main.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.1745F, 0.0F);
        cube_r3.addBox(0, 0, -5.0F, 0.0F, -6.0F, 3, 1, 3, 0.0F);
        // spotless:on

    }
}
