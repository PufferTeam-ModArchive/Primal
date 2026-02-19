package net.pufferlab.primal.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Registry;
import net.pufferlab.primal.utils.Utils;
import net.pufferlab.primal.items.itemblocks.ItemBlockPrimal;
import net.pufferlab.primal.tileentities.TileEntityCampfire;
import net.pufferlab.primal.tileentities.TileEntityOven;
import net.pufferlab.primal.utils.BlockUtils;
import net.pufferlab.primal.utils.ItemUtils;

public class BlockOven extends BlockCampfire {

    public IIcon[] ovenIcons = new IIcon[2];

    public static final int iconOven = 96;

    public BlockOven() {}

    public boolean isOven() {
        return true;
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        super.registerBlockIcons(reg);

        ovenIcons[0] = reg.registerIcon(Primal.MODID + ":oven");
        ovenIcons[1] = reg.registerIcon("minecraft:brick");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == iconCampfire) {
            return icons[0];
        }
        if (side == iconCampfireSpit) {
            return icons[4];
        }
        if (side == iconOven) {
            return ovenIcons[0];
        }
        return ovenIcons[1];
    }

    @Override
    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX,
        float subY, float subZ) {
        ItemStack heldItem = player.getHeldItem();
        if (ItemUtils.canBeLit(heldItem)) return true;
        int meta = worldIn.getBlockMetadata(x, y, z);
        if ((Utils.containsOreDict(heldItem, "firewood") && meta > 0 && meta < 5)
            || (Utils.containsOreDict(heldItem, "kindling") && meta == 0)) {
            worldIn.setBlockMetadataWithNotify(x, y, z, meta + 1, 2);
            TileEntity te = worldIn.getTileEntity(x, y, z);
            if (meta == 0) {
                BlockUtils.playSound(worldIn, x, y, z, Registry.thatch);
            } else {
                BlockUtils.playSound(worldIn, x, y, z, Registry.log_pile);
            }
            if (te instanceof TileEntityCampfire tef) {
                tef.addInventorySlotContentsUpdate(meta + 1, player);
                tef.markDirty();
                if (meta == 4) {
                    tef.isBuilt = true;
                    worldIn.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
                    worldIn.markBlockForUpdate(x, y, z);
                }
                return true;
            }
        } else {
            TileEntity te = worldIn.getTileEntity(x, y, z);
            if (te instanceof TileEntityCampfire tef) {
                boolean top = false;
                boolean left = false;
                if (subZ < 0.5F) {
                    top = true;
                }
                if (subX > 0.5F) {
                    left = true;
                }
                if (!top && left) {
                    return addOrRemoveItem(worldIn, x, y, z, player, tef, 6, heldItem);
                }
                if (!top && !left) {
                    return addOrRemoveItem(worldIn, x, y, z, player, tef, 7, heldItem);
                }
                if (top && left) {
                    return addOrRemoveItem(worldIn, x, y, z, player, tef, 8, heldItem);
                }
                if (top && !left) {
                    return addOrRemoveItem(worldIn, x, y, z, player, tef, 9, heldItem);
                }
            }
        }
        return false;
    }

    @Override
    public void randomDisplayTick(World worldIn, int x, int y, int z, Random random) {
        TileEntity te = worldIn.getTileEntity(x, y, z);
        if (te instanceof TileEntityCampfire tef) {
            if (tef.isFired) {
                if (random.nextInt(24) == 0) {
                    worldIn.playSound(
                        (double) ((float) x + 0.5F),
                        (double) ((float) y + 0.5F),
                        (double) ((float) z + 0.5F),
                        "fire.fire",
                        1.0F + random.nextFloat(),
                        random.nextFloat() * 0.7F + 0.3F,
                        false);
                }
                ForgeDirection dir = BlockUtils.getDirectionFromFacing(tef.facingMeta);
                int offsetX = dir.offsetX;
                int offsetZ = dir.offsetZ;
                int newX = x;
                int newY = y;
                int newZ = z;
                boolean skip = false;
                Block block = worldIn.getBlock(x + offsetX, y, z + offsetZ);
                if (block instanceof BlockChimney) {
                    for (int i = 0; i < 20; i++) {
                        if (!skip) {
                            Block block2 = worldIn.getBlock(x + offsetX, y + i, z + offsetZ);
                            if (block2 instanceof BlockChimney) {
                                newX = x + offsetX;
                                newY = y + i;
                                newZ = z + offsetZ;
                            } else {
                                skip = true;
                            }
                        }
                    }
                }

                int l;
                float f;
                float f1;
                float f2;

                for (l = 0; l < 4; ++l) {
                    f = (float) newX + random.nextFloat();
                    f1 = (float) newY + random.nextFloat() * 0.5F + 0.5F;
                    f2 = (float) newZ + random.nextFloat();
                    worldIn.spawnParticle("largesmoke", (double) f, (double) f1, (double) f2, 0.0D, 0.0D, 0.0D);
                }
            }
        }

    }

    @Override
    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {}

    @Override
    public String getUnlocalizedName() {
        return "tile." + Primal.MODID + ".oven";
    }

    @Override
    public int getRenderType() {
        return Primal.proxy.getOvenRenderID();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityOven();
    }

    @Override
    public Class<? extends ItemBlock> getItemBlockClass() {
        return ItemBlockPrimal.class;
    }
}
