package net.pufferlab.primal.client.helper;

public class VertexModifier {

    public static final VertexModifier blank = new VertexModifier();

    public float getR(float r) {
        return r;
    }

    public float getG(float g) {
        return g;
    }

    public float getB(float b) {
        return b;
    }

    public static class ColorMultiplier extends VertexModifier {

        private final VertexModifier mod;
        private final float multiplier;

        public ColorMultiplier(float multiplier) {
            this.mod = null;
            this.multiplier = multiplier;
        }

        public ColorMultiplier(VertexModifier mod, float multiplier) {
            this.mod = mod;
            this.multiplier = multiplier;
        }

        @Override
        public float getR(float r) {
            if (mod != null) {
                r = mod.getR(r);
            }
            return r * multiplier;
        }

        @Override
        public float getG(float g) {
            if (mod != null) {
                g = mod.getG(g);
            }
            return g * multiplier;
        }

        @Override
        public float getB(float b) {
            if (mod != null) {
                b = mod.getG(b);
            }
            return b * multiplier;
        }
    }

}
