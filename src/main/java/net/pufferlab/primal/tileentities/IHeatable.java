package net.pufferlab.primal.tileentities;

import net.pufferlab.primal.world.GlobalTickingData;
import net.pufferlab.primal.world.HeatInfo;

public interface IHeatable extends ITile {

    default HeatInfo getHeatInfo() {
        return null;
    }

    public boolean canBeFired();

    public void setFired(boolean state);

    public boolean isFired();

    default void setTemperature(float multiplier) {
        HeatInfo heat = getHeatInfo();
        if (heat != null) {
            heat.setTemperatureInfo(GlobalTickingData.getTickTime(), getMaxTemperature(), multiplier, getTemperature());
        }
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
            return heat.getTemperature();
        }
        return 0;
    }

    public boolean isHeatProvider();
}
