package net.pufferlab.primal.events;

import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.ItemKnifePrimitive;
import net.pufferlab.primal.tileentities.TileEntityInventory;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ToolHandler {

    @SubscribeEvent
    public void setBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack heldItem = event.entityPlayer.inventory.getCurrentItem();

        if (Utils.isLogBlock(event.block)) {
            if (heldItem != null) {
                if (!Utils.isAxeTool(heldItem)) {
                    event.setCanceled(true);
                }
            } else {
                event.setCanceled(true);
            }
        }

        if (Utils.isSoilBlock(event.block, event.metadata)) {
            if (heldItem != null) {
                if (!Utils.isShovelTool(heldItem)) {
                    event.newSpeed = event.originalSpeed / 2;
                }
            } else {
                event.newSpeed = event.originalSpeed / 2;
            }
        }

    }

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (Config.vanillaToolsRemovalMode == 2) {
            if (Utils.isBrokenTool(event.itemStack)) {
                event.toolTip.add("§cThis tool is too weak to be used!");
            }
        }
        if (Config.vanillaToolsRemovalMode == 1) {
            if (Utils.isBrokenTool(event.itemStack)) {
                event.toolTip.add("§cThis tool cannot be crafted!");
            }
        }
    }

    @SubscribeEvent
    public void attackEntityEvent(AttackEntityEvent event) {
        if (Config.vanillaToolsRemovalMode == 2) {
            ItemStack heldItem = event.entityPlayer.getHeldItem();
            if (Utils.isBrokenTool(heldItem)) {
                event.entityPlayer.destroyCurrentEquippedItem();
            }
        }

    }

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (Config.vanillaToolsRemovalMode == 2) {
            if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
                ItemStack heldItem = event.entityPlayer.getHeldItem();
                if (Utils.isBrokenTool(heldItem)) {
                    event.entityPlayer.destroyCurrentEquippedItem();
                }
            }
        }

        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) {
            ItemStack heldItem = event.entityPlayer.getHeldItem();
            if (Utils.isLighter(heldItem)) {
                TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
                if (te instanceof TileEntityInventory tef) {
                    if (tef.canBeFired()) {
                        tef.setFired(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void harvestDropsEvent(BlockEvent.HarvestDropsEvent event) {
        int chance = (int) Math.floor(1 / Config.stickDropChance);

        if (event.block instanceof BlockLeavesBase) {
            if (event.world.rand.nextInt(chance) == 0) {
                event.drops.add(new ItemStack(Items.stick, 1, 0));
            }
        }

        if (event.harvester != null) {
            ItemStack heldItem = event.harvester.getHeldItem();
            if (Config.vanillaToolsRemovalMode == 2) {
                if (Utils.isBrokenTool(heldItem)) {
                    event.drops.clear();
                    event.harvester.destroyCurrentEquippedItem();
                }
            }

            if (event.block instanceof BlockTallGrass) {
                if (event.harvester.getCurrentEquippedItem() != null) {
                    if (event.harvester.getCurrentEquippedItem()
                        .getItem() != null) {
                        if (event.harvester.getCurrentEquippedItem()
                            .getItem() instanceof ItemKnifePrimitive) {
                            if (event.world.rand.nextInt(3) == 0) {
                                event.drops.add(
                                    Utils.getModItem("straw", 1)
                                        .copy());
                            }
                        }
                    }
                }
            }
        }

    }
}
