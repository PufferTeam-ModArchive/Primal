package net.pufferlab.primal.utils;

public class PositionUtils {

    public static long packCoord(int x, int y, int z) {
        long lx = x & 0x3FFFFFFL; // 26 bits [-33,554,432 to 33,554,432]
        long ly = y & 0xFFFL; // 12 bits [-2048 to 2048]
        long lz = z & 0x3FFFFFFL; // 26 bits [-33,554,432 to 33,554,432]
        return (lx << 38) | (ly << 26) | lz;
    }

    public static long packChunkCoord(int x, int z) {
        return (long) x & 4294967295L | ((long) z & 4294967295L) << 32;
    }

    public static int unpackX(long packed) {
        return (int) (packed << 0 >> 38); // signed
    }

    public static int unpackY(long packed) {
        return (int) (packed << 26 >> 52); // signed
    }

    public static int unpackZ(long packed) {
        return (int) (packed << 38 >> 38); // signed
    }
}
