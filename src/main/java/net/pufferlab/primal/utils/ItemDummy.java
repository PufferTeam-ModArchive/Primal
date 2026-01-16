package net.pufferlab.primal.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemDummy extends Item {

    public static ItemDummy instance = new ItemDummy();

    public MovingObjectPosition getMovingObjectPositionFromPlayerPublic(World worldIn, EntityPlayer player,
        boolean useLiquids) {
        return getMovingObjectPositionFromPlayer(worldIn, player, useLiquids);
    }
}
