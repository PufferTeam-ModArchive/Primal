package net.pufferlab.primal.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.FoodType;
import net.pufferlab.primal.utils.ItemUtils;
import net.pufferlab.primal.utils.Utils;

import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdible;

@Optional.Interface(iface = "squeek.applecore.api.food.IEdible", modid = Mods.ID.appleCore)
public class ItemMetaFood extends ItemFood implements IMetaItem, IPrimalItem, IEdible {

    protected FoodType[] elements;
    protected String[] elementsNames;
    private String[] elementsBlacklist;
    private IIcon[] icons;
    private String name;

    public ItemMetaFood(FoodType[] elements, String type, boolean meat) {
        super(0, 0.0f, meat);
        this.elements = elements;
        this.elementsNames = FoodType.getNames(elements);
        this.name = type;
        this.elementsBlacklist = FoodType.getBlacklistNames(elements);
        this.setHasSubtypes(true);
        setCreativeTab(CreativeTabs.tabFood);
        for (int i = 0; i < elements.length; i++) {
            elements[i].setFoodItem(this, i);
        }
    }

    public ItemMetaFood(FoodType[] elements, String type) {
        this(elements, type, false);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons = new IIcon[elements.length];

        for (int i = 0; i < elements.length; i++) {
            if (!Utils.contains(elementsBlacklist, elements[i].name)) {
                icons[i] = register.registerIcon(Primal.MODID + ":" + elements[i].name);
            }
        }
    }

    public ItemMetaFood setBlacklist(String[] blacklist) {
        this.elementsBlacklist = blacklist;
        return this;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTabs, List<ItemStack> list) {
        for (int i = 0; i < elements.length; i++) {
            if (!Utils.contains(elementsBlacklist, elements[i].name)) {
                list.add(new ItemStack(item, 1, i));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (meta >= elements.length || Utils.contains(elementsBlacklist, elements[meta].name)) {
            return null;
        }
        return icons[meta];
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() >= elements.length
            || Utils.contains(elementsBlacklist, elements[stack.getItemDamage()].name)) {
            return "item." + Primal.MODID + ".error";
        }
        return "item." + Primal.MODID + "." + elements[stack.getItemDamage()].name;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        if (meta >= elements.length || Utils.contains(elementsBlacklist, elements[meta].name)) {
            return null;
        }
        return icons[meta];
    }

    @Override
    public int func_150905_g(ItemStack itemStackIn) {
        return elements[itemStackIn.getItemDamage()].hunger;
    }

    @Override
    public float func_150906_h(ItemStack itemStackIn) {
        return elements[itemStackIn.getItemDamage()].saturation;
    }

    public boolean isMeat(int meta) {
        return elements[meta].isMeat;
    }

    @Override
    public FoodValues getFoodValues(ItemStack itemStackIn) {
        return new FoodValues(func_150905_g(itemStackIn), func_150906_h(itemStackIn));
    }

    @Override
    protected void onFoodEaten(ItemStack itemstack, World world, EntityPlayer player) {
        FoodType food = elements[itemstack.getItemDamage()];
        if (food.hasExtraItem()) {
            String item = food.extraItem;
            player.inventory.addItemStackToInventory(ItemUtils.getItem(item));
        }

        if (food.hasEffect()) {
            int potionId = food.effectId;
            int potionDuration = food.effectDuration;
            int potionAmplifier = 0;
            float potionEffectProbability = food.effectProbability;

            if (!world.isRemote && potionId > 0 && world.rand.nextFloat() < potionEffectProbability) {
                player.addPotionEffect(new PotionEffect(potionId, potionDuration * 20, potionAmplifier));
            }
        }

    }

    public String[] getElements() {
        return elementsNames;
    }

    @Override
    public String[] getElementsBlacklist() {
        return elementsBlacklist;
    }

    public boolean hasSuffix() {
        return false;
    }

    public String getElementName() {
        return name;
    }
}
