package net.pufferlab.primal.world.graph;

import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.NBTType;
import net.pufferlab.primal.utils.PosMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;

public class GraphData extends WorldSavedData {

    private static final String name = Primal.MODID + "GraphData";

    private int lastID;
    public final PosMap.Single<IGraphEntity> entityCoordMap = new PosMap.Single<>();
    public final TIntObjectMap<IGraphEntity> entityIDMap = new TIntObjectHashMap<>();

    public GraphData(String p_i2141_1_) {
        super(name);
    }

    public int getLastID() {
        return lastID++;
    }

    public void addEntity(IGraphEntity entity) {
        entityCoordMap.put(entity.x(), entity.y(), entity.z(), entity);
        entityIDMap.put(entity.id(), entity);
    }

    public static IGraphEntity getEntity(World world, int id) {
        GraphData data = GraphData.get(world);
        IGraphEntity entity = data.entityIDMap.get(id);
        if (entity.invalid()) {
            entity = null;
        }
        return entity;
    }

    public static IGraphEntity getEntity(World world, int x, int y, int z) {
        GraphData data = GraphData.get(world);
        IGraphEntity entity = data.entityCoordMap.get(x, y, z);
        if (entity.invalid()) {
            entity = null;
        }
        if (entity == null) {
            Block block = world.getBlock(x, y, z);
            entity = data.getNewGraphEntity(block);
            entity.initialize(block, data.getLastID(), x, y, z);
            data.addEntity(entity);
        }
        return entity;
    }

    public static void removeEntity(World world, int id) {
        GraphData data = GraphData.get(world);
        IGraphEntity entity = data.entityIDMap.remove(id);
        data.entityCoordMap.remove(entity.x(), entity.y(), entity.z());
        entity.invalidate();
    }

    public static void removeEntity(World world, int x, int y, int z) {
        GraphData data = GraphData.get(world);
        IGraphEntity entity = data.entityCoordMap.remove(x, y, z);
        data.entityIDMap.remove(entity.id());
        entity.invalidate();
    }

    public IGraphEntity getNewGraphEntity(Block block) {
        if (block instanceof IGraphEntityProvider provider) {
            return provider.getNewGraphEntity();
        } else {
            return null;
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.lastID = nbt.getInteger("lastID");

        NBTTagList list = nbt.getTagList(name, NBTType.TagCompound);
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound tag = list.getCompoundTagAt(i);
            Block block = Block.getBlockById(tag.getInteger("blockID"));
            IGraphEntity entity = getNewGraphEntity(block);
            entity.readFromNBT(tag);
            addEntity(entity);
        }
    }

    public void readFromBuffer(ByteBuf buf) {
        this.lastID = buf.readInt();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            Block block = Block.getBlockById(buf.readInt());
            IGraphEntity entity = getNewGraphEntity(block);
            entity.readFromBuffer(buf);
            addEntity(entity);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("lastID", this.lastID);

        NBTTagList list = new NBTTagList();
        for (IGraphEntity graphEntity : entityCoordMap.values()) {
            if (graphEntity.invalid()) continue;
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("blockID", Block.getIdFromBlock(graphEntity.block()));
            graphEntity.writeToNBT(tag);
            list.appendTag(tag);
        }

        nbt.setTag(name, list);
    }

    public void writeToBuffer(ByteBuf buf) {
        buf.writeInt(this.lastID);

        Collection<IGraphEntity> list = entityCoordMap.values();
        buf.writeInt(list.size());
        for (IGraphEntity graphEntity : list) {
            if (graphEntity.invalid()) continue;
            buf.writeInt(Block.getIdFromBlock(graphEntity.block()));
            graphEntity.writeToBuffer(buf);
        }
    }

    public static GraphData get(World world) {
        GraphData data = (GraphData) world.loadItemData(GraphData.class, name);

        if (data == null) {
            data = new GraphData(name);
            world.setItemData(name, data);
        }

        return data;
    }
}
