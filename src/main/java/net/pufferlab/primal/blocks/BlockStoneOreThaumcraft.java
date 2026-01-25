package net.pufferlab.primal.blocks;

import net.minecraft.item.Item;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.utils.OreType;
import net.pufferlab.primal.utils.StoneType;

public class BlockStoneOreThaumcraft extends BlockStoneOre {

    int color;

    public BlockStoneOreThaumcraft(StoneType[] materials, OreType type) {
        super(materials, type);
        this.color = type.color;
        this.setEmissive(type.color);
        this.setEmissiveTexture("tc_infused_ore");
    }

    public static void setupShards() {
        if (Mods.tc.isLoaded()) {
            Item shardItem = Mods.tc.getModItem("ItemShard");
            Constants.aer.setOreItem(shardItem, 0);
            Constants.ignis.setOreItem(shardItem, 1);
            Constants.aqua.setOreItem(shardItem, 2);
            Constants.terra.setOreItem(shardItem, 3);
            Constants.ordo.setOreItem(shardItem, 4);
            Constants.perditio.setOreItem(shardItem, 5);
        }
    }

    @Override
    public boolean isEmissive() {
        return true;
    }
}
