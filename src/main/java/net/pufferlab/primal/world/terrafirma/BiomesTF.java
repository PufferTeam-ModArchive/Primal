package net.pufferlab.primal.world.terrafirma;

import net.pufferlab.primal.utils.Utils;

public class BiomesTF {

    public enum Type {
        surface,
        underwater
    }

    public static BiomesTF[] biomes = new BiomesTF[0];

    public static final BiomesTF plain = new BiomesTF(Type.surface, "plain");
    public static final BiomesTF ocean = new BiomesTF(Type.underwater, "ocean");

    public String name;
    public Type type;
    public int id;

    public BiomesTF(Type type, String name) {
        this.name = name;
        this.id = biomes.length;
        biomes = Utils.append(biomes, this);
        this.type = type;
    }

    public static BiomesTF getBiome(int id) {
        return biomes[id];
    }

    public static int getID(BiomesTF biome) {
        return biome.id;
    }
}
