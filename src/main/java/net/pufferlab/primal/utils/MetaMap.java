package net.pufferlab.primal.utils;

import java.util.HashSet;
import java.util.Set;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class MetaMap {

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

    private static class Base<K, T> {

        private int lastID;
        private final TObjectIntMap<K> metaMap = new TObjectIntHashMap<>();
        private final TIntObjectMap<K> objectMap = new TIntObjectHashMap<>();

        public int getObjectID(K item) {
            if (metaMap.containsKey(item)) {
                return metaMap.get(item);
            }
            int blockID = lastID++;
            metaMap.put(item, blockID);
            objectMap.put(blockID, item);
            return blockID;
        }

        public int getMetaID(K item, int meta) {
            return HashUtils.packShort(getObjectID(item), meta);
        }

        public K unpackObject(int id) {
            return objectMap.get(HashUtils.unpackShortA(id));
        }

        public int unpackMeta(int id) {
            return HashUtils.unpackShortB(id);
        }
    }

    public static class Single<K, T> extends Base<K, T> {

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
                    K obj = unpackObject(id);
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
