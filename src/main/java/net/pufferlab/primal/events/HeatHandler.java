package net.pufferlab.primal.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.events.ticks.GlobalTickingData;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.utils.TemperatureUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class HeatHandler implements IEventHandler {

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (event.itemStack.hasTagCompound()) {
            NBTTagCompound tag = event.itemStack.getTagCompound();
            if (tag != null) {
                if (TemperatureUtils.hasImpl(event.itemStack)) {
                    event.toolTip.add(
                        Utils.getTemperatureTooltip(
                            TemperatureUtils.getInterpolatedTemperature(
                                GlobalTickingData.getTickTime(event.entity.worldObj),
                                tag)));
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.entityLiving;

        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            ItemStack stack = player.inventory.mainInventory[i];
            if (stack == null) continue;

            IHeatableItem impl = TemperatureUtils.getImpl(stack);
            if (impl != null) {
                impl.onUpdateHeat(stack, player.worldObj);
            }
        }
    }

    @SubscribeEvent
    public void onEntityItemUpdate(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        for (Object obj : event.world.loadedEntityList) {
            if (!(obj instanceof EntityItem)) continue;
            EntityItem ei = (EntityItem) obj;
            ItemStack stack = ei.getEntityItem();

            IHeatableItem impl = TemperatureUtils.getImpl(stack);
            if (impl != null) {
                impl.onUpdateHeat(stack, event.world);
            }
        }
    }
}
