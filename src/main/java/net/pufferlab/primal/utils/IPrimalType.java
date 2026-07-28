package net.pufferlab.primal.utils;

import net.pufferlab.primal.Config;

public interface IPrimalType {

    public String getName();

    default int getMinInt(Config.Value.Type index) {
        return -1;
    }

    default void setMinInt(Config.Value.Type index, int min) {}

    default int getMaxInt(Config.Value.Type index) {
        return -1;
    }

    default void setMaxInt(Config.Value.Type index, int max) {}

    default int getInt(Config.Value.Type index) {
        return -1;
    }

    default void setInt(Config.Value.Type index, int primary) {}

    default float getFloat(Config.Value.Type index) {
        return -1;
    }

    default void setFloat(Config.Value.Type index, float primary) {}

    default String getString(Config.Value.Type index) {
        return null;
    }

    default void setString(Config.Value.Type index, String string) {}
}
