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
}
