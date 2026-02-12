package net.pufferlab.primal.items.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockCutSlab;
import net.pufferlab.primal.network.packets.PacketCutMaterial;
import net.pufferlab.primal.tileentities.TileEntityCut;
import net.pufferlab.primal.utils.CutUtils;

public class ItemBlockCutSlab extends ItemBlock {

    private final boolean isFull;
    private final BlockCutSlab slabBlock;
    private final BlockCutSlab fullBlock;

    public ItemBlockCutSlab(Block block, BlockCutSlab blockSlab, BlockCutSlab blockFullSlab, Boolean isFull) {
        super(block);
        this.slabBlock = (BlockCutSlab) blockSlab;
        this.fullBlock = (BlockCutSlab) blockFullSlab;
        this.isFull = isFull;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getSpriteNumber() {
        return 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (field_150939_a instanceof BlockCutSlab block2) {
            return block2.func_150002_b(stack.getItemDamage());
        }
        return null;
    }

    @Override
    public IIcon getIconFromDamage(int id) {
        return CutUtils.getIcon(2, id);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (this.isFull) {
            return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        } else if (stack.stackSize == 0) {
            return false;
        } else if (!player.canPlayerEdit(x, y, z, side, stack)) {
            return false;
        } else {
            Block block = world.getBlock(x, y, z);
            int i1 = world.getBlockMetadata(x, y, z);
            int materialID = -1;
            if (block == this.slabBlock) {
                if (block instanceof BlockCutSlab block2) {
                    materialID = block2.getMaterialMeta(world, x, y, z);
                }
            }
            boolean flag = (i1 & 8) != 0;

            if ((side == 1 && !flag || side == 0 && flag) && materialID == stack.getItemDamage()) {
                if (world.checkNoEntityCollision(this.fullBlock.getCollisionBoundingBoxFromPool(world, x, y, z))
                    && world.setBlock(x, y, z, this.fullBlock, 0, 3)) {
                    sendMaterialPacket(x, y, z, stack.getItemDamage());
                    world.playSoundEffect(
                        (double) ((float) x + 0.5F),
                        (double) ((float) y + 0.5F),
                        (double) ((float) z + 0.5F),
                        this.fullBlock.stepSound.func_150496_b(),
                        (this.fullBlock.stepSound.getVolume() + 1.0F) / 2.0F,
                        this.fullBlock.stepSound.getPitch() * 0.8F);
                    --stack.stackSize;
                    return true;
                }
                return false;
            } else {
                return this.func_150946_a(stack, player, world, x, y, z, side)
                    || super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
            }
        }
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
            sendMaterialPacket(
                x,
                y,
                z,
                player.getHeldItem()
                    .getItemDamage());
        }

        return true;
    }

    @Override
    public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack) {
        int i1 = x;
        int j1 = y;
        int k1 = z;
        Block block = world.getBlock(x, y, z);
        int l1 = world.getBlockMetadata(x, y, z);
        int i2 = l1 & 7;
        boolean flag = (l1 & 8) != 0;

        if ((side == 1 && !flag || side == 0 && flag) && block == this.slabBlock && i2 == stack.getItemDamage()) {
            return true;
        } else {
            if (side == 0) {
                --y;
            }

            if (side == 1) {
                ++y;
            }

            if (side == 2) {
                --z;
            }

            if (side == 3) {
                ++z;
            }

            if (side == 4) {
                --x;
            }

            if (side == 5) {
                ++x;
            }

            int materialID = -1;
            if (block == this.slabBlock) {
                if (block instanceof BlockCutSlab block2) {
                    materialID = block2.getMaterialMeta(world, x, y, z);
                }
            }
            return materialID == stack.getItemDamage() || super.func_150936_a(world, i1, j1, k1, side, player, stack);
        }
    }

    private boolean func_150946_a(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side) {
        if (side == 0) {
            --y;
        }

        if (side == 1) {
            ++y;
        }

        if (side == 2) {
            --z;
        }

        if (side == 3) {
            ++z;
        }

        if (side == 4) {
            --x;
        }

        if (side == 5) {
            ++x;
        }

        Block block = world.getBlock(x, y, z);
        int materialID = -1;
        if (block == this.slabBlock) {
            if (block instanceof BlockCutSlab block2) {
                materialID = block2.getMaterialMeta(world, x, y, z);
            }
        }

        if (materialID == stack.getItemDamage()) {
            if (world.checkNoEntityCollision(this.fullBlock.getCollisionBoundingBoxFromPool(world, x, y, z))
                && world.setBlock(x, y, z, this.fullBlock, 0, 3)) {
                sendMaterialPacket(x, y, z, stack.getItemDamage());
                world.playSoundEffect(
                    (double) ((float) x + 0.5F),
                    (double) ((float) y + 0.5F),
                    (double) ((float) z + 0.5F),
                    this.fullBlock.stepSound.func_150496_b(),
                    (this.fullBlock.stepSound.getVolume() + 1.0F) / 2.0F,
                    this.fullBlock.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
            }
            return true;
        } else {
            return false;
        }
    }

    public void sendMaterialPacket(int x, int y, int z, int material) {
        if (Primal.proxy.getClientWorld().isRemote) {
            TileEntity te = Primal.proxy.getClientWorld()
                .getTileEntity(x, y, z);
            if (te instanceof TileEntityCut tef) {
                tef.setMaterialMeta(material);
            }
            Primal.proxy.sendPacketToServer(new PacketCutMaterial(x, y, z, material));
        }
    }
}
