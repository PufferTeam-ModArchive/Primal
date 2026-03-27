package net.pufferlab.primal.items;

public interface IPrimalItem {

    default boolean hideItem() {
        return false;
    }
}
