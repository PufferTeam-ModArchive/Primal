package net.pufferlab.primal.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.network.packets.PacketPlayerData;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerHandler implements IEventHandler {

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;

            if (player.getExtendedProperties(PlayerData.name) == null) {
                PlayerData.register(player);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        PlayerData oldData = PlayerData.get(event.original);
        PlayerData newData = PlayerData.get(event.entityPlayer);
        PlayerData.syncPlayerData(oldData, newData);

        updatePacket(event.entityPlayer, newData);
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerLoggedInEvent event) {
        EntityPlayer player = event.player;
        PlayerData data = PlayerData.get(player);
        if (data != null) {
            updatePacket(player, data);
        }
    }

    @SubscribeEvent
    public void onPlayerChangeDimension(PlayerChangedDimensionEvent event) {
        PlayerData data = PlayerData.get(event.player);
        if (data != null) {
            updatePacket(event.player, data);
        }
    }

    public void updatePacket(EntityPlayer player, PlayerData data) {
        if (!player.worldObj.isRemote) {
            Primal.proxy.sendPacketToClient(new PacketPlayerData(player, data));
        }
    }

    @Override
    public boolean isFMLEvent() {
        return true;
    }
}
