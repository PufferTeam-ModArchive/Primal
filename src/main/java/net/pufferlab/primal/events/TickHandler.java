package net.pufferlab.primal.events;

import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.helper.ClientTickHolder;
import net.pufferlab.primal.network.packets.PacketWorldTime;
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

    public int timer;

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!event.world.isRemote) {
                if (event.world.provider.dimensionId == 0) {
                    GlobalTickingData.tick();
                    timer++;
                }
                SchedulerData.tickTasks(GlobalTickingData.getTickTime(event.world), event.world);
            }
            if (timer++ > 3) {
                syncTime(event.world);
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

    public void syncTime(World world) {
        long tickTime = GlobalTickingData.getTickTime(world);
        updatePacket(world, tickTime);
    }

    public void updatePacket(World world, long tickTime) {
        if (!world.isRemote) {
            Primal.proxy.sendPacketToClient(new PacketWorldTime(tickTime));
        }
    }

    @Override
    public boolean isFMLEvent() {
        return true;
    }
}
