package net.pufferlab.primal.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.AxisAlignedBB;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CollideUtils {

    public static List<Line> getCleanedLines(List<AxisAlignedBB> axisAlignedBBS) {
        List<Line> line = getLines(axisAlignedBBS);
        cleanLines(line);
        return line;
    }

    public static List<Line> getLines(List<AxisAlignedBB> axisAlignedBBS) {
        List<Line> lines = new ArrayList<>();
        for (AxisAlignedBB aabb : axisAlignedBBS) {
            processLines(lines, CollideUtils.getCorners(aabb));
        }
        return lines;
    }

    public static List<Line> getRotatedLines(BoundingBox axisAlignedBBS) {
        List<Line> lines = new ArrayList<>();

        Vector3f center = axisAlignedBBS.getCenter();

        processLines(lines, CollideUtils.getCorners(axisAlignedBBS));

        for (Line line : lines) {
            transformAroundCenter(line.a, axisAlignedBBS.matrix, center);
            transformAroundCenter(line.b, axisAlignedBBS.matrix, center);
        }

        return lines;
    }

    private static void transformAroundCenter(Vector3f point, Matrix4f matrix, Vector3f center) {
        point.sub(center);
        matrix.transformPosition(point);
        point.add(center);
    }

    public static void processLines(List<Line> lines, Vector3f[] v) {
        // Bottom face
        putLine(lines, v[0], v[1]);
        putLine(lines, v[1], v[2]);
        putLine(lines, v[2], v[3]);
        putLine(lines, v[3], v[0]);

        // Top face
        putLine(lines, v[4], v[5]);
        putLine(lines, v[5], v[6]);
        putLine(lines, v[6], v[7]);
        putLine(lines, v[7], v[4]);

        // Vertical edges
        putLine(lines, v[0], v[4]);
        putLine(lines, v[1], v[5]);
        putLine(lines, v[2], v[6]);
        putLine(lines, v[3], v[7]);
    }

    public static void putLine(List<Line> lines, Vector3f a, Vector3f b) {
        lines.add(new Line(new Vector3f(a), new Vector3f(b)));
    }

    public static void cleanLines(List<Line> lines) {
        List<Line> copy = new ArrayList<>(lines);
        lines.clear();

        for (int i = 0; i < copy.size(); i++) {
            Line a = copy.get(i);

            boolean hasDuplicate = false;

            for (int j = 0; j < copy.size(); j++) {
                if (i == j) continue;

                if (a.isClose(copy.get(j))) {
                    hasDuplicate = true;
                    break;
                }
            }

            // only keep lines that have NO duplicates at all
            if (!hasDuplicate) {
                lines.add(a);
            }
        }
    }

    public static Vector3f[] getCorners(AxisAlignedBB aabb) {
        Vector3f minVec = new Vector3f((float) aabb.minX, (float) aabb.minY, (float) aabb.minZ);
        Vector3f maxVec = new Vector3f((float) aabb.maxX, (float) aabb.maxY, (float) aabb.maxZ);
        return getCorners(minVec, maxVec);
    }

    public static Vector3f[] getCorners(Vector3f minVec, Vector3f maxVec) {
        return getVector3fs(minVec, maxVec);
    }

    public static Vector3f[] getVector3fs(Vector3f minVec, Vector3f maxVec) {
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

    // THIS IS SHIT COPY PASTED CODE (PROBABLY DOESNT WORK)
    public static Vector3f find(Vector3f[] cornersA, Vector3f[] cornersB) {
        Vector3f[] axesA = getAxes(cornersA);
        Vector3f[] axesB = getAxes(cornersB);

        Vector3f[] axes = Utils.combineArrays(axesA, axesB);

        return run(axes, cornersA, cornersB);
    }

    public static Vector3f[] getAxes(Vector3f[] corners) {
        Vector3f x = new Vector3f(corners[1]).sub(corners[0])
            .normalize();

        Vector3f y = new Vector3f(corners[3]).sub(corners[0])
            .normalize();

        Vector3f z = new Vector3f(corners[4]).sub(corners[0])
            .normalize();

        return new Vector3f[] { x, y, z };
    }

    public static Vector3f run(Vector3f[] axes, Vector3f[] cornersA, Vector3f[] cornersB) {
        float smallestOverlap = Float.POSITIVE_INFINITY;
        Vector3f smallestAxis = null;

        for (Vector3f axis : axes) {
            Vector3f normalizedAxis = new Vector3f(axis).normalize();

            float overlap = test(normalizedAxis, cornersA, cornersB);

            if (overlap < 0) {
                return new Vector3f(0, 0, 0); // no collision
            }

            if (overlap < smallestOverlap) {
                smallestOverlap = overlap;
                smallestAxis = normalizedAxis;
            }
        }

        Vector3f mtv = new Vector3f(smallestAxis).mul(smallestOverlap);

        Vector3f direction = new Vector3f(getMiddle(cornersB)).sub(getMiddle(cornersA));

        // Make MTV push A away from B
        if (direction.dot(mtv) > 0) {
            mtv.negate();
        }

        return mtv;
    }

    public static Vector3f getMiddle(Vector3f[] cornersB) {
        Vector3f centerB = new Vector3f();

        for (Vector3f corner : cornersB) {
            centerB.add(corner);
        }

        centerB.mul(1.0f / cornersB.length);
        return centerB;
    }

    public static float test(Vector3f axis, Vector3f[] cornersA, Vector3f[] cornersB) {
        float minA = Float.POSITIVE_INFINITY;
        float maxA = Float.NEGATIVE_INFINITY;

        for (Vector3f corner : cornersA) {
            float p = corner.dot(axis);
            minA = Math.min(minA, p);
            maxA = Math.max(maxA, p);
        }

        float minB = Float.POSITIVE_INFINITY;
        float maxB = Float.NEGATIVE_INFINITY;

        for (Vector3f corner : cornersB) {
            float p = corner.dot(axis);
            minB = Math.min(minB, p);
            maxB = Math.max(maxB, p);
        }

        // Separating axis found
        if (maxA < minB || maxB < minA) {
            return -1;
        }

        // Amount of overlap
        return Math.min(maxA, maxB) - Math.max(minA, minB);
    }
}
