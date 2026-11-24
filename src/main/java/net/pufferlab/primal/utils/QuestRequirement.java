package net.pufferlab.primal.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class QuestRequirement {

    public QuestType quest;

    public QuestRequirement(QuestType quest) {
        this.quest = quest;
    }

    public boolean hasRequirements(EntityPlayer player) {
        return false;
    }

    public void unlock(EntityPlayer player) {
        QuestType.unlockQuest(player, quest);
    }

    public static class ItemRequirement extends QuestRequirement {

        public ItemStack stack;

        public ItemRequirement(QuestType quest) {
            super(quest);
            this.stack = quest.stack;
        }

        @Override
        public boolean hasRequirements(EntityPlayer player) {
            for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                ItemStack stack = player.inventory.mainInventory[i];
                if (stack == null) continue;

                if (Utils.equalsStack(stack, this.stack)) return true;
            }
            return false;
        }
    }
}
