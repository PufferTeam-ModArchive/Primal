package net.pufferlab.primal.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.quests.QuestRequirement;
import net.pufferlab.primal.quests.QuestType;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class QuestHandler implements IEventHandler {

    public int tick;
    public static final int tickWait = 20;

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (event.world.isRemote) return;
        if (event.world.provider.dimensionId != 0) return;
        tick++;
        if (tick > tickWait) {
            tick = 0;
            for (EntityPlayer player : Primal.proxy.getPlayerList()) {
                checkRequirements(player);
            }
        }
    }

    public void checkRequirements(EntityPlayer player) {
        PlayerData data = PlayerData.get(player);
        List<QuestType> questsToUnlock = new ArrayList<>(data.getQuestsToUnlock());
        for (QuestType quest : questsToUnlock) {
            QuestRequirement req = quest.getRequirement();
            if (req == null) continue;
            if (req.hasRequirements(player)) {
                req.unlock(player);
            }
        }
    }

    @Override
    public boolean isFMLEvent() {
        return true;
    }
}
