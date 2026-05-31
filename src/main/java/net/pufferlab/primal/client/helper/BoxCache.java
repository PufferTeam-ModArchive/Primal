package net.pufferlab.primal.client.helper;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.AxisAlignedBB;
import net.pufferlab.primal.client.utils.ModelRenderer;

import org.jetbrains.annotations.NotNull;

public class BoxCache {

    private AxisAlignedBB[] boxes = new AxisAlignedBB[64];
    private int size;

    public void addBox(AxisAlignedBB bb) {
        extendCache(1);
        int s = size;
        boxes[s] = bb;
        size = s + 1;
    }

    private void extendCache(int additional) {
        if (size + additional > boxes.length) {
            int newCapacity = Math.max(boxes.length * 2, size + additional);
            AxisAlignedBB[] newVertices = new AxisAlignedBB[newCapacity];
            System.arraycopy(boxes, 0, newVertices, 0, size);
            boxes = newVertices;
        }
    }

    public AxisAlignedBB[] getList() {
        return this.boxes;
    }

    public static class BoxList extends AbstractList<AxisAlignedBB> {

        private final AxisAlignedBB[] array;
        int length = 0;
        public ModelRenderer modelRenderer;

        public BoxList(List<AxisAlignedBB> bblist, ModelRenderer renderer) {
            this.array = bblist.toArray(new AxisAlignedBB[0]);
            for (AxisAlignedBB bb : array) {
                if (bb != null) {
                    length++;
                }
            }
            this.modelRenderer = renderer;
        }

        public BoxList setRenderer(ModelRenderer renderer) {
            this.modelRenderer = renderer;
            return this;
        }

        @Override
        public AxisAlignedBB get(int index) {
            return array[index];
        }

        @Override
        public AxisAlignedBB set(int index, AxisAlignedBB value) {
            AxisAlignedBB old = array[index];
            array[index] = value;
            return old;
        }

        @Override
        public Object[] toArray() {
            return this.array;
        }

        @NotNull
        @Override
        public <T> T[] toArray(@NotNull T[] a) {
            return (T[]) Arrays.copyOf(this.array, this.array.length, a.getClass());
        }

        @Override
        public int size() {
            return length;
        }
    }
}
