package net.pufferlab.primal.compat.baubles;

import net.pufferlab.primal.Constants;

import baubles.api.expanded.BaubleExpandedSlots;

public class BACompat {

    public void loadConfig() {
        registerBaubles();
    }

    public void registerBaubles() {
        registerBauble(Constants.hat);
        registerBauble(Constants.shirt);
        registerBauble(Constants.pants);
        registerBauble(Constants.shoes);
    }

    public void registerBauble(String name) {
        BaubleExpandedSlots.tryRegisterType(name);
        BaubleExpandedSlots.tryAssignSlotOfType(name);
    }
}
