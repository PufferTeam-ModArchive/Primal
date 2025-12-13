package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelBox;
import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelTanningFrame extends ModelPrimal {

    public ModelRenderer cube_r1;
    public ModelRenderer cube_r2;

    public ModelTanningFrame() {
        super(64);
        cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(0.0F, 7.9F, 2.0F);
        bb_main.addChild(cube_r1);
        setRotationAngle(cube_r1, 1.0472F, 0.0F, 0.0F);
        cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 21, -9.0F, -0.9F, 6.01F, 18, 2, 2, 0.0F));
        cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 21, -9.0F, -0.9F, -9.0F, 18, 2, 2, 0.0F));

        cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(0.0F, 8.0F, 2.0F);
        bb_main.addChild(cube_r2);
        setRotationAngle(cube_r2, 1.0472F, 0.0F, 0.0F);
        cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 0, 6.0F, -1.0F, -10.0F, 2, 2, 19, 0.0F));
        cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 0, -8.0F, -1.0F, -10.0F, 2, 2, 19, 0.0F));
    }

    @Override
    public String getName() {
        return "blocks/tanning_frame";
    }
}
