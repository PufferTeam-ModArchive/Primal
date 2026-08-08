package net.pufferlab.primal.world.terrafirma.gen.noise;

import net.pufferlab.primal.utils.Spline;
import net.pufferlab.primal.utils.SplineBezier;

public class NoiseSplines {

    public static final Spline continentalnessSpline;
    public static final Spline erosionSpline;
    public static final Spline peaksvalleysSpline;
    public static final Spline hillSpline;

    public static final Spline forestSpline;

    static {
        hillSpline = new Spline();
        hillSpline.addPoint(0.1F, 0.00001F);
        hillSpline.addPoint(0.2F, 0.00032F);
        hillSpline.addPoint(0.3F, 0.00243F);
        hillSpline.addPoint(0.4F, 0.01024F);
        hillSpline.addPoint(0.5F, 0.03125F);
        hillSpline.addPoint(0.6F, 0.07776F);
        hillSpline.addPoint(0.65F, 0.116029F);
        hillSpline.addPoint(0.7F, 0.16807F);
        hillSpline.addPoint(0.75F, 0.237305F);
        hillSpline.addPoint(0.8F, 0.32768F);
        hillSpline.addPoint(0.85F, 0.47715F);
        hillSpline.addPoint(0.9F, 0.631441F);
        hillSpline.addPoint(1.0F, 1.0F);

        continentalnessSpline = new Spline();
        continentalnessSpline.addPoint(0.0F, 1.0F);
        continentalnessSpline.addPoint(0.1F, 0.1F);
        continentalnessSpline.addPoint(0.35F, 0.2F);
        continentalnessSpline.addPoint(0.38F, 0.4F);
        continentalnessSpline.addPoint(0.51F, 0.5F);
        continentalnessSpline.addPoint(0.55F, 0.7F);
        continentalnessSpline.addPoint(0.6F, 0.75F);
        continentalnessSpline.addPoint(0.7F, 0.8F);
        continentalnessSpline.addPoint(1.0F, 1.0F);

        erosionSpline = new Spline();
        erosionSpline.addPoint(0.00F, 1.00F);
        erosionSpline.addPoint(0.08F, 0.70F);
        erosionSpline.addPoint(0.18F, 0.62F);
        erosionSpline.addPoint(0.30F, 0.40F);
        erosionSpline.addPoint(0.38F, 0.45F); // small bump
        erosionSpline.addPoint(0.48F, 0.05F);
        erosionSpline.addPoint(0.62F, 0.10F); // long flat
        erosionSpline.addPoint(0.72F, 0.23F);
        erosionSpline.addPoint(0.74F, 0.30F); // sharp rise
        erosionSpline.addPoint(0.80F, 0.30F); // plateau
        erosionSpline.addPoint(0.90F, 0.03F); // sharp fall
        erosionSpline.addPoint(0.96F, 0.00F);

        peaksvalleysSpline = new SplineBezier();
        peaksvalleysSpline.addPoint(0.0F, 0.0F);
        peaksvalleysSpline.addPoint(0.25F, 0.8F);
        peaksvalleysSpline.addPoint(0.5F, 0.2F);
        peaksvalleysSpline.addPoint(0.75F, 0.3F);
        peaksvalleysSpline.addPoint(1.0F, 1.0F);

        forestSpline = new Spline();
        forestSpline.addPoint(0.0F, 0.0F);
        forestSpline.addPoint(0.6F, 0.0F);
        forestSpline.addPoint(0.7F, 0.3F);
        forestSpline.addPoint(0.8F, 0.5F);
        forestSpline.addPoint(1.0F, 0.8F);
    }
}
