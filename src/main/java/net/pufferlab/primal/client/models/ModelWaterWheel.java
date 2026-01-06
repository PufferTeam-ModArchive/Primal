package net.pufferlab.primal.client.models;

import net.pufferlab.primal.client.utils.ModelRenderer;

public class ModelWaterWheel extends ModelPrimal {

    public ModelRenderer ring_45;
    public ModelRenderer beam_90;
    public ModelRenderer side_30;

    public ModelWaterWheel() {
        super(64);

        beam_90 = new ModelRenderer(this);
        beam_90.setRotationPoint(0.0F, 0.0F, 0.0F);
        for (int i = 0; i < 4; i++) {
            ModelRenderer beam = new ModelRenderer(this);
            float angle = (float) Math.toRadians(90 * i);
            beam.setRotationPoint(0.0F, 0.0F, 0.0F);
            setRotationAngle(beam, 0.0F, angle, 0.0F);
            beam.addBox(0, 39, -22.0F, 4.0F, -2.0F, 20, 4, 4, 0.0F);
            beam.addBox(0, 39, -22.0F, 4.0F - 12F, -2.0F, 20, 4, 4, 0.0F);
            beam_90.addChild(beam);
        }
        bb_main.addChild(beam_90);

        ring_45 = new ModelRenderer(this);
        ring_45.setRotationPoint(0.0F, 0.0F, 0.0F);
        for (int i = 0; i < 8; i++) {
            ModelRenderer ring = new ModelRenderer(this);
            float angle = (float) Math.toRadians(45 * i);
            ring.setRotationPoint(0.0F, 0.0F, 0.0F);
            setRotationAngle(ring, 0.0F, angle, 0.0F);
            ring.addBox(0, 31, -11.0F, -8.0F - 1F, -25.0F, 22, 4, 4, 0.0F);
            ring.addBox(0, 31, -11.0F, -8.0F + 12F + 1F, -25.0F, 22, 4, 4, 0.0F);
            ring_45.addChild(ring);
        }
        bb_main.addChild(ring_45);

        side_30 = new ModelRenderer(this);
        side_30.setRotationPoint(0.0F, 0.0F, 0.0F);
        for (int i = 0; i < 12; i++) {
            ModelRenderer side = new ModelRenderer(this);
            float angle = (float) Math.toRadians(30 * i);
            side.setRotationPoint(0.0F, 0.0F, 0.0F);
            setRotationAngle(side, 0.0F, angle, 0.0F);
            side.addBox(0, 0, -24.0F, -8.0F, -7.0F, 2, 16, 14, 0.0F);
            side.addBox(33, 0, -33.0F, -8.0F, -2.0F, 9, 16, 2, 0.0F);
            side_30.addChild(side);
        }
        bb_main.addChild(side_30);
    }

    @Override
    public String getName() {
        return "blocks/waterwheel";
    }
}
