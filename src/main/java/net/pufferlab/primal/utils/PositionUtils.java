package net.pufferlab.primal.utils;

public class PositionUtils {

    // Coordinates Bits
    public static final int bx = 26; // 26 bits [-33,554,432 to 33,554,432]
    public static final int by = 12; // 12 bits [-2048 to 2048]
    public static final int bz = 26; // 26 bits [-33,554,432 to 33,554,432]

    public static final int bzs = 0;
    public static final int bys = bz;
    public static final int bxs = bz + by;

    public static final long xm = (1L << bx) - 1L;
    public static final long ym = (1L << by) - 1L;
    public static final long zm = (1L << bz) - 1L;

    public static final long cm = 0xFFFFFFFFL;
    public static final int czs = 32;

    public static long packCoord(int x, int y, int z) {

        long lx = x & xm;
        long ly = y & ym;
        long lz = z & zm;

        return (lx << bxs) | (ly << bys) | (lz << bzs);
    }

    public static long packChunkCoord(int x, int z) {

        return ((long) x & cm) | (((long) z & cm) << czs);
    }

    public static int unpackX(long packed) {

        return (int) (packed << (64 - bx - bxs) >> (64 - bx));
    }

    public static int unpackY(long packed) {

        return (int) (packed << (64 - by - bys) >> (64 - by));
    }

    public static int unpackZ(long packed) {

        return (int) (packed << (64 - bz - bzs) >> (64 - bz));
    }

    public static int packCoord2(int x, int y, int z) {
        return ((x + 256) & 0x3FF) << 20 | ((y + 256) & 0x3FF) << 10 | ((z + 256) & 0x3FF);
    }

    public static int unpackX2(int packed) {
        return ((packed >>> 20) & 0x3FF) - 256;
    }

    public static int unpackY2(int packed) {
        return ((packed >>> 10) & 0x3FF) - 256;
    }

    public static int unpackZ2(int packed) {
        return (packed & 0x3FF) - 256;
    }
}
