package net.pufferlab.primal.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class NoisePreview {

    public static void main(String[] args) throws IOException {
        // generateNoiseImage(1000, 1000);
    }

    public static void generateNoiseImage(int width, int height) throws IOException {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int z = 0; z < height; z++) {
            for (int x = 0; x < width; x++) {

                float value = 100.0F;

                int color = (int) value;

                color = Math.max(0, Math.min(255, color));

                int rgb = (color << 16) | (color << 8) | color;

                if (value < 100.0F) {
                    rgb = HashUtils.getRGB(0, 0, 255);
                }
                image.setRGB(x, z, rgb);
            }
        }
        ImageIO.write(image, "png", new File("terrain.png"));
    }
}
