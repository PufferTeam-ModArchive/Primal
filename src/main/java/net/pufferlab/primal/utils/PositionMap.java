package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.List;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public class PositionMap<T> {

    public TLongObjectMap<List<T>> map = new TLongObjectHashMap<>();

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

    public List<T> put(int x, int y, int z, T object) {
        List<T> list = get(x, y, z);
        if (list == null) {
            list = new ArrayList<>();
            map.put(packCoord(x, y, z), list);
        }
        list.add(object);
        return list;
    }

    public List<T> put(int x, int z, T object) {
        List<T> list = get(x, z);
        if (list == null) {
            list = new ArrayList<>();
            map.put(packChunkCoord(x, z), list);
        }
        list.add(object);
        return list;
    }

    public List<T> get(int x, int y, int z) {
        return map.get(packCoord(x, y, z));
    }

    public List<T> get(int x, int z) {
        return map.get(packChunkCoord(x, z));
    }

    public List<T> remove(int x, int y, int z) {
        return map.remove(packCoord(x, y, z));
    }

    public List<T> remove(int x, int z) {
        return map.remove(packChunkCoord(x, z));
    }
}
