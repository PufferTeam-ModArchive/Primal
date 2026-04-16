package net.pufferlab.primal.tileentities;

import net.pufferlab.primal.world.GlobalTickingData;
import net.pufferlab.primal.world.HeatInfo;

public interface IHeatable extends ITile {

    default void addFuel() {
        if (consumesFuel()) {
            int meta = getMeta();
            if (meta % (getFuelStages() + 1) == (getFuelStages())) return;
            setMeta(meta + 1);
        }
    }

    default void removeFuel() {
        if (consumesFuel()) {
            int meta = getMeta();
            if (meta % (getFuelStages() + 1) == 0) {
                setFired(false);
            }
            setMeta(meta - 1);
        }
    }

    default boolean hasFuel() {
        int meta = getMeta();
        if (meta % (getFuelStages() + 1) == 0) {
            return false;
        }
        return true;
    }

    default boolean canBeFueled() {
        int meta = getMeta();
        return meta % (getFuelStages() + 1) != getFuelStages();
    }

    default int getCurrentFuelStages() {
        int meta = getMeta();
        return meta % (getFuelStages() + 1);
    }

    default int getFuelStages() {
        return 5;
    }

    default HeatInfo getHeatInfo() {
        return null;
    }

    default boolean consumesFuel() {
        return false;
    }

    public boolean canBeFired();

    default void setFired(boolean state) {
        if (consumesFuel()) {
            int meta = getMeta();
            if (state) {
                if (!isFired()) {
                    setMeta(meta + (getFuelStages() + 1));
                    updateTELight();
                }
            } else {
                if (isFired()) {
                    setMeta(meta - (getFuelStages() + 1));
                    updateTELight();
                }
            }
        }
    }

    default boolean isFired() {
        if (consumesFuel()) {
            int meta = getMeta();
            if (meta > getFuelStages()) return true;
        }
        return false;
    }

    default void setTemperatureMultiplier(float multiplier) {
        HeatInfo heat = getHeatInfo();
        if (heat != null) {
            heat.setTemperatureInfo(GlobalTickingData.getTickTime(), getMaxTemperature(), multiplier, getTemperature());
        }
        updateTEState();
    }

    default void setTemperature(int temperature) {
        HeatInfo heat = getHeatInfo();
        if (heat != null) {
            heat.setTemperatureInfo(GlobalTickingData.getTickTime(), getMaxTemperature(), getMultiplier(), temperature);
        }
        updateTEState();
    }

    default void setMaxTemperature(int maxTemperature) {
        HeatInfo heat = getHeatInfo();
        if (heat != null) {
            heat.setMaxTemperature(maxTemperature);
        }
    }

    default int getLastTemperature() {
        HeatInfo heat = getHeatInfo();
        if (heat != null) {
            return heat.getLastTemperature();
        }
        return 0;
    }

    default float getMultiplier() {
        HeatInfo heat = getHeatInfo();
        if (heat != null) {
            return heat.getMultiplier();
        }
        return 1.0F;
    }

    default int getMaxTemperature() {
        HeatInfo heat = getHeatInfo();
        if (heat != null) {
            return heat.getMaxTemperature();
        }
        return 1300;
    }

    default int getTemperature() {
        HeatInfo heat = getHeatInfo();
        if (heat != null) {
            return heat.getTemperature(getWorld());
        }
        return 0;
    }

    public boolean isHeatProvider();
}
