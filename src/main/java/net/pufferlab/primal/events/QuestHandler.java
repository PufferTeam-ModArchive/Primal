package net.pufferlab.primal.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.utils.*;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class QuestHandler implements IEventHandler {

    public int tick;
    public static final int tickWait = 20;

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        tick++;
        if (tick > tickWait) {
            tick = 0;
            for (EntityPlayer player : Primal.proxy.getPlayerList()) {
                checkQuestStatus(player);
            }
        }
    }

    public void checkQuestStatus(EntityPlayer player) {
        PlayerData data = PlayerData.get(player);
        List<QuestType> questsToUnlock = new ArrayList<>(data.getQuestsToUnlock());
        for (QuestType quest : questsToUnlock) {
            QuestRequirement requirement = quest.getRequirement();
            if (requirement instanceof QuestRequirement.ItemRequirement req) {
                if (req.hasRequirements(player)) {
                    req.unlock(player);
                }
            }
        }
    }

    @Override
    public boolean isFMLEvent() {
        return true;
    }
}
