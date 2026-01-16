package net.pufferlab.primal.entities.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.pufferlab.primal.Primal;

public class PlayerData implements IExtendedEntityProperties {

    public static final String name = Primal.MODID + "PlayerData";

    private final EntityPlayer player;
    public boolean temperatureDebug;

    public PlayerData(EntityPlayer player) {
        this.player = player;
    }

    public void setTemperatureDebug(boolean state) {
        this.temperatureDebug = state;
    }

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(name, new PlayerData(player));
    }

    public static PlayerData get(EntityPlayer player) {
        return (PlayerData) player.getExtendedProperties(name);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound tag = new NBTTagCompound();
        compound.setTag(name, tag);

        tag.setBoolean("TemperatureDebug", this.temperatureDebug);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound tag = compound.getCompoundTag(name);

        this.temperatureDebug = tag.getBoolean("TemperatureDebug");
    }

    public static void syncPlayerData(PlayerData from, PlayerData to) {
        if (from != null && to != null) {
            to.temperatureDebug = from.temperatureDebug;
        }
    }

    @Override
    public void init(Entity entity, World world) {}
}
