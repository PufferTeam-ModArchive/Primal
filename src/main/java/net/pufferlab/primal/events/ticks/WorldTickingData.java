package net.pufferlab.primal.events.ticks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.pufferlab.primal.Primal;

public class WorldTickingData extends WorldSavedData {

    private static final String name = Primal.MODID + "TickData";

    public long tickTime = 0;
    public static long clientTickTime = 0;

    public WorldTickingData(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        tickTime = nbt.getLong("tickTime");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setLong("tickTime", tickTime);
    }

    public static WorldTickingData get(World world) {
        WorldTickingData data = (WorldTickingData) world.loadItemData(WorldTickingData.class, name);

        if (data == null) {
            data = new WorldTickingData(name);
            world.setItemData(name, data);
        }

        return data;
    }

    public static void tick(World world) {
        WorldTickingData data = WorldTickingData.get(world);
        data.tickTime++;
        data.markDirty();
    }

    public static long getTickTime(World world) {
        if (world.isRemote) {
            return clientTickTime;
        } else {
            WorldTickingData data = WorldTickingData.get(world);
            return data.tickTime;
        }
    }

    public static long getClientTickTime() {
        return clientTickTime;
    }
}
