package net.pufferlab.primal.client.utils;

import net.pufferlab.primal.blocks.IPrimalBlock;

public class RenderState {

    public static final ThreadLocal<RenderState> state = ThreadLocal.withInitial(RenderState::new);

    boolean[] isInventory = new boolean[3];
    int[] renderPass = new int[3];
    int[] renderPassWorld = new int[3];

    public static RenderState getInstance() {
        return state.get();
    }

    public static void setInventory(IPrimalBlock block, boolean isInventory) {
        int passID = block.getStateID();
        getInstance().isInventory[passID] = isInventory;
    }

    public static boolean isInventory(IPrimalBlock block) {
        int passID = block.getStateID();
        return getInstance().isInventory[passID];
    }

    public static void setPass(IPrimalBlock block, int pass) {
        int passID = block.getStateID();
        RenderState render = getInstance();
        if (render.isInventory[passID]) {
            render.renderPass[passID] = pass;
        } else {
            render.renderPassWorld[passID] = pass;
        }
    }

    public static int getPass(IPrimalBlock block) {
        int passID = block.getStateID();
        RenderState render = getInstance();
        if (render.isInventory[passID]) {
            return render.renderPass[passID];
        } else {
            return render.renderPassWorld[passID];
        }
    }

}
