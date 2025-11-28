package net.pufferlab.primal.events;

import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.ItemKnifePrimitive;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ToolHandler {

    @SubscribeEvent
    public void setBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack heldItem = event.entityPlayer.inventory.getCurrentItem();

        if (Utils.containsOreDict(event.block, "logWood") || event.block instanceof BlockLog) {
            if (heldItem != null) {
                if (!(heldItem.getItem() instanceof ItemAxe)) {
                    event.setCanceled(true);
                }
            } else {
                event.setCanceled(true);
            }
        }

        if (event.block.getHarvestTool(event.metadata) == "shovel") {
            if (heldItem != null) {
                if (!(heldItem.getItem() instanceof ItemSpade)) {
                    event.newSpeed = event.originalSpeed / 2;
                }
            } else {
                event.newSpeed = event.originalSpeed / 2;
            }
        }

    }

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (Utils.containsOreDict(event.itemStack, "toolBroken")) {
            event.toolTip.add("§cThis tool is too weak to be used!");
        }
    }

    @SubscribeEvent
    public void attackEntityEvent(AttackEntityEvent event) {
        ItemStack heldItem = event.entityPlayer.getHeldItem();
        if (Utils.containsOreDict(heldItem, "toolBroken")) {
            event.entityPlayer.destroyCurrentEquippedItem();
        }
    }

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        ItemStack heldItem = event.entityPlayer.getHeldItem();
        if (Utils.containsOreDict(heldItem, "toolBroken")) {
            event.entityPlayer.destroyCurrentEquippedItem();
        }
    }

    @SubscribeEvent
    public void harvestDropsEvent(BlockEvent.HarvestDropsEvent event) {
        if (event.block instanceof BlockLeavesBase) {
            if (event.world.rand.nextInt(6) == 0) {
                event.drops.add(new ItemStack(Items.stick, 1, 0));
            }
        }

        if (event.harvester != null) {
            ItemStack heldItem = event.harvester.getHeldItem();
            if (Utils.containsOreDict(heldItem, "toolBroken")) {
                event.drops.clear();
                event.harvester.destroyCurrentEquippedItem();
            }

            if (event.block instanceof BlockTallGrass) {
                if (event.harvester.getCurrentEquippedItem() != null) {
                    if (event.harvester.getCurrentEquippedItem()
                        .getItem() != null) {
                        if (event.harvester.getCurrentEquippedItem()
                            .getItem() instanceof ItemKnifePrimitive) {
                            if (event.world.rand.nextInt(3) == 0) {
                                event.drops.add(
                                    Utils.getModItem("misc", "item", "straw", 1)
                                        .copy());
                            }
                        }
                    }
                }
            }
        }

    }
}
