package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelBox;
import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelLargeVessel extends ModelPrimal {

    public ModelRenderer lid;

    int type;

    public ModelLargeVessel() {
        super(64);
        bb_main.cubeList.add(new ModelBox(bb_main, 18, 20, -4.0F, 1.0F, -5.0F, 8, 9, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 18, 20, -4.0F, 1.0F, 4.0F, 8, 9, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 20, 4.0F, 1.0F, -4.0F, 1, 9, 8, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 20, -5.0F, 1.0F, -4.0F, 1, 9, 8, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 0, 11, -4.0F, 0.0F, -4.0F, 8, 1, 8, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 18, 30, 3.0F, 10.0F, -4.0F, 1, 1, 8, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 18, 30, -4.0F, 10.0F, -4.0F, 1, 1, 8, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 30, 5, -3.0F, 10.0F, -4.0F, 6, 1, 1, 0.0F));
        bb_main.cubeList.add(new ModelBox(bb_main, 30, 5, -3.0F, 10.0F, 3.0F, 6, 1, 1, 0.0F));

        lid = new ModelRenderer(this);
        lid.setRotationPoint(0.0F, 0.0F, 0.0F);
        lid.cubeList.add(new ModelBox(lid, 0, 0, -5.0F, 11.0F, -5.0F, 10, 1, 10, 0.0F));
        lid.cubeList.add(new ModelBox(lid, 30, 0, -1.0F, 12.0F, -1.0F, 2, 1, 2, 0.0F));
        bb_main.addChild(lid);
    }

    public ModelLargeVessel setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public String getName() {
        if (type == 1) {
            return "items/clay_large_vessel";
        }
        return "blocks/large_vessel";
    }
}
