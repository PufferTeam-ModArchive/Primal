package net.pufferlab.primal.client.helper;

import net.minecraft.client.renderer.Tessellator;

public class VertexCache {

    private float[] vertices = new float[64];
    private int size;
    private int vertexInQuad = 0;

    public int size() {
        return this.size;
    }

    public void reset() {
        vertices = new float[64];
        size = 0;
        vertexInQuad = 0;
    }

    public void addVertexWithUV(float x, float y, float z, float u, float v, float r, float g, float b, float nX,
        float nY, float nZ) {
        extendCache(11);
        int s = size;
        vertices[s] = x;
        vertices[s + 1] = y;
        vertices[s + 2] = z;
        vertices[s + 3] = u;
        vertices[s + 4] = v;
        vertices[s + 5] = r;
        vertices[s + 6] = g;
        vertices[s + 7] = b;
        vertices[s + 8] = nX;
        vertices[s + 9] = nY;
        vertices[s + 10] = nZ;
        size = s + 11;

        vertexInQuad++;

        if (vertexInQuad == 4) {
            vertexInQuad = 0;
        }
    }

    private void extendCache(int additional) {
        if (size + additional > vertices.length) {
            int newCapacity = Math.max(vertices.length * 2, size + additional);
            float[] newVertices = new float[newCapacity];
            System.arraycopy(vertices, 0, newVertices, 0, size);
            vertices = newVertices;
        }
    }

    public void render(Tessellator tess, int x, int y, int z, double offsetX0, double offsetY0, double offsetZ0) {
        if (vertexInQuad > 0) {
            throw new IllegalStateException("Incomplete quad detected before render!");
        }

        int stride = 11;
        int quadSize = stride * 4;

        int safeSize = size - (size % quadSize);

        for (int s = 0; s < safeSize; s += quadSize) {

            for (int i = 0; i < 4; i++) {
                int idx = s + i * stride;

                float vX = vertices[idx];
                float vY = vertices[idx + 1];
                float vZ = vertices[idx + 2];
                float u = vertices[idx + 3];
                float v = vertices[idx + 4];
                float r = vertices[idx + 5];
                float g = vertices[idx + 6];
                float b = vertices[idx + 7];
                float nX = vertices[idx + 8];
                float nY = vertices[idx + 9];
                float nZ = vertices[idx + 10];

                // TODO: Use a VBO (might be weird to do)
                tess.setNormal(nX, nY, nZ);
                tess.setColorOpaque_F(r, g, b);

                tess.addVertexWithUV(vX + x + offsetX0, vY + y + offsetY0, vZ + z + offsetZ0, u, v);
            }
        }
    }
}
