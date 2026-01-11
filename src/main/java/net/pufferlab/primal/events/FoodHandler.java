package net.pufferlab.primal.events;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityInteractEvent;
import net.pufferlab.primal.items.ItemMetaFood;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FoodHandler implements IEventHandler {

    @SubscribeEvent
    public void onEntityInteraction(EntityInteractEvent event) {
        if (!(event.target instanceof EntityWolf)) return;
        if (!((EntityWolf) event.target).isTamed()) return;
        if (event.entityPlayer.getHeldItem() == null || !(event.entityPlayer.getHeldItem()
            .getItem() instanceof ItemMetaFood)) return;

        int damage = event.entityPlayer.getHeldItem()
            .getItemDamage();
        if (!((ItemMetaFood) event.entityPlayer.getHeldItem()
            .getItem()).isMeat(damage)) return;
        if (((EntityWolf) event.target).getHealth() >= ((EntityWolf) event.target).getMaxHealth()) {
            // breed
        } else {
            ((EntityWolf) event.target).heal(
                ((ItemMetaFood) event.entityPlayer.getHeldItem()
                    .getItem()).func_150905_g(event.entityPlayer.getHeldItem()));
            if (!event.entityPlayer.capabilities.isCreativeMode)
                if (event.entityPlayer.getHeldItem().stackSize-- <= 0) event.entityPlayer.inventory
                    .setInventorySlotContents(event.entityPlayer.inventory.currentItem, (ItemStack) null);
        }
        event.setCanceled(true);
    }
}
