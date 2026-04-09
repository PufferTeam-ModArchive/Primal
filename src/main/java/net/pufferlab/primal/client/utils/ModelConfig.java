package net.pufferlab.primal.client.utils;

import net.pufferlab.primal.Constants;

public class ModelConfig {

    public boolean[] quadsEnabled;
    public ModelBound bound;

    public ModelConfig() {
        this.quadsEnabled = new boolean[] { true, true, true, true, true, true };
    }

    public ModelConfig(boolean blacklist) {
        if (blacklist) {
            this.quadsEnabled = new boolean[] { false, false, false, false, false, false };
        } else {
            this.quadsEnabled = new boolean[] { true, true, true, true, true, true };
        }
    }

    public ModelConfig setBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        bound = new ModelBound(
            minX * Constants.pixel,
            minY * Constants.pixel,
            minZ * Constants.pixel,
            maxX * Constants.pixel,
            maxY * Constants.pixel,
            maxZ * Constants.pixel);
        return this;
    }

    public ModelConfig setUp(boolean state) {
        this.quadsEnabled[3] = state;
        return this;
    }

    public ModelConfig setDown(boolean state) {
        this.quadsEnabled[2] = state;
        return this;
    }

    public ModelConfig setWest(boolean state) {
        this.quadsEnabled[1] = state;
        return this;
    }

    public ModelConfig setEast(boolean state) {
        this.quadsEnabled[0] = state;
        return this;
    }

    public ModelConfig setNorth(boolean state) {
        this.quadsEnabled[4] = state;
        return this;
    }

    public ModelConfig setSouth(boolean state) {
        this.quadsEnabled[5] = state;
        return this;
    }
}
