package net.pufferlab.primal.events;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Config;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.events.ticks.GlobalTickingData;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.items.MetalType;
import net.pufferlab.primal.tileentities.IHeatable;
import net.pufferlab.primal.utils.FluidUtils;
import net.pufferlab.primal.utils.TemperatureUtils;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class HeatHandler implements IEventHandler {

    public static final Item crucible = Item.getItemFromBlock(Registry.crucible);

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (event.itemStack.hasTagCompound()) {
            NBTTagCompound tag = event.itemStack.getTagCompound();
            if (tag != null) {
                if (TemperatureUtils.hasImpl(event.itemStack)) {
                    int temperature = TemperatureUtils
                        .getInterpolatedTemperature(GlobalTickingData.getTickTime(event.entity.worldObj), tag);
                    if (temperature > Config.temperatureCap.getInt()) {
                        event.toolTip.add(Utils.getTemperatureTooltip(temperature));
                    }
                    if (event.itemStack.getItem() == crucible) {
                        FluidStack stack = FluidUtils.getFluidTankFromNBT(event.itemStack.getTagCompound());
                        if (stack != null) {
                            MetalType metal = MetalType.getMetalFromFluid(Constants.metalTypes, stack);
                            if (metal != null) {
                                int meltingTemperature = metal.meltingTemperature;
                                if (temperature > meltingTemperature) {
                                    event.toolTip.add("Content is molten");
                                } else {
                                    event.toolTip.add("Content is solid");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static final Item unlit_torch = Item.getItemFromBlock(Registry.unlit_torch);
    public static final ItemStack lit_torch = new ItemStack(Registry.lit_torch, 1, 0);

    @SubscribeEvent
    public void playerInteractEventHandler(PlayerInteractEvent event) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && event.useBlock != Event.Result.DENY) return;
        ItemStack heldItem = event.entityPlayer.getHeldItem();
        if (heldItem == null) return;
        if (heldItem.getItem() == null) return;
        if (heldItem.getItem() == unlit_torch && !event.entityPlayer.isSneaking()) {
            if (event.world.isRemote) {
                event.useItem = Event.Result.DENY;
            }
            TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
            if (te instanceof IHeatable heat) {
                if (heat.isFired() && heat.canBeFired()) {
                    event.entityPlayer.getHeldItem().stackSize--;
                    event.entityPlayer.inventory.addItemStackToInventory(lit_torch.copy());
                    event.useBlock = Event.Result.DENY;
                    event.entityPlayer.inventoryContainer.detectAndSendChanges();
                }
            }
        }
        if (heldItem.getItem() instanceof ItemFlintAndSteel
            || heldItem.getItem() == Item.getItemFromBlock(Registry.lit_torch)) {
            TileEntity te = event.world.getTileEntity(event.x, event.y, event.z);
            if (te instanceof IHeatable tef) {
                if (tef.canBeFired()) {
                    tef.setFired(true);
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
