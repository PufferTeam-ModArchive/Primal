package net.pufferlab.primal.client.utils;

public class RenderState {

    public static final ThreadLocal<RenderState> state = ThreadLocal.withInitial(RenderState::new);

    boolean isInventory;
    int renderPass;
    int renderPassWorld;

    public static RenderState getInstance() {
        return state.get();
    }

    public static void setInventory(boolean isInventory) {
        getInstance().isInventory = isInventory;
    }

    public static boolean isInventory() {
        return getInstance().isInventory;
    }

    public static void setPass(int pass) {
        RenderState render = getInstance();
        if (render.isInventory) {
            render.renderPass = pass;
        } else {
            render.renderPassWorld = pass;
        }
    }

    public static int getPass() {
        RenderState render = getInstance();
        if (render.isInventory) {
            return render.renderPass;
        } else {
            return render.renderPassWorld;
        }
    }

}
