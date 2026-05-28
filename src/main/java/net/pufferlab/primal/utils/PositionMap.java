package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.List;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public class PositionMap<T> {

    public TLongObjectMap<List<T>> map = new TLongObjectHashMap<>();

    public List<T> put(int x, int y, int z, T object) {
        List<T> list = get(x, y, z);
        if (list == null) {
            list = new ArrayList<>();
            map.put(PositionUtils.packCoord(x, y, z), list);
        }
        list.add(object);
        return list;
    }

    public List<T> put(int x, int z, T object) {
        List<T> list = get(x, z);
        if (list == null) {
            list = new ArrayList<>();
            map.put(PositionUtils.packChunkCoord(x, z), list);
        }
        list.add(object);
        return list;
    }

    public List<T> get(int x, int y, int z) {
        return map.get(PositionUtils.packCoord(x, y, z));
    }

    public List<T> get(int x, int z) {
        return map.get(PositionUtils.packChunkCoord(x, z));
    }

    public List<T> remove(int x, int y, int z) {
        return map.remove(PositionUtils.packCoord(x, y, z));
    }

    public List<T> remove(int x, int z) {
        return map.remove(PositionUtils.packChunkCoord(x, z));
    }
}
