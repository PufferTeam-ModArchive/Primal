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
        String str = obj.getClass()
            .getName();
        putName(str, id);
        idToObject.put(id, obj);
    }

    public void putName(String name, int id) {
        nameToID.put(name, id);
        idToName.put(id, name);
    }

    public int getID(Object name) {
        return getID(
            name.getClass()
                .getName());
    }

    public int getID(String name) {
        return nameToID.get(name);
    }

    public String getName(int id) {
        return idToName.get(id);
    }

    public T getObject(int id) {
        return idToObject.get(id);
    }
}
