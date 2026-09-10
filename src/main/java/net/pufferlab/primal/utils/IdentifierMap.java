package net.pufferlab.primal.utils;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;

public class IdentifierMap<T> {

    private final TObjectIntMap<String> nameToID = new TObjectIntHashMap<>();
    private final TIntObjectMap<String> idToName = new TIntObjectHashMap<>();
    private final TIntObjectMap<T> idToObject = new TIntObjectHashMap<>();

    public IdentifierMap() {}

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

    public int getID(Object obj) {
        String name = getIdentifier(obj);
        return nameToID.get(name);
    }

    public static String getIdentifier(Object obj) {
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
