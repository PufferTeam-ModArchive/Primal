package net.pufferlab.primal.events;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
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
    public void harvestDropsEvent(BlockEvent.HarvestDropsEvent event) {
        if (event.block instanceof BlockLeavesBase) {
            if (event.world.rand.nextInt(6) == 0) {
                event.drops.add(new ItemStack(Items.stick, 1, 0));
            }
        }

        if (event.block instanceof BlockBush) {
            if (event.harvester != null) {
                if (event.harvester.getCurrentEquippedItem() != null) {
                    if (event.harvester.getCurrentEquippedItem()
                        .getItem() != null) {
                        if (event.harvester.getCurrentEquippedItem()
                            .getItem() instanceof ItemKnifePrimitive) {
                            if (event.world.rand.nextInt(5) == 0) {
                                event.drops.add(
                                    new ItemStack(
                                        Registry.item,
                                        1,
                                        Utils.getItemFromArray(Constants.miscItems, "straw")));
                            }
                        }
                    }
                }
            }
        }

    }
}
