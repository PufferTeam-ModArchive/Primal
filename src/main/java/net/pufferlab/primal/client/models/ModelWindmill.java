package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelWindmill extends ModelPrimal {

    public ModelRenderer beam_90;

    public ModelWindmill(int amount) {
        super(64);

        beam_90 = new ModelRenderer(this);
        beam_90.setRotationPoint(0.0F, 0.0F, 0.0F);
        for (int i = 0; i < 4; i++) {
            ModelRenderer beam = new ModelRenderer(this);
            float angle = (float) Math.toRadians(90 * i);
            beam.setRotationPoint(0.0F, 0.0F, 0.0F);
            setRotationAngle(beam, 0.0F, angle, 0.0F);
            beam.addBox(0, 20, -18.0F, 8.0F - 9F, -1.0F, 16, 2, 2, 0.0F);
            beam.addBox(0, 10, -18.0F, 8.5F - 9F, -10.0F, 12, 1, 9, 0.0F);
            for (int j = 0; j < amount; j++) {
                int position = (16 * j);
                beam.addBox(0, 20, -34.0F - position, 8.0F - 9F, -1.0F, 16, 2, 2, 0.0F);
                beam.addBox(0, 0, -34.0F - position, 8.5F - 9F, -10.0F, 16, 1, 9, 0.0F);
            }
            beam_90.addChild(beam);
        }
        bb_main.addChild(beam_90);

    }

    @Override
    public String getName() {
        return "blocks/windmill";
    }
}
