package net.pufferlab.primal.items;

public interface IPrimalItem {

    default boolean hideItem() {
        return false;
    }

    default boolean canRegister() {
        return true;
    }
}
