package net.pufferlab.primal.events;

import java.util.Objects;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MobDropHandler implements IEventHandler {

    @SubscribeEvent
    public void onLivingDrop(LivingDropsEvent event) {
        if (Config.leatherDropReplacement.getBoolean()) {
            boolean hasLeatherDrop = false;
            for (int i = 0; i < event.drops.size(); i++) {
                EntityItem entityItem = event.drops.get(i);
                if (entityItem.getEntityItem() != null) {
                    if (entityItem.getEntityItem()
                        .getItem() == Items.leather) {
                        event.drops.set(
                            i,
                            addNewEntityItem(
                                event,
                                ItemUtils.getModItem("hide", 1),
                                entityItem.getEntityItem().stackSize));
                        hasLeatherDrop = true;
                    }
                    if (Mods.efr.isLoaded()) {
                        if (Utils.containsOreDict(entityItem.getEntityItem(), "rabbitHide")) {
                            event.drops.set(
                                i,
                                addNewEntityItem(
                                    event,
                                    ItemUtils.getModItem("hide", 1),
                                    entityItem.getEntityItem().stackSize));
                        }
                    }
                }
            }
            if (!hasLeatherDrop && event.entityLiving instanceof EntityCow) {
                event.drops.add(addNewEntityItem(event, ItemUtils.getModItem("hide", 1), 1));
            }
            if (event.entityLiving instanceof EntityPig || event.entityLiving instanceof EntitySheep) {
                event.drops.add(addNewEntityItem(event, ItemUtils.getModItem("hide", 1), 1));
            }
        }
    }

    public EntityItem addNewEntityItem(LivingDropsEvent event, ItemStack item, int amount) {
        item = Objects.requireNonNull(item);
        item.stackSize = amount;
        return new EntityItem(
            event.entityLiving.worldObj,
            event.entityLiving.posX,
            event.entityLiving.posY,
            event.entityLiving.posZ,
            item.copy());
    }
}
