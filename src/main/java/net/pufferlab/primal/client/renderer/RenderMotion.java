package net.pufferlab.primal.client.renderer;

import net.pufferlab.primal.client.helper.TickHolder;

public class RenderMotion {

    public static float getInterpolatedRotation(float speed, float offset) {
        float time = TickHolder.getRenderTime();
        float angle = ((time * speed * 3f / 10 + offset) % 360) / 180 * (float) Math.PI;
        return angle;
    }

    public static float getInterpolatedRotation(float speed, float offset, float partialTicks) {
        float time = TickHolder.getRenderTime() + partialTicks;
        float angle = ((time * speed * 3f / 10 + offset) % 360) / 180 * (float) Math.PI;
        return angle;
    }

    public static float getInterpolatedRotationDeg(float speed, float offset) {
        return (float) Math.toDegrees(getInterpolatedRotation(speed, offset));
    }

    public static float getInterpolatedRotationDeg(float speed, float offset, float partialTicks) {
        return (float) Math.toDegrees(getInterpolatedRotation(speed, offset, partialTicks));
    }
}
