package net.pufferlab.primal.utils;

public class CoordUtils {

    public static long pack(int x, int y, int z) {
        long lx = x & 0x3FFFFFFL; // 26 bits
        long ly = y & 0xFFFL; // 12 bits
        long lz = z & 0x3FFFFFFL; // 26 bits
        return (lx << 38) | (ly << 26) | lz;
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
