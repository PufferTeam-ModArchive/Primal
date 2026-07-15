package net.pufferlab.primal.utils;

import org.joml.Vector3f;

public class Line {

    public Vector3f a;
    public Vector3f b;
    public static double range = 0.02D;

    public Line(Vector3f a, Vector3f b) {
        this.a = a;
        this.b = b;
    }

    public boolean isClose(Line line) {

        boolean direct = Utils.roughlyEquals(this.a.x, line.a.x, range)
            && Utils.roughlyEquals(this.a.y, line.a.y, range)
            && Utils.roughlyEquals(this.a.z, line.a.z, range)
            &&

            Utils.roughlyEquals(this.b.x, line.b.x, range)
            && Utils.roughlyEquals(this.b.y, line.b.y, range)
            && Utils.roughlyEquals(this.b.z, line.b.z, range);

        if (direct) return true;

        boolean reversed = Utils.roughlyEquals(this.a.x, line.b.x, range)
            && Utils.roughlyEquals(this.a.y, line.b.y, range)
            && Utils.roughlyEquals(this.a.z, line.b.z, range)
            &&

            Utils.roughlyEquals(this.b.x, line.a.x, range)
            && Utils.roughlyEquals(this.b.y, line.a.y, range)
            && Utils.roughlyEquals(this.b.z, line.a.z, range);

        return reversed;
    }
}
