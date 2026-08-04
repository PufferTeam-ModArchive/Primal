package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;

import gnu.trove.iterator.TLongObjectIterator;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.hash.TLongObjectHashMap;

public class PosMap {

    public static class ConcurrentSingle<T> {

        public ConcurrentMap<Long, T> map = new ConcurrentHashMap<>();

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

        public Collection<T> values() {
            return map.values();
        }

        public T computeIfAbsent(int x, int z, Function<? super Long, ? extends T> mappingFunction) {
            return map.computeIfAbsent(HashUtils.packChunkCoord(x, z), mappingFunction);
        }

        public T computeIfAbsent(int x, int y, int z, Function<? super Long, ? extends T> mappingFunction) {
            return map.computeIfAbsent(HashUtils.packCoord(x, y, z), mappingFunction);
        }
    }

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

        public void removeIf(Predicate<? super T> filter) {
            TLongObjectIterator<T> it = map.iterator();

            while (it.hasNext()) {
                it.advance();

                if (filter.test(it.value())) {
                    it.remove();
                }
            }
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
