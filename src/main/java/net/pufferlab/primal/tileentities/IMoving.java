package net.pufferlab.primal.tileentities;

public interface IMoving extends ITile {

    public int getMovingAxis();

    public float getMovingSpeed();

    public float getMovingAngle();

    public void setMovingSpeed(float speed);

    public void setMovingAngle(float angle);

    public void setMovingAxis(int axis);

}
