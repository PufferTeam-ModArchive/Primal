package net.pufferlab.primal.client.utils;

import net.pufferlab.primal.blocks.IPrimalBlock;

public class RenderState {

    private static final ThreadLocal<RenderState> state = ThreadLocal.withInitial(RenderState::new);
    private static final int amount = 3;

    boolean[] isInventory = new boolean[amount];
    int[] renderPass = new int[amount];
    int[] renderPassWorld = new int[amount];

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

    public static int getStateID(IPrimalBlock block) {
        int id = block.getStateID();
        if (id >= amount) id = 0;
        return id;
    }

    public static void setPass(IPrimalBlock block, int pass) {
        int passID = getStateID(block);
        RenderState render = getInstance();
        if (render.isInventory[passID]) {
            render.renderPass[passID] = pass;
        } else {
            render.renderPassWorld[passID] = pass;
        }
    }

    public static int getPass(IPrimalBlock block) {
        int passID = getStateID(block);
        RenderState render = getInstance();
        if (render.isInventory[passID]) {
            return render.renderPass[passID];
        } else {
            return render.renderPassWorld[passID];
        }
    }

}
