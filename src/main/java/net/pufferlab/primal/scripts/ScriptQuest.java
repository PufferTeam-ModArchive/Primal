package net.pufferlab.primal.scripts;

import static net.pufferlab.primal.utils.ItemUtils.getItem;

import net.pufferlab.primal.Registry;
import net.pufferlab.primal.quests.QuestType;

public class ScriptQuest implements IScript {

    public void run() {
        QuestType flint = addQuest(0, 0, "flint", getItem("minecraft:flint:0:1"));

        QuestType flint_axe = addQuest(-2, -1, "flint_axe", getModItem("flint_axe", 1)).addDependency(flint);
        QuestType tree = addQuest(-2, -3, "tree", getItem("minecraft:log:0:1"), "logWood").addDependency(flint_axe);
        QuestType firewood = addQuest(-2, -5, "firewood", getModItem("firewood", 1)).addDependency(tree);

        QuestType flint_knife = addQuest(2, -1, "flint_knife", getModItem("flint_knife", 1), "toolKnife")
            .addDependency(flint);
        QuestType straw = addQuest(2, -3, "straw", getModItem("straw", 1)).addDependency(flint_knife);

        QuestType clay = addQuest(4, -3, "clay", getItem("minecraft:clay_ball:0:1"));

        QuestType pit_kiln = addQuest(2, -5, "pit_kiln", getModItem("pit_kiln", 1), Registry.pit_kiln, 8)
            .addDependency(firewood)
            .addDependency(straw)
            .addDependency(clay);
        QuestType crucible = addQuest(2, -7, "crucible", getModItem("crucible", 1)).addDependency(pit_kiln);

        QuestType charcoal = addQuest(-3, -7, "charcoal", getItem("minecraft:coal:1:1"), "charcoal")
            .addDependency(firewood);
        QuestType forge = addQuest(-3, -9, "forge", getModItem("forge", 1)).addDependency(charcoal);

        QuestType campfire = addQuest(-4, -5, "campfire", getModItem("campfire", 1), Registry.campfire, 5)
            .addDependency(firewood);
    }
}
