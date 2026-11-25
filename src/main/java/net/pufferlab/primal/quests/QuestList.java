package net.pufferlab.primal.quests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import net.pufferlab.primal.utils.BaseArrayList;
import net.pufferlab.primal.utils.IOUtils;

import org.jetbrains.annotations.NotNull;

import io.netty.buffer.ByteBuf;

public class QuestList extends BaseArrayList<QuestType> {

    public List<String> nameList = new ArrayList<>();

    public QuestList() {}

    public void writeToBuffer(ByteBuf buf) {
        IOUtils.writeStringList(buf, this.getNames());
    }

    public void readFromBuffer(ByteBuf buf) {
        this.setNames(IOUtils.readStringList(buf));
    }

    public void setNames(List<String> names) {
        clear();
        for (String name : names) {
            add(QuestType.getQuest(name));
        }
    }

    public List<String> getNames() {
        return nameList;
    }

    @Override
    public boolean addAll(int index, Collection<? extends QuestType> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends QuestType> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(QuestType t) {
        if (t == null) throw new NullPointerException();
        nameList.add(t.name);
        return super.add(t);
    }

    @Override
    public QuestType remove(int index) {
        nameList.remove(index);
        return super.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        if (o instanceof QuestType quest) {
            nameList.remove(quest.name);
        }
        return super.remove(o);
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(@NotNull Predicate<? super QuestType> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        nameList.clear();
        super.clear();
    }
}
