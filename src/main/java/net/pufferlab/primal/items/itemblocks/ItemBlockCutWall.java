package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockCutWall;
import net.pufferlab.primal.items.IMetaItem;
import net.pufferlab.primal.utils.CutUtils;

public class ItemBlockCutWall extends ItemBlock implements IMetaItem {

    public ItemBlockCutWall(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getSpriteNumber() {
        return 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (field_150939_a instanceof BlockCutWall block2) {
            return block2.func_150002_b(stack.getItemDamage());
        }
        return null;
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {

        if (!world.setBlock(x, y, z, field_150939_a, metadata, 3)) {
            return false;
        }

        if (world.getBlock(x, y, z) == field_150939_a) {
            field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
            field_150939_a.onPostBlockPlaced(world, x, y, z, metadata);
            Primal.proxy.packet.sendMaterialPacket(
                world,
                x,
                y,
                z,
                field_150939_a,
                player.getHeldItem()
                    .getItemDamage());
        }

        return true;
    }

    @Override
    public IIcon getIconFromDamage(int id) {
        return CutUtils.getIcon(2, id);
    }

    @Override
    public String[] getElements() {
        return CutUtils.getBlockNames();
    }

    @Override
    public String getElementName() {
        return "wall";
    }

    @Override
    public boolean hasSuffix() {
        return true;
    }
}
