package net.pufferlab.primal.entities.player;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.HashUtils;
import net.pufferlab.primal.utils.IOUtils;
import net.pufferlab.primal.utils.QuestList;
import net.pufferlab.primal.utils.QuestType;

import io.netty.buffer.ByteBuf;

public class PlayerData implements IExtendedEntityProperties {

    public static final String name = Primal.MODID + "PlayerData";

    private final EntityPlayer player;
    private boolean temperatureDebug;
    private boolean blockInfoDebug;
    private boolean recipeDebug;

    private QuestList completedQuests = new QuestList();
    private QuestList claimedQuests = new QuestList();
    private QuestList questsToUnlock = new QuestList();

    private long pos1Coords;
    private boolean pos1CoordsValid;
    private long pos2Coords;
    private boolean pos2CoordsValid;
    private boolean coordChanged;

    private boolean isBreakingTree;

    public PlayerData(EntityPlayer player) {
        this.player = player;
    }

    public PlayerData() {
        this.player = null;
    }

    public List<QuestType> getCompletedQuests() {
        return completedQuests;
    }

    public List<QuestType> getClaimedQuests() {
        return claimedQuests;
    }

    public List<QuestType> getQuestsToUnlock() {
        return questsToUnlock;
    }

    public boolean isBreakingTree() {
        return isBreakingTree;
    }

    public void setBreakingTree(boolean breakingTree) {
        isBreakingTree = breakingTree;
    }

    public boolean getTemperatureDebug() {
        return this.temperatureDebug;
    }

    public boolean getRecipeDebug() {
        return this.recipeDebug;
    }

    public void setTemperatureDebug(boolean state) {
        this.temperatureDebug = state;
        updateClient();
    }

    public void setRecipeDebug(boolean state) {
        this.recipeDebug = state;
        updateClient();
    }

    public boolean getBlockInfoDebug() {
        return this.blockInfoDebug;
    }

    public void setBlockInfoDebug(boolean state) {
        this.blockInfoDebug = state;
        updateClient();
    }

    public long getSelectPos1() {
        return this.pos1Coords;
    }

    public void setSelectPos1(long coord) {
        this.pos1Coords = coord;
        this.pos1CoordsValid = true;
        coordChanged = true;
        updateClient();
    }

    public long getSelectPos2() {
        return this.pos2Coords;
    }

    public void setSelectPos2(long coord) {
        this.pos2Coords = coord;
        this.pos2CoordsValid = true;
        coordChanged = true;
        updateClient();
    }

    public void updateClient() {
        updateLists();
        Primal.proxy.packet.sendPlayerData(player, this);
    }

    public AxisAlignedBB cachedBB;

