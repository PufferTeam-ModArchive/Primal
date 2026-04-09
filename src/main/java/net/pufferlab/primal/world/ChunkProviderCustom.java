package net.pufferlab.primal.world;

public class ChunkProviderCustom {

    private static int seaLevel = 105;
    private static double factor;

    static {
        factor = (seaLevel - 63.0D) / 8.0D;
    }

    public static int getSeaLevel() {
        return seaLevel;
    }

    public static double getRaiseFactor() {
        return factor;
    }
}
