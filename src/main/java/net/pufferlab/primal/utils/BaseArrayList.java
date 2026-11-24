package net.pufferlab.primal.utils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BaseArrayList<T> extends AbstractList<T> {

    public List<T> list = new ArrayList<>();

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @NotNull
    @Override
    public Spliterator<T> spliterator() {
        return list.spliterator();
    }

    @Override
    public boolean removeIf(@NotNull Predicate<? super T> filter) {
        return list.removeIf(filter);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public boolean add(T t) {
        return list.add(t);
    }

    @Override
    public T set(int index, T t) {
        return list.set(index, t);
    }

    @Override
    public T get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        list.forEach(action);
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public void sort(@Nullable Comparator<? super T> c) {
        list.sort(c);
    }

    @Override
    public void replaceAll(@NotNull UnaryOperator<T> operator) {
        list.replaceAll(operator);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @NotNull
    @Override
    public <T1> T1[] toArray(@NotNull T1[] a) {
        return list.toArray(a);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @NotNull
    @Override
    public Stream<T> stream() {
        return list.stream();
    }

    @NotNull
    @Override
    public Stream<T> parallelStream() {
        return list.parallelStream();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return new HashSet<>(list).containsAll(c);
    }
}
