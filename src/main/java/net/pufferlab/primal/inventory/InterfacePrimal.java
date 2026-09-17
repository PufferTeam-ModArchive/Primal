package net.pufferlab.primal.inventory;

import net.pufferlab.primal.utils.IIdentifiable;

public abstract class InterfacePrimal implements IInterfaceHandler, IIdentifiable {

    @Override
    public String getIdentifierType() {
        return "interface";
    }
}
