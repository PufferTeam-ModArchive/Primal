package net.pufferlab.primal.utils;

import java.util.HashMap;
import java.util.Map;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class IdentifierMap {

    public static class Obj<T, K> {

        private final Map<String, K> nameToID = new HashMap<>();
        private final Map<K, String> idToName = new HashMap<>();
        private final Map<K, T> idToObject = new HashMap<>();

        public Obj() {}

        public boolean contains(T obj) {
            String name = getIdentifier(obj);
            return nameToID.containsKey(name);
        }

        public void putObject(T obj, K id) {
            String name = getIdentifier(obj);
            if (name != null) {
                if (nameToID.containsKey(name)) {
                    throw new IllegalStateException("Cannot map '" + name + "' as it already has a identifier");
                } else {
                    nameToID.put(name, id);
                    idToName.put(id, name);
                    idToObject.put(id, obj);
                }
            } else {
                throw new IllegalStateException("Cannot add a null object");
            }
        }

        public K getID(java.lang.Object obj) {
            String name = getIdentifier(obj);
            return nameToID.get(name);
        }

        public static String getIdentifier(java.lang.Object obj) {
            if (obj instanceof String string) {
                return string;
            }
            if (obj instanceof IIdentifiable string) {
                return string.getIdentifier();
            }
            return obj.getClass()
                .getName();
        }

        public String getName(K id) {
            return idToName.get(id);
        }

        public T getObject(K id) {
            return idToObject.get(id);
        }
    }

    public static class Int<T> {

        private final TObjectIntMap<String> nameToID = new TObjectIntHashMap<>();
        private final TIntObjectMap<String> idToName = new TIntObjectHashMap<>();
        private final TIntObjectMap<T> idToObject = new TIntObjectHashMap<>();

        public Int() {}

        public boolean contains(T obj) {
            String name = getIdentifier(obj);
            return nameToID.containsKey(name);
        }

        public void putObject(T obj, int id) {
            String name = getIdentifier(obj);
            if (name != null) {
                if (nameToID.containsKey(name)) {
                    throw new IllegalStateException("Cannot map '" + name + "' as it already has a identifier");
                } else {
                    nameToID.put(name, id);
                    idToName.put(id, name);
                    idToObject.put(id, obj);
                }
            } else {
                throw new IllegalStateException("Cannot add a null object");
            }
        }

        public int getID(java.lang.Object obj) {
            String name = getIdentifier(obj);
            return nameToID.get(name);
        }

        public static String getIdentifier(java.lang.Object obj) {
            if (obj instanceof String string) {
                return string;
            }
            if (obj instanceof IIdentifiable string) {
                return string.getIdentifier();
            }
            return obj.getClass()
                .getName();
        }

        public String getName(int id) {
            return idToName.get(id);
        }

        public T getObject(int id) {
            return idToObject.get(id);
        }
    }
}
