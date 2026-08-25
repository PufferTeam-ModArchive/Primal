package net.pufferlab.primal.utils;

import java.awt.*;

import javax.swing.*;

import net.pufferlab.primal.world.terrafirma.gen.noise.NoiseTerrain;

public class SplinePreview extends JPanel {

    public int minX = 10;
    public int minY = 10;

    public int maxX = 750;
    public int maxY = 750;

    public Spline spline;

    public SplinePreview(Spline spline) {
        this.spline = spline;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int difX = maxX - minX;
        int difY = maxY - minY;

        // draw bounds
        g2.drawRect(minX, minY, difX, difY);

        int lastX = minX;
        int lastY = maxY - (int) (difY * spline.sample(0));

        // render curve
        for (int i = 1; i <= 1000; i++) {

            float t = i / 1000.0F;

            float value = spline.sample(t);

            int x = minX + (int) (difX * t);

            // invert Y because Swing starts at the top
            int y = maxY - (int) (difY * value);

            g2.drawLine(lastX, lastY, x, y);

            lastX = x;
            lastY = y;
        }

        // draw control points
        for (int i = 0; i < spline.size; i++) {

            int x = minX + (int) (difX * spline.locations[i]);
            int y = maxY - (int) (difY * spline.points[i]);

            g2.fillOval(x - 3, y - 3, 6, 6);
        }
    }

    public static void main(String[] args) {
        Spline spline = NoiseTerrain.continentalnessSpline;
        render(spline);
    }

    public static void render(Spline spline) {
        JFrame frame = new JFrame();
        JPanel panel = new SplinePreview(spline);
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
