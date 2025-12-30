package net.pufferlab.primal.client.renderer;

import net.pufferlab.primal.Utils;
import net.pufferlab.primal.client.models.ModelPrimal;

import org.lwjgl.opengl.GL11;

public class RenderTemperature {

    public static float getIntensity(float temperature) {
        float minTemp = 200f;
        float maxTemp = 1500f;
        float p = temperature / 200f;
        float t = (temperature - minTemp) / (maxTemp - minTemp) + (0.3F * p);
        return Math.max(0, Math.min(1, t));
    }

    public static int[][] stops = { { 0, 0, 0 }, { 170, 0, 0 }, // cold-ish heat
        { 190, 110, 0 }, // mid
        { 200, 215, 180 } // hot
    };
    public static float[] stopPositions = { 0.0F, 0.5F, 0.8f, 1f };

    public static int getHeatingColor(float temperature) {
        int level = Utils.getHeatingLevel((int) temperature);
        if (level == 0) {
            return Utils.rgbToInt(255, 255, 255);
        }
        float minTemp = 0f;
        float maxTemp = 1200f;
        float t = (temperature - minTemp) / (maxTemp - minTemp);
        t = Math.max(0, Math.min(1, t));

        int idx = 0;
        while (idx < stopPositions.length - 1 && t > stopPositions[idx + 1]) idx++;
        float localT = (t - stopPositions[idx]) / (stopPositions[idx + 1] - stopPositions[idx]);

        int r = (int) (stops[idx][0] + localT * (stops[idx + 1][0] - stops[idx][0]));
        int g = (int) (stops[idx][1] + localT * (stops[idx + 1][1] - stops[idx][1]));
        int b = (int) (stops[idx][2] + localT * (stops[idx + 1][2] - stops[idx][2]));

        return Utils.rgbToInt(r, g, b);
    }

    public static void renderTemperature(ModelPrimal model, int temperature) {
        int color = getHeatingColor(temperature);
        float intensity = getIntensity(temperature);
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        model.render();

        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDepthMask(false);
        GL11.glColor4f(r, g, b, intensity);
        model.setOverlay(true);
        model.render();
        model.setOverlay(false);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);

        GL11.glPopAttrib();

        GL11.glColor4f(1f, 1f, 1f, 1f);
    }
}
