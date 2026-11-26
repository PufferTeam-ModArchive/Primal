package net.pufferlab.primal.quests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.client.gui.GuiQuest;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.PosMap;
import net.pufferlab.primal.utils.TextUtils;

public class QuestType {

    // What this advancement requires
    private final List<QuestType> dependencies = new ArrayList<>();
    // What advancements requires this
    private final List<QuestType> dependants = new ArrayList<>();

    public static final String learnedQuestLangKey = "quest." + Primal.MODID + ".message.learned.info";
    public final String name;
    public final String nameLangKey;
    public final String descLangKey;
    public ItemStack stack;
    public int x;
    public int y;
    private QuestRequirement requirement;
    private QuestReward reward;

    public QuestType(int x, int y, String name) {
        this.name = name;
        this.nameLangKey = "quest." + Primal.MODID + "." + this.name + ".name";
        this.descLangKey = "quest." + Primal.MODID + "." + this.name + ".desc";
        this.x = x;
        this.y = y * -1;
    }

    public QuestType(int x, int y, String name, ItemStack stack) {
        this(x, y, name);
        this.stack = stack;
        this.requirement = new QuestRequirement.ItemRequirement(this, this.stack);
        this.reward = new QuestReward.XPReward(this, 5);
    }

    public QuestType(int x, int y, String name, ItemStack stack, String oreDict) {
        this(x, y, name);
        this.stack = stack;
        this.requirement = new QuestRequirement.ItemListRequirement(this, ItemUtils.getOres(oreDict));
        this.reward = new QuestReward.XPReward(this, 5);
    }

    public QuestType(int x, int y, String name, ItemStack stack, Block block, int meta) {
        this(x, y, name);
        this.stack = stack;
        this.requirement = new QuestRequirement.BlockRequirement(this, block, meta);
        this.reward = new QuestReward.XPReward(this, 5);
    }

    public QuestType addDependency(QuestType questType) {
        questType.dependants.add(this);
        this.dependencies.add(questType);
        return this;
    }

    public List<QuestType> getDependencies() {
        if (this.dependencies.isEmpty()) {
            Primal.debugLog(this.name + ": HAS NO DEPENDENCIES");
        }
        return this.dependencies;
    }

    public QuestRequirement getRequirement() {
        return this.requirement;
    }

    public QuestReward getReward() {
        return this.reward;
    }

    private static final List<QuestType> questList = new ArrayList<>();
    private static final List<String> questNameList = new ArrayList<>();
    private static final Map<String, QuestType> questMap = new HashMap<>();
    private static final PosMap.Single<QuestType> questPosMap = new PosMap.Single<>();

    public static List<QuestType> values() {
        return questList;
    }

    public static List<String> valuesName() {
        return questNameList;
    }

    public static QuestType addQuest(int x, int y, String name, ItemStack stack) {
        return addQuest(new QuestType(x, y, name, stack));
    }

    public static QuestType addQuest(int x, int y, String name, ItemStack stack, String oreDict) {
        return addQuest(new QuestType(x, y, name, stack, oreDict));
    }

    public static QuestType addQuest(int x, int y, String name, ItemStack stack, Block block, int meta) {
        return addQuest(new QuestType(x, y, name, stack, block, meta));
    }

    public static QuestType addQuest(QuestType quest) {
        // GUI Quests
        GuiQuest.quests.add(quest);
        // Actual Quests
        questList.add(quest);
        questNameList.add(quest.name);
        questMap.put(quest.name, quest);

        return quest;
    }

    public boolean canBeUnlocked(EntityPlayer player) {
        PlayerData data = PlayerData.get(player);
        return data.getQuestsToUnlock()
            .contains(this);
    }

    public boolean canBeUnlocked(PlayerData data) {
        if (this.isClaimed(data) || this.isUnlocked(data)) return false;

        List<QuestType> dependencies = getDependencies();
        for (QuestType dep : dependencies) {
            if (!dep.isUnlocked(data) && !dep.isClaimed(data)) return false;
        }
        return true;
    }

    public boolean isUnlocked(EntityPlayer player) {
        PlayerData data = PlayerData.get(player);
        return isUnlocked(data);
    }

    public boolean isUnlocked(PlayerData data) {
        List<QuestType> questData = data.getCompletedQuests();
        return questData.contains(this);
    }

    public boolean isClaimed(EntityPlayer player) {
        PlayerData data = PlayerData.get(player);
        return isClaimed(data);
    }

    public boolean isClaimed(PlayerData data) {
        List<QuestType> questData = data.getClaimedQuests();
        return questData.contains(this);
    }

    public static QuestType getQuest(String string) {
        return questMap.get(string);
    }

    public static void claimQuest(EntityPlayer player, QuestType questType) {
        PlayerData data = PlayerData.get(player);
        List<QuestType> questData = data.getClaimedQuests();
        List<QuestType> questData0 = data.getCompletedQuests();
        if (!questData.contains(questType) && questData0.contains(questType)) {
            questData0.remove(questType);

            questData.add(questType);

            data.updateClient();
            sendQuestUpdate(player);

            QuestReward reward = questType.getReward();
            reward.giveReward(player);
        }
    }

    public static void unlockQuest(EntityPlayer player, QuestType questType) {
        PlayerData data = PlayerData.get(player);
        List<QuestType> questData = data.getCompletedQuests();
        if (!questData.contains(questType)) {
            questData.add(questType);

            data.updateClient();
            sendQuestUpdate(player);

            sendUnlockMessage(player, questType);
        }
    }

    public static void sendUnlockMessage(EntityPlayer player, QuestType questType) {
        IChatComponent ichatcomponent = new ChatComponentTranslation(questType.nameLangKey);
        ichatcomponent.getChatStyle()
            .setColor(EnumChatFormatting.GREEN);
        IChatComponent ichatcomponent1 = (new ChatComponentText("[")).appendSibling(ichatcomponent)
            .appendText("]");
        ichatcomponent1.setChatStyle(ichatcomponent.getChatStyle());

        TextUtils.sendChatMessage(player, learnedQuestLangKey, player.getCommandSenderName(), ichatcomponent1);
    }

    public static void lockQuest(EntityPlayer player, QuestType questType) {
        PlayerData data = PlayerData.get(player);

        List<QuestType> questData = data.getCompletedQuests();
        questData.remove(questType);

        List<QuestType> questData2 = data.getClaimedQuests();
        questData2.remove(questType);

        data.updateClient();
        sendQuestUpdate(player);
    }

    public static void sendQuestUpdate(EntityPlayer player) {
        Primal.proxy.packet.sendQuestUpdate(player);
    }
}
