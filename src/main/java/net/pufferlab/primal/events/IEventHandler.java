package net.pufferlab.primal.events;

public interface IEventHandler {

    default boolean isFMLEvent() {
        return false;
    };
}