    public AxisAlignedBB getSelectionBB() {
        if (cachedBB == null || coordChanged) {
            coordChanged = false;
            long packedCoord1 = getSelectPos1();
            int x1 = HashUtils.unpackX(packedCoord1);
            int y1 = HashUtils.unpackY(packedCoord1);
            int z1 = HashUtils.unpackZ(packedCoord1);
            long packedCoord2 = getSelectPos2();
            int x2 = HashUtils.unpackX(packedCoord2);
            int y2 = HashUtils.unpackY(packedCoord2);
            int z2 = HashUtils.unpackZ(packedCoord2);
            int minX = Math.min(x1, x2);
            int minY = Math.min(y1, y2);
            int minZ = Math.min(z1, z2);
            int maxX = Math.max(x1, x2);
            int maxY = Math.max(y1, y2);
            int maxZ = Math.max(z1, z2);
            cachedBB = AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX + 1, maxY + 1, maxZ + 1);
        }
        return cachedBB;
    }

    public boolean hasValidSelection() {
        return pos1CoordsValid && pos2CoordsValid;
    }

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(name, new PlayerData(player));
    }

    public static PlayerData get(EntityPlayer player) {
        if (player == null) return null;
        return (PlayerData) player.getExtendedProperties(name);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound tag = new NBTTagCompound();
        compound.setTag(name, tag);

        writeToNBT(tag);
    }

    public void writeToNBT(NBTTagCompound tag) {
        tag.setBoolean("temperatureDebug", this.temperatureDebug);
        tag.setBoolean("blockInfoDebug", this.blockInfoDebug);
        tag.setBoolean("recipeDebug", this.recipeDebug);
        tag.setLong("pos1Coords", this.pos1Coords);
        tag.setBoolean("pos1CoordsValid", this.pos1CoordsValid);
        tag.setLong("pos2Coords", this.pos2Coords);
        tag.setBoolean("pos2CoordsValid", this.pos2CoordsValid);

        ByteBuf buf = IOUtils.getBuffer();
        this.completedQuests.writeToBuffer(buf);
        byte[] questData = IOUtils.getBytes(buf);
        tag.setByteArray("completedQuests", questData);

        ByteBuf buf2 = IOUtils.getBuffer();
        this.claimedQuests.writeToBuffer(buf2);
        byte[] questData2 = IOUtils.getBytes(buf2);
        tag.setByteArray("claimedQuests", questData2);
    }

    public void writeToBuffer(ByteBuf buf) {
        buf.writeBoolean(this.temperatureDebug);
        buf.writeBoolean(this.blockInfoDebug);
        buf.writeBoolean(this.recipeDebug);
        buf.writeLong(this.pos1Coords);
        buf.writeBoolean(this.pos1CoordsValid);
        buf.writeLong(this.pos2Coords);
        buf.writeBoolean(this.pos2CoordsValid);

        buf.writeBoolean(this.coordChanged);

        this.completedQuests.writeToBuffer(buf);
        this.claimedQuests.writeToBuffer(buf);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound tag = compound.getCompoundTag(name);

        readFromNBT(tag);
    }

    public void readFromNBT(NBTTagCompound tag) {
        this.temperatureDebug = tag.getBoolean("temperatureDebug");
        this.blockInfoDebug = tag.getBoolean("blockInfoDebug");
        this.recipeDebug = tag.getBoolean("recipeDebug");
        this.pos1Coords = tag.getLong("pos1Coords");
        this.pos1CoordsValid = tag.getBoolean("pos1CoordsValid");
        this.pos2Coords = tag.getLong("pos2Coords");
        this.pos2CoordsValid = tag.getBoolean("pos2CoordsValid");

        byte[] questData = tag.getByteArray("completedQuests");
        ByteBuf buf = IOUtils.getBuffer(questData);
        this.completedQuests.readFromBuffer(buf);

        byte[] questData2 = tag.getByteArray("claimedQuests");
        ByteBuf buf2 = IOUtils.getBuffer(questData2);
        this.claimedQuests.readFromBuffer(buf2);

        updateLists();
    }

    public void readFromBuffer(ByteBuf buf) {
        this.temperatureDebug = buf.readBoolean();
        this.blockInfoDebug = buf.readBoolean();
        this.recipeDebug = buf.readBoolean();
        this.pos1Coords = buf.readLong();
        this.pos1CoordsValid = buf.readBoolean();
        this.pos2Coords = buf.readLong();
        this.pos2CoordsValid = buf.readBoolean();

        this.coordChanged = buf.readBoolean();

        this.completedQuests.readFromBuffer(buf);
        this.claimedQuests.readFromBuffer(buf);

        updateLists();
    }

    public static void syncPlayerData(PlayerData from, PlayerData to) {
        if (from != null && to != null) {
            to.temperatureDebug = from.temperatureDebug;
            to.blockInfoDebug = from.blockInfoDebug;
            to.recipeDebug = from.recipeDebug;
            to.pos1Coords = from.pos1Coords;
            to.pos1CoordsValid = from.pos1CoordsValid;
            to.pos2Coords = from.pos2Coords;
            to.pos2CoordsValid = from.pos2CoordsValid;

            to.coordChanged = from.coordChanged;

            to.completedQuests = from.completedQuests;
            to.claimedQuests = from.claimedQuests;
        }
    }

    public void updateLists() {
        this.questsToUnlock.clear();
        for (QuestType quest : QuestType.values()) {
            if (quest.canBeUnlocked(this)) {
                this.questsToUnlock.add(quest);
            }
        }
    }

    @Override
    public void init(Entity entity, World world) {}
}
