package net.pufferlab.primal.client.utils;

import net.minecraft.util.AxisAlignedBB;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class ModelBound {

    public Vector3f minVec;
    public Vector3f maxVec;

    public ModelBound(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
        minVec = new Vector3f(minX, minY, minZ);
        maxVec = new Vector3f(maxX, maxY, maxZ);
    }

    public ModelBound(AxisAlignedBB aabb) {
        minVec = new Vector3f((float) aabb.minX, (float) aabb.minY, (float) aabb.minZ);

        maxVec = new Vector3f((float) aabb.maxX, (float) aabb.maxY, (float) aabb.maxZ);
    }

    public ModelBound transform(Matrix4f matrix) {

        Vector3f[] corners = getCorners();

        float minX = Float.POSITIVE_INFINITY;
        float minY = Float.POSITIVE_INFINITY;
        float minZ = Float.POSITIVE_INFINITY;

        float maxX = Float.NEGATIVE_INFINITY;
        float maxY = Float.NEGATIVE_INFINITY;
        float maxZ = Float.NEGATIVE_INFINITY;

        for (Vector3f corner : corners) {

            matrix.transformPosition(corner);

            minX = Math.min(minX, corner.x);
            minY = Math.min(minY, corner.y);
            minZ = Math.min(minZ, corner.z);

            maxX = Math.max(maxX, corner.x);
            maxY = Math.max(maxY, corner.y);
            maxZ = Math.max(maxZ, corner.z);
        }

        return new ModelBound(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public Vector3f[] getCorners() {
        return new Vector3f[] {

            // Bottom
            new Vector3f(minVec.x, minVec.y, minVec.z), // 0
            new Vector3f(maxVec.x, minVec.y, minVec.z), // 1
            new Vector3f(maxVec.x, minVec.y, maxVec.z), // 2
            new Vector3f(minVec.x, minVec.y, maxVec.z), // 3

            // Top
            new Vector3f(minVec.x, maxVec.y, minVec.z), // 4
            new Vector3f(maxVec.x, maxVec.y, minVec.z), // 5
            new Vector3f(maxVec.x, maxVec.y, maxVec.z), // 6
            new Vector3f(minVec.x, maxVec.y, maxVec.z) // 7
        };
    }

    public AxisAlignedBB getAxisAlignedBB() {
        return AxisAlignedBB.getBoundingBox(minVec.x, minVec.y, minVec.z, maxVec.x, maxVec.y, maxVec.z);
    }
}
