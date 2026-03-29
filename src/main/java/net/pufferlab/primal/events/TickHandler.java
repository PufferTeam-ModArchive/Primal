package net.pufferlab.primal.events;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.helper.ClientTickHolder;
import net.pufferlab.primal.world.GlobalTickingData;
import net.pufferlab.primal.world.SchedulerData;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TickHandler implements IEventHandler {

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!isGameActive()) return;
        ClientTickHolder.tick();
    }

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        tick(event);
        tickTasks(event);
    }

    private synchronized void tick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!event.world.isRemote) {
                if (Primal.proxy.isOverworld(event.world)) {
                    GlobalTickingData.tick();
                }
            }
        } else {
            if (!event.world.isRemote) {
                GlobalTickingData.tickClient();
            }
        }
    }

    private synchronized void tickTasks(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!event.world.isRemote) {
                SchedulerData.tickTasks(GlobalTickingData.getTickTime(event.world), event.world);
                SchedulerData.tickWaitingTasks(event.world);
            }
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {

    }

    @SubscribeEvent
    public void onJoin(EntityJoinWorldEvent event) {}

    @SubscribeEvent
    public void onLoadWorld(WorldEvent.Load event) {
        if (event.world.isRemote) {
            ClientTickHolder.reset();
        }
    }

    @SubscribeEvent
    public void onUnloadWorld(WorldEvent.Unload event) {
        if (event.world.isRemote) {
            ClientTickHolder.reset();
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
