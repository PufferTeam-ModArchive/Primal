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
        String name = obj.getClass()
            .getName();
        if (obj instanceof String string) {
            name = string;
        }
        nameToID.put(name, id);
        idToName.put(id, name);
        idToObject.put(id, obj);
    }

    public int getID(Object obj) {
        String name = obj.getClass()
            .getName();
        if (obj instanceof String string) {
            name = string;
        }
        return nameToID.get(name);
    }

    public String getName(int id) {
        return idToName.get(id);
    }

    public T getObject(int id) {
        return idToObject.get(id);
    }
}
