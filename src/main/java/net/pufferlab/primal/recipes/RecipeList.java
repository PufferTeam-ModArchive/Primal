package net.pufferlab.primal.recipes;

import java.util.*;
import java.util.function.Predicate;

import org.jetbrains.annotations.NotNull;

public class RecipeList<T extends Recipe> extends AbstractList<T> {

    public List<T> recipeList = new ArrayList<>();
    public Map<String, T> recipeIDMap = new HashMap<>();

    @Override
    public ListIterator<T> listIterator() {
        return recipeList.listIterator();
    }

    @Override
    public Iterator<T> iterator() {
        return recipeList.iterator();
    }

    @NotNull
    @Override
    public Spliterator<T> spliterator() {
        return recipeList.spliterator();
    }

    @Override
    public boolean removeIf(@NotNull Predicate<? super T> filter) {
        recipeIDMap.entrySet()
            .removeIf(t -> filter.test(t.getValue()));
        return recipeList.removeIf(filter);
    }

    @Override
    public T remove(int index) {
        recipeIDMap.remove(
            recipeList.get(index)
                .getRecipeID());
        return recipeList.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return recipeList.indexOf(o);
    }

    @Override
    public boolean add(T t) {
        String recipeID = t.getRecipeID();
        if (recipeID != null) {
            recipeIDMap.put(t.getRecipeID(), t);
        }
        return recipeList.add(t);
    }

    @Override
    public T set(int index, T t) {
        String recipeID = t.getRecipeID();
        if (recipeID != null) {
            recipeIDMap.put(t.getRecipeID(), t);
        }
        return recipeList.set(index, t);
    }

    public T get(String recipeID) {
        return recipeIDMap.get(recipeID);
    }

    @Override
    public T get(int index) {
        return recipeList.get(index);
    }

    @Override
    public int size() {
        return recipeList.size();
    }

    @Override
    public boolean equals(Object o) {
        return recipeList.equals(o);
    }

    @Override
    public int hashCode() {
        return recipeList.hashCode();
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        return recipeList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return recipeList.addAll(index, c);
    }
}
