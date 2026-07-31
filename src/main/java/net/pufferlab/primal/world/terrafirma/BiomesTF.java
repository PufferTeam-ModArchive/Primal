package net.pufferlab.primal.world.terrafirma;

public enum BiomesTF {

    plains,
    ocean,
    rocky;

    BiomesTF() {

    }

    public static BiomesTF[] biomes;

    public static BiomesTF getBiome(int id) {
        if (biomes == null) {
            biomes = values();
        }
        return biomes[id];
    }

    public static int getID(BiomesTF biome) {
        return biome.ordinal();
    }
}
