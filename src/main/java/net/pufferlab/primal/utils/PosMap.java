package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.List;

import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public class PosMap {

    public static class Single<T> {

        public TLongObjectMap<T> map = new TLongObjectHashMap<>();

        public T put(int x, int y, int z, T object) {
            map.put(HashUtils.packCoord(x, y, z), object);
            return object;
        }

        public T put(int x, int z, T object) {
            map.put(HashUtils.packChunkCoord(x, z), object);
            return object;
        }

        public T get(int x, int y, int z) {
            return map.get(HashUtils.packCoord(x, y, z));
        }

        public T get(int x, int z) {
            return map.get(HashUtils.packChunkCoord(x, z));
        }

        public T remove(int x, int y, int z) {
            return map.remove(HashUtils.packCoord(x, y, z));
        }

        public T remove(int x, int z) {
            return map.remove(HashUtils.packChunkCoord(x, z));
        }

        public void clear() {
            map.clear();
        }
    }

    public static class Multi<T> {

        public TLongObjectMap<List<T>> map = new TLongObjectHashMap<>();

        public List<T> put(int x, int y, int z, T object) {
            List<T> list = get(x, y, z);
            if (list == null) {
                list = new ArrayList<>();
                map.put(HashUtils.packCoord(x, y, z), list);
            }
            list.add(object);
            return list;
        }

        public List<T> put(int x, int z, T object) {
            List<T> list = get(x, z);
            if (list == null) {
                list = new ArrayList<>();
                map.put(HashUtils.packChunkCoord(x, z), list);
            }
            list.add(object);
            return list;
        }

        public List<T> get(int x, int y, int z) {
            return map.get(HashUtils.packCoord(x, y, z));
        }

        public List<T> get(int x, int z) {
            return map.get(HashUtils.packChunkCoord(x, z));
        }

        public List<T> remove(int x, int y, int z) {
            return map.remove(HashUtils.packCoord(x, y, z));
        }

        public List<T> remove(int x, int z) {
            return map.remove(HashUtils.packChunkCoord(x, z));
        }
    }
}
