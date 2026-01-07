package net.pufferlab.primal.events;

import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.helper.TickHolder;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandler implements IEventHandler {

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!isGameActive()) return;
        TickHolder.tick();
    }

    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {}

    @SubscribeEvent
    public void onLoadWorld(WorldEvent.Load event) {
        World world = event.world;
        if (world.isRemote) {
            TickHolder.reset();
        }
    }

    @SubscribeEvent
    public void onUnloadWorld(WorldEvent.Unload event) {
        if (event.world.isRemote) {
            TickHolder.reset();
        }
    }

    protected boolean isGameActive() {
        return !(Primal.proxy.getClientWorld() == null || Primal.proxy.getClientPlayer() == null);
    }

    @Override
    public boolean isFMLEvent() {
        return true;
    }
}
