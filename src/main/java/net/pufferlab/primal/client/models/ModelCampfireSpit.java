package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelBox;
import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelCampfireSpit extends ModelPrimal {

    public ModelRenderer cube_r1;
    public ModelRenderer cube_r2;

    public ModelCampfireSpit(int i) {
        super(64);
        if (i == 0) {
            bb_main.cubeList.add(new ModelBox(bb_main, 0, 4, -10.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F));
            bb_main.cubeList.add(new ModelBox(bb_main, 0, 4, 8.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F));
            bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -12.0F, 15.0F, -1.0F, 24, 2, 2, 0.0F));

            cube_r1 = new ModelRenderer(this);
            cube_r1.setRotationPoint(7.0F, 13.0F, -1.0F);
            bb_main.addChild(cube_r1);
            setRotationAngle(cube_r1, 2.7488F, 0.0F, 0.0F);
            cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 4, 1.01F + 7F, -3.0F + 8F, -1.0F - 1.0F, 2, 9, 2, 0.0F));
            cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 4, -17.01F + 7F, -3.0F + 8F, -1.0F - 1.0F, 2, 9, 2, 0.0F));

            cube_r2 = new ModelRenderer(this);
            cube_r2.setRotationPoint(7.0F, 13.0F, 1.0F);
            bb_main.addChild(cube_r2);
            setRotationAngle(cube_r2, -2.7488F, 0.0F, 0.0F);
            cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 4, 1.01F + 7F, -3.0F + 8F, -1.0F + 1.0F, 2, 9, 2, 0.0F));
            cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 4, -17.01F + 7F, -3.0F + 8F, -1.0F + 1.0F, 2, 9, 2, 0.0F));
        }
        if (i == 1) {
            bb_main.rotateAngleY = (float) Math.PI / 2;
            bb_main.cubeList.add(new ModelBox(bb_main, 0, 4, -10.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F));
            bb_main.cubeList.add(new ModelBox(bb_main, 0, 4, 8.0F, 0.0F, -1.0F, 2, 15, 2, 0.0F));
            bb_main.cubeList.add(new ModelBox(bb_main, 0, 0, -12.0F, 15.0F, -1.0F, 24, 2, 2, 0.0F));

            cube_r1 = new ModelRenderer(this);
            cube_r1.setRotationPoint(0.0F, 13.0F, 0.0F);
            bb_main.addChild(cube_r1);
            setRotationAngle(cube_r1, 2.7488F, 0.0F, 0.0F);
            cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 4, 1.01F + 7F, -3.0F + 8F, -1.0F + 1.0F, 2, 9, 2, 0.0F));
            cube_r1.cubeList.add(new ModelBox(cube_r1, 0, 4, -17.01F + 7F, -3.0F + 8F, -1.0F + 1.0F, 2, 9, 2, 0.0F));

            cube_r2 = new ModelRenderer(this);
            cube_r2.setRotationPoint(0.0F, 13.0F, 0.0F);
            bb_main.addChild(cube_r2);
            setRotationAngle(cube_r2, -2.7488F, 0.0F, 0.0F);
            cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 4, 1.01F + 7F, -3.0F + 8F, -1.0F - 1.0F, 2, 9, 2, 0.0F));
            cube_r2.cubeList.add(new ModelBox(cube_r2, 0, 4, -17.01F + 7F, -3.0F + 8F, -1.0F - 1.0F, 2, 9, 2, 0.0F));
        }

    }
}
