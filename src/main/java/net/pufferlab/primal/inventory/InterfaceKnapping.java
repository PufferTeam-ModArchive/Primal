package net.pufferlab.primal.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.gui.GuiKnapping;
import net.pufferlab.primal.recipes.KnappingType;

public class InterfaceKnapping extends InterfacePrimal {

    public KnappingType knappingType;

    public InterfaceKnapping(KnappingType knappingType) {
        this.knappingType = knappingType;
        Primal.proxy.register(this);
    }

    @Override
    public Object getNewContainer(EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerKnapping(this.knappingType, player.inventory);
    }

    @Override
    public Object getNewGui(EntityPlayer player, World world, int x, int y, int z) {
        return new GuiKnapping(this.knappingType, player.inventory);
    }

    @Override
    public String getIdentifier() {
        return this.knappingType.name + "_knapping";
    }
}
