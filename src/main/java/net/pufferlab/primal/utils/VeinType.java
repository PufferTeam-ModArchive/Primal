package net.pufferlab.primal.utils;

import java.util.Random;

import net.minecraft.world.World;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;

public class VeinType implements IPrimalType {

    public String name;
    public OreType oreType;
    public float rarity;
    public float rarityBlock;
    public float rarityIndicator;
    public int sizeMin;
    public int sizeMax;
    public int minHeight;
    public int maxHeight;
    public int minHeightTF;
    public int maxHeightTF;
    public StoneType[] stoneTypes;

    public VeinType(OreType oreType, String name, int minY, int maxY, int sizeMin, int sizeMax, float rarityIndicator,
        float rarityBlock, float rarity, StoneType... stoneTypes) {
        this.name = name;
        this.oreType = oreType;
        this.sizeMin = sizeMin;
        this.sizeMax = sizeMax;
        this.rarityIndicator = rarityIndicator;
        this.rarityBlock = rarityBlock;
        this.rarity = rarity;
        this.maxHeight = maxY;
        this.minHeight = minY;
        this.minHeightTF = Utils.floor(minHeight * Constants.heightMultiplier);
        this.maxHeightTF = Utils.floor(maxHeight * Constants.heightMultiplier);
        this.stoneTypes = stoneTypes;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMinInt(Config.Value.Type index) {
        if (index == Config.Value.Type.height) {
            return minHeight;
        } else if (index == Config.Value.Type.heightTF) {
            return minHeightTF;
        } else if (index == Config.Value.Type.size) {
            return sizeMin;
        }
        return 0;
    }

    @Override
    public int getMaxInt(Config.Value.Type index) {
        if (index == Config.Value.Type.height) {
            return maxHeight;
        } else if (index == Config.Value.Type.heightTF) {
            return maxHeightTF;
        } else if (index == Config.Value.Type.size) {
            return sizeMax;
        }
        return 0;
    }

    @Override
    public void setMinInt(Config.Value.Type index, int min) {
        if (index == Config.Value.Type.height) {
            this.minHeight = min;
        } else if (index == Config.Value.Type.heightTF) {
            this.minHeightTF = min;
        } else if (index == Config.Value.Type.size) {
            this.sizeMin = min;
        }
    }

    @Override
    public void setMaxInt(Config.Value.Type index, int max) {
        if (index == Config.Value.Type.height) {
            this.maxHeight = max;
        } else if (index == Config.Value.Type.heightTF) {
            this.maxHeightTF = max;
        } else if (index == Config.Value.Type.size) {
            this.sizeMax = max;
        }
    }

    @Override
    public float getFloat(Config.Value.Type index) {
        if (index == Config.Value.Type.rarity) {
            return rarity;
        }
        return 0.0F;
    }

    @Override
    public void setFloat(Config.Value.Type index, float primary) {
        if (index == Config.Value.Type.rarity) {
            this.rarity = primary;
        }
    }

    public VeinType setHeight(int min, int max) {
        this.minHeight = min;
        this.maxHeight = max;
        return this;
    }

    public VeinType setSize(int min, int max) {
        this.sizeMin = min;
        this.sizeMax = max;
        return this;
    }

    public VeinType setRarity(float rarity) {
        this.rarity = rarity;
        return this;
    }

    public boolean canGenerate(int height) {
        if (height < maxHeight && height > minHeight) {
            return true;
        }
        return false;
    }

    public boolean isValidStone(StoneType stone) {
        return Utils.contains(stoneTypes, stone);
    }

    public int getSize(Random rand) {
        int value = rand.nextInt(sizeMax - sizeMin + 1) + sizeMin;
        return value;
    }

    public boolean getChance(Random random) {
        if (random.nextFloat() < this.rarity) {
            return true;
        }
        return false;
    }

    public boolean getChanceBlock(Random random) {
        if (random.nextFloat() < this.rarityBlock) {
            return true;
        }
        return false;
    }

    public boolean getChanceIndicator(Random random) {
        if (random.nextFloat() < this.rarityIndicator) {
            return true;
        }
        return false;
    }

    public int getHeight(World world, Random random) {
        if (WorldUtils.isTerraFirma(world)) {
            return minHeightTF + random.nextInt(Math.abs(maxHeightTF - minHeightTF));
        } else {
            return minHeight + random.nextInt(Math.abs(maxHeight - minHeight));
        }
    }
}
