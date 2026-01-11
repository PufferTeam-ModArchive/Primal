package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.items.ItemKnifePrimitive;
import net.pufferlab.primal.tileentities.IHeatable;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ToolHandler implements IEventHandler {

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
            if (heldItem != null) {
                if (heldItem.getItem() instanceof ItemFlintAndSteel) {
                    TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
                    if (te instanceof IHeatable tef) {
                        if (tef.canBeFired()) {
                            tef.setFired(true);
                        }
                    }
                }
            }

            if (Utils.isKnifeTool(heldItem) && Primal.EFRLoaded) {
                World world = event.world;
                Block block = world.getBlock(event.x, event.y, event.z);
                int meta = world.getBlockMetadata(event.x, event.y, event.z);
                Block target = null;

                ForgeDirection side = ForgeDirection.getOrientation(event.face);

                int offsetX = side.offsetX;
                int offsetY = side.offsetY;
                int offsetZ = side.offsetZ;
                if (block == Blocks.log) {
                    target = GameRegistry.findBlock("etfuturum", "log_stripped");
                } else if (block == Blocks.log2) {
                    target = GameRegistry.findBlock("etfuturum", "log2_stripped");
                }

                if (target != null) {
                    int woodType = meta & 3;
                    ItemStack droppedStack = null;
                    world.setBlock(event.x, event.y, event.z, target, meta, 2);
                    heldItem.damageItem(1, event.entityPlayer);
                    Utils.playSound(event.world, event.x, event.y, event.z, Blocks.log);

                    if (block == Blocks.log) {
                        droppedStack = new ItemStack(Registry.bark, 1, woodType);
                    } else {
                        droppedStack = new ItemStack(Registry.bark, 1, woodType + 4);
                    }

                    event.entityPlayer.swingItem();
                    if (!world.isRemote) {
                        EntityItem entityItem = new EntityItem(
                            world,
                            event.x + 0.5 + offsetX,
                            event.y + 0.5 + offsetY,
                            event.z + 0.5 + offsetZ,
                            droppedStack);

                        entityItem.motionX = world.rand.nextGaussian() * 0.005D;
                        entityItem.motionY = 0.02D;
                        entityItem.motionZ = world.rand.nextGaussian() * 0.005D;
                        world.spawnEntityInWorld(entityItem);
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
