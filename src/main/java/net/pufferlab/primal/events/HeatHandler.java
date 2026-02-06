package net.pufferlab.primal.events;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
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
import net.pufferlab.primal.*;
import net.pufferlab.primal.entities.player.PlayerData;
import net.pufferlab.primal.items.IHeatableItem;
import net.pufferlab.primal.tileentities.IHeatable;
import net.pufferlab.primal.utils.FluidUtils;
import net.pufferlab.primal.utils.HeatUtils;
import net.pufferlab.primal.utils.MetalType;
import net.pufferlab.primal.world.GlobalTickingData;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class HeatHandler implements IEventHandler {

    public static final Item crucible = Item.getItemFromBlock(Registry.crucible);

    @SubscribeEvent
    public void tooltipEvent(ItemTooltipEvent event) {
        if (HeatUtils.hasImpl(event.itemStack)) {
            IHeatableItem impl = HeatUtils.getImpl(event.itemStack);
            MetalType metal0 = impl.getMetal(event.itemStack);
            int meltingTemperature0 = impl.getMeltingTemperature(event.itemStack);
            if (meltingTemperature0 > 0 && metal0 != null) {
                event.toolTip.add(
                    Utils.translate(
                        "metal." + Primal.MODID + ".melting.desc",
                        metal0.getTranslatedName(),
                        meltingTemperature0));
            }
        }
        if (event.itemStack.hasTagCompound()) {
            NBTTagCompound tag = event.itemStack.getTagCompound();
            if (tag != null) {
                if (HeatUtils.hasImpl(event.itemStack)) {
                    int temperature = HeatUtils
                        .getInterpolatedTemperature(GlobalTickingData.getTickTime(event.entity.worldObj), tag);
                    if (temperature > Config.temperatureCap.getInt()) {
                        event.toolTip.add(HeatUtils.getTemperatureTooltip(temperature));
                    }
                    if (event.itemStack.getItem() == crucible) {
                        FluidStack stack = FluidUtils.getFluidTankFromNBT(tag);
                        if (stack != null) {
                            MetalType metal = MetalType.getMetalFromFluid(stack);
                            if (metal != null) {
                                int meltingTemperature = metal.meltingTemperature;
                                if (temperature > meltingTemperature) {
                                    event.toolTip
                                        .add(Utils.translate("metal." + Primal.MODID + ".melting.liquid.name"));
                                } else {
                                    event.toolTip.add(Utils.translate("metal." + Primal.MODID + ".melting.solid.name"));
                                }
                            }
                        }
                    }
                    PlayerData data = PlayerData.get(event.entityPlayer);
                    if (data.temperatureDebug) {
                        event.toolTip.add("  ");
                        event.toolTip.add("Advanced Info :");
                        event.toolTip.add("Last-Temperature: " + HeatUtils.getTemperatureFromNBT(tag));
                        event.toolTip.add("Last-WorldTime: " + HeatUtils.getWorldTimeFromNBT(tag));
                        event.toolTip.add("Modifier: " + HeatUtils.getMultiplierFromNBT(tag));
                        event.toolTip.add("Max-Temperature: " + HeatUtils.getMaxTemperatureFromNBT(tag));
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

            IHeatableItem impl = HeatUtils.getImpl(stack);
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

            IHeatableItem impl = HeatUtils.getImpl(stack);
            if (impl != null) {
                Block block = event.world.getBlock(Utils.floor(ei.posX), Utils.floor(ei.posY), Utils.floor(ei.posZ));
                if (block.getMaterial() == Material.water) {
                    impl.onUpdateHeat(stack, event.world, -5.0F);
                } else {
                    impl.onUpdateHeat(stack, event.world);
                }
            }
        }
    }
}
