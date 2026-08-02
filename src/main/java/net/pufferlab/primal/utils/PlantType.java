package net.pufferlab.primal.utils;

import net.minecraft.block.Block;

public class PlantType implements IPrimalType {

    public String name;
    public boolean doublePlant = false;
    public Block plantBlock;
    public int plantMeta;
    public Block plantBlock2;
    public int plantMeta2;

    public PlantType(String name) {
        this.name = name;
    }

    public PlantType setPlantItem(Block block, int meta) {
        this.plantBlock = block;
        this.plantMeta = meta;
        return this;
    }

    public PlantType setDoublePlantItem(Block block, int meta, Block block2, int meta2) {
        this.plantBlock = block;
        this.plantMeta = meta;
        this.plantBlock2 = block2;
        this.plantMeta2 = meta2;
        this.doublePlant = true;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }
}
