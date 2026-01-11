package net.pufferlab.primal.events.ticks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.pufferlab.primal.Primal;

public class GlobalTickingData extends WorldSavedData {

    private static final String name = Primal.MODID + "GlobalTickData";

    public long tickTime = 0;
    public static long clientTickTime = 0;

    public GlobalTickingData(String name) {
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

    public static GlobalTickingData get() {
        World world = Primal.proxy.getOverworld();
        GlobalTickingData data = (GlobalTickingData) world.loadItemData(GlobalTickingData.class, name);

        if (data == null) {
            data = new GlobalTickingData(name);
            world.setItemData(name, data);
        }

        return data;
    }

    public static void tick() {
        GlobalTickingData data = GlobalTickingData.get();
        data.tickTime++;
        data.markDirty();
    }

    public static long getTickTime(World world) {
        if (world.isRemote) {
            return clientTickTime;
        } else {
            GlobalTickingData data = GlobalTickingData.get();
            return data.tickTime;
        }
    }

    public static long getTickTime() {
        GlobalTickingData data = GlobalTickingData.get();
        return data.tickTime;
    }

    public static long getClientTickTime() {
        return clientTickTime;
    }
}
