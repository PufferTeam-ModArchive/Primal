package net.pufferlab.primal.tileentities;

public interface IHeatable {

    public boolean canBeFired();

    public void setFired(boolean state);

    public boolean isFired();

    public int getMaxTemperature();

    public int getTemperature();

    public boolean isHeatProvider();
}
