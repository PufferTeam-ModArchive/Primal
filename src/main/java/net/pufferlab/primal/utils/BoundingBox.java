package net.pufferlab.primal.utils;

import net.minecraft.util.AxisAlignedBB;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class BoundingBox extends AxisAlignedBB {

    public Matrix4f matrix = new Matrix4f();
    public Vector3f center = new Vector3f();
    public Vector3f rotation = new Vector3f();

    protected BoundingBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        super(x1, y1, z1, x2, y2, z2);
        center.set(
            (float) ((this.minX + this.maxX) * 0.5f),
            (float) ((this.minY + this.maxY) * 0.5f),
            (float) ((this.minZ + this.maxZ) * 0.5f));
    }

    public static BoundingBox getBoundingBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new BoundingBox(x1, y1, z1, x2, y2, z2);
    }

    public BoundingBox setRotation(float rotX, float rotY, float rotZ) {
        this.rotation.set(rotX, rotY, rotZ);
        this.matrix.rotateXYZ((float) Math.toRadians(rotX), (float) Math.toRadians(rotY), (float) Math.toRadians(rotZ));
        return this;
    }

    public BoundingBox setRotation(Vector3f rot) {
        this.rotation.set(rot);
        this.matrix
            .rotateXYZ((float) Math.toRadians(rot.x), (float) Math.toRadians(rot.y), (float) Math.toRadians(rot.z));
        return this;
    }

    public BoundingBox expand(double x, double y, double z) {
        double d3 = this.minX - x;
        double d4 = this.minY - y;
        double d5 = this.minZ - z;
        double d6 = this.maxX + x;
        double d7 = this.maxY + y;
        double d8 = this.maxZ + z;
        return getBoundingBox(d3, d4, d5, d6, d7, d8).setRotation(this.rotation);
    }

    public BoundingBox getOffsetBoundingBox(double x, double y, double z) {
        return getBoundingBox(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z)
            .setRotation(this.rotation);
    }

    public Vector3f getCenter() {
        return center;
    }

}
