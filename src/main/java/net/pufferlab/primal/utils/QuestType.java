package net.pufferlab.primal.utils;

import static net.pufferlab.primal.utils.ItemUtils.getItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.pufferlab.primal.client.gui.GuiQuest;
import net.pufferlab.primal.entities.player.PlayerData;

public class QuestType {

    // What this advancement requires
    private final List<QuestType> dependencies = new ArrayList<>();
    // What advancements requires this
    private final List<QuestType> dependants = new ArrayList<>();

    private String name;
    public ItemStack stack;
    public int x;
    public int y;

    public QuestType(int x, int y, String name, ItemStack stack) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.stack = stack;
    }

    public QuestType addDependency(QuestType questType) {
        questType.dependants.add(this);
        this.dependencies.add(questType);
        return this;
    }

    public List<QuestType> getDependencies() {
        return this.dependencies;
    }

    public static final List<QuestType> questList = new ArrayList<>();
    public static final List<String> questNameList = new ArrayList<>();
    public static final Map<String, QuestType> questMap = new HashMap<>();

    public static final QuestType adv1 = new QuestType(0, 0, "adv1", getItem("minecraft:stone"));
    public static final QuestType adv2 = new QuestType(5, 5, "adv2", getItem("minecraft:dirt")).addDependency(adv1);
    public static final QuestType adv3 = new QuestType(-3, 2, "adv3", getItem("minecraft:diamond")).addDependency(adv2);

    public static void registerQuest(QuestType quest) {
        // GUI Quests
        GuiQuest.quests.add(quest);
        // Actual Quests
        questList.add(quest);
        questNameList.add(quest.name);
        questMap.put(quest.name, quest);
    }

    public boolean isUnlocked(EntityPlayer player) {
        PlayerData data = PlayerData.get(player);
        List<String> questData = data.getCompletedQuests();
        return questData.contains(this.name);
    }

    public static QuestType getQuest(String string) {
        return questMap.get(string);
    }

    public static void unlockQuest(EntityPlayer player, QuestType questType) {
        PlayerData data = PlayerData.get(player);
        List<String> questData = data.getCompletedQuests();
        questData.add(questType.name);
        data.updateClient();
        GuiQuest.updateRendering();
    }

    public static void lockQuest(EntityPlayer player, QuestType questType) {
        PlayerData data = PlayerData.get(player);
        List<String> questData = data.getCompletedQuests();
        questData.remove(questType.name);
        data.updateClient();
        GuiQuest.updateRendering();
    }

}
