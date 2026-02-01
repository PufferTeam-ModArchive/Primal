package net.pufferlab.primal.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.pufferlab.primal.blocks.BlockStoneOreThaumcraft;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fox.spiteful.forbidden.Config;
import fox.spiteful.forbidden.enchantments.DarkEnchantments;
import fox.spiteful.forbidden.items.ForbiddenItems;

public class ForbiddenMagicHandler {

    @SubscribeEvent
    public void onHarvest(BlockEvent.HarvestDropsEvent event) {
        EntityPlayer player = event.harvester;
        if (player != null && player.inventory.getCurrentItem() != null) {
            Random randy = player.worldObj.rand;
            ItemStack equip = player.inventory.getCurrentItem();
            if (EnchantmentHelper.getEnchantmentLevel(DarkEnchantments.corrupting.effectId, equip) > 0
                && event.block instanceof BlockStoneOreThaumcraft
                && randy.nextInt(3) == 1) {
                ArrayList<ItemStack> moarDrops = new ArrayList<ItemStack>();
                ArrayList<ItemStack> deadDrops = new ArrayList<ItemStack>();
                Iterator current = event.drops.iterator();
                while (current.hasNext()) {
                    ItemStack curDrop = (ItemStack) current.next();
                    if (curDrop != null && curDrop.getItem() == Config.thaumcraftShard.getItem()) {
                        int x = curDrop.stackSize;
                        for (int i = 0; i < x; i++) {
                            moarDrops.add(getSinShard(randy));
                        }
                        deadDrops.add(curDrop);
                    }
                }
                current = deadDrops.iterator();
                while (current.hasNext()) {
                    event.drops.remove((ItemStack) (current.next()));
                }
                current = moarDrops.iterator();
                while (current.hasNext()) {
                    event.drops.add((ItemStack) (current.next()));
                }
            }
        }
    }

    public ItemStack getSinShard(Random randy) {
        int x = randy.nextInt(7);
        switch (x) {
            case 0:
            case 1:
            case 3:
            case 5:
            case 6:
                return new ItemStack(ForbiddenItems.deadlyShards, 1, x);
            case 2:
                return new ItemStack(ForbiddenItems.gluttonyShard, 1, 0);
            case 4:
                if (Config.noLust) return getSinShard(randy);
                else return new ItemStack(ForbiddenItems.deadlyShards, 1, 4);
        }
        return null;
    }
}
