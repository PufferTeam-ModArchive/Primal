package net.pufferlab.primal.utils;

import java.util.HashSet;
import java.util.Set;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class MetaMap {

    public static int lastID;
    public static final TObjectIntMap<Object> metaMap = new TObjectIntHashMap<>();
    public static final TIntObjectMap<Object> objectMap = new TIntObjectHashMap<>();

    public static int getObjectID(Object item) {
        if (metaMap.containsKey(item)) {
            return metaMap.get(item);
        }
        int blockID = lastID++;
        metaMap.put(item, blockID);
        objectMap.put(blockID, item);
        return blockID;
    }

    public static int getMetaID(Object item, int meta) {
        return HashUtils.packShort(getObjectID(item), meta);
    }

    public static Object unpackObject(int id) {
        return objectMap.get((id >> 16) & 0xFFFF);
    }

    public static int unpackMeta(int id) {
        return id & 0xFFFF;
    }

    public static class MapEntry<K, T> {

        public K item;
        public int meta;
        public T value;

        public MapEntry(K item, int meta, T value) {
            this.item = item;
            this.meta = meta;
            this.value = value;
        }
    }

    public static class Single<K, T> {

        private final TIntObjectMap<T> map = new TIntObjectHashMap<>();
        private final Set<MapEntry<K, T>> entrySet = new HashSet<>();

        public void put(K block, int meta, T value) {
            int id = getMetaID(block, meta);
            map.put(id, value);
        }

        public T get(K block, int meta) {
            int id = getMetaID(block, meta);
            return map.get(id);
        }

        public Set<MapEntry<K, T>> entrySet() {
            if (entrySet.isEmpty()) {
                for (int id : map.keys()) {
                    K obj = (K) unpackObject(id);
                    int meta = unpackMeta(id);
                    T value = map.get(id);
                    MapEntry<K, T> mapEntry = new MapEntry<>(obj, meta, value);
                    entrySet.add(mapEntry);
                }
            }
            return entrySet;
        }
    }
}
