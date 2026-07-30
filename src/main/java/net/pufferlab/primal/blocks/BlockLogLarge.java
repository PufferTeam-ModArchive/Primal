package net.pufferlab.primal.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.items.itemblocks.ItemBlockMeta;
import net.pufferlab.primal.utils.WoodType;

public class BlockLogLarge extends BlockLog implements IMetaBlock, IPrimalBlock {

    public WoodType wood;
    public String name;
    public String[] field_150168_M;
    public String[] blacklist;

    public BlockLogLarge(WoodType wood) {
        this.name = wood.getName();
        this.wood = wood;
        this.field_150168_M = wood.types;
        this.wood.setLogItem(this, 0);
        this.wood.setStrippedLogItem(this, 1);
        this.wood.setWoodItem(this, 2);
        this.wood.setStrippedWoodItem(this, 3);
        String[] blacklist = new String[4];
        if (!this.wood.hasLog) {
            blacklist[0] = wood.types[0];
        }
        this.blacklist = blacklist;
    }

    @Override
    public void breakBlock(World worldIn, int x, int y, int z, Block blockBroken, int meta) {}

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < 4; i++) {
            if (!this.wood.hasLog && i == 0) {
                list.add(this.wood.getLogItem());
            } else {
                list.add(new ItemStack(itemIn, 1, i));
            }
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        this.field_150167_a = new IIcon[field_150168_M.length];
        this.field_150166_b = new IIcon[field_150168_M.length];

        for (int i = 0; i < this.field_150167_a.length; ++i) {
            if (i == 0 || i == 1) {
                this.field_150167_a[i] = reg.registerIcon(wood.getSideTexture(i == 1));
                this.field_150166_b[i] = reg.registerIcon(wood.getTopTexture(i == 1));
            } else {
                this.field_150167_a[i] = this.field_150167_a[i - 2];
                this.field_150166_b[i] = this.field_150167_a[i - 2];
            }
        }
    }

    @Override
    public String[] getElements() {
        return field_150168_M;
    }

    @Override
    public String[] getElementsBlacklist() {
        return blacklist;
    }

    @Override
    public String getElementName() {
        return "log";
    }

    @Override
    public boolean hasSuffix() {
        return false;
    }

    @Override
    public CreativeTabs getCreativeTab() {
        return Registry.creativeTabWorld;
    }

    @Override
    public boolean wrapElements() {
        return true;
    }

    @Override
    public int getDamageValue(World worldIn, int x, int y, int z) {
        return worldIn.getBlockMetadata(x, y, z) & 3;
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockMeta.class;
    }
}
