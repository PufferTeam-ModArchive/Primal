package net.pufferlab.primal.network;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.blocks.BlockCutSlab;
import net.pufferlab.primal.blocks.BlockCutSlabVertical;
import net.pufferlab.primal.blocks.BlockCutStairs;
import net.pufferlab.primal.network.packets.*;
import net.pufferlab.primal.tileentities.*;

public class NetworkPacket {

    public void sendMaterialPacket(World world, int x, int y, int z, Block block, int material, int material2) {
        if (world.isRemote) {
            Primal.proxy.sendPacketToServer(new PacketCutMaterial(x, y, z, material, material2));
        }
        TileEntity te = world.getTileEntity(x, y, z);
        if (block instanceof BlockCutSlab block2) {
            block2.setCutTileEntity(world, x, y, z, material, material2);
        }
        if (block instanceof BlockCutSlabVertical block2) {
            block2.setCutTileEntity(world, x, y, z, material, material2);
        }
        if (te instanceof TileEntityCut tef) {
            tef.setMaterialMeta(material);
        }
        if (te instanceof TileEntityCutDouble tef) {
            tef.setMaterialMeta2(material2);
        }
    }

    public void sendMaterialPacket(World world, int x, int y, int z, Block block, int material) {
        if (world.isRemote) {
            Primal.proxy.sendPacketToServer(new PacketCutMaterial(x, y, z, material));
        }
        TileEntity te = world.getTileEntity(x, y, z);
        if (block instanceof BlockCutStairs block2) {
            block2.setCutTileEntity(world, x, y, z, material);
        }
        if (block instanceof BlockCutSlab block2) {
            block2.setCutTileEntity(world, x, y, z, material, -1);
        }
        if (block instanceof BlockCutSlabVertical block2) {
            block2.setCutTileEntity(world, x, y, z, material, -1);
        }
        if (te instanceof TileEntityCut tef) {
            tef.setMaterialMeta(material);
        }
    }

    public void sendAnvilPlanPacket(TileEntityAnvil tile, String recipeID) {
        if (Primal.proxy.getClientWorld().isRemote) {
            Primal.proxy.sendPacketToServer(new PacketAnvilPlan(tile, recipeID));
        }
    }

    public void sendAnvilWorkPacket(TileEntityAnvil tile, int buttonID) {
        if (Primal.proxy.getClientWorld().isRemote) {
            Primal.proxy.sendPacketToServer(new PacketAnvilWork(tile, buttonID));
        }
    }

    public void sendSpeedButtonPacket(TileEntityGenerator tile, boolean addSpeed, boolean isShifting) {
        if (Primal.proxy.getClientWorld().isRemote) {
            Primal.proxy.sendPacketToServer(new PacketSpeedButton(tile, addSpeed, isShifting));
        }
    }

    public void sendKnappingPacket(int x, int y) {
        if (Primal.proxy.getClientWorld().isRemote) {
            Primal.proxy.sendPacketToServer(new PacketKnappingClick(x, y));
        }
    }

    public void sendSwingPacket(EntityPlayer player) {
        Primal.proxy.sendPacketToClient(new PacketSwingArm(player));
    }

    public void sendInventoryUpdate(EntityPlayer player) {
        if (!player.worldObj.isRemote) {
            Primal.proxy.sendPacketToClient(new PacketSwingArm(player));
            player.inventoryContainer.detectAndSendChanges();
        }
    }

    public void sendMotionSpeedPacket(IMotion te) {
        if (!te.getWorld().isRemote) {
            Primal.proxy.sendPacketToClient(new PacketSpeedUpdate(te));
            te.mark();
        }
    }

    public void sendChunkUpdate(World world) {
        if (world instanceof WorldServer worldServer) {
            worldServer.getPlayerManager()
                .updatePlayerInstances();
        }
    }
}
