package net.pufferlab.primal.quests;

import net.minecraft.entity.player.EntityPlayer;

public class QuestReward {

    public final QuestType quest;

    public QuestReward(QuestType quest) {
        this.quest = quest;
    }

    public void giveReward(EntityPlayer player) {}

    public static class XPReward extends QuestReward {

        public int xp;

        public XPReward(QuestType quest, int xpPoint) {
            super(quest);
            this.xp = xpPoint;
        }

        public void giveReward(EntityPlayer player) {
            player.worldObj.playSoundAtEntity(
                player,
                "random.orb",
                0.1F,
                0.5F * ((player.worldObj.rand.nextFloat() - player.worldObj.rand.nextFloat()) * 0.7F + 1.8F));
            player.addExperience(xp);
        }
    }
}
