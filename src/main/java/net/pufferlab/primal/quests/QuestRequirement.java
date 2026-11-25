package net.pufferlab.primal.quests;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.Utils;

public class QuestRequirement {

    public final QuestType quest;

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

        public ItemRequirement(QuestType quest, ItemStack stack) {
            super(quest);
            this.stack = stack;
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

    public static class ItemListRequirement extends QuestRequirement {

        public List<ItemStack> stack;

        public ItemListRequirement(QuestType quest, List<ItemStack> stack) {
            super(quest);
            this.stack = stack;
        }

        @Override
        public boolean hasRequirements(EntityPlayer player) {
            for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                ItemStack stack = player.inventory.mainInventory[i];
                if (stack == null) continue;

                if (Utils.containsStack(stack, this.stack)) return true;
            }
            return false;
        }
    }

    public static class BlockRequirement extends QuestRequirement {

        public Block block;
        public int meta;

        public BlockRequirement(QuestType quest, Block block, int meta) {
            super(quest);
            this.block = block;
            this.meta = meta;
        }

        @Override
        public boolean hasRequirements(EntityPlayer player) {
            MovingObjectPosition mop = BlockUtils.getMovingObjectPositionFromPlayer(player.worldObj, player, false);
            if (mop == null) return false;
            int x = mop.blockX;
            int y = mop.blockY;
            int z = mop.blockZ;
            Block block = player.worldObj.getBlock(x, y, z);
            if (block != this.block) return false;
            int meta = player.worldObj.getBlockMetadata(x, y, z);
            if (meta != this.meta) return false;
            return true;
        }
    }
}
