package net.pufferlab.primal.recipes;

import java.util.*;
import java.util.function.Predicate;

import net.pufferlab.primal.utils.BaseArrayList;

import org.jetbrains.annotations.NotNull;

public class RecipeList<T extends Recipe> extends BaseArrayList<T> {

    public Map<String, T> recipeIDMap = new HashMap<>();

    @Override
    public boolean removeIf(@NotNull Predicate<? super T> filter) {
        recipeIDMap.entrySet()
            .removeIf(t -> filter.test(t.getValue()));
        return super.removeIf(filter);
    }

    @Override
    public T remove(int index) {
        recipeIDMap.remove(super.get(index).getRecipeID());
        return super.remove(index);
    }

    @Override
    public boolean add(T t) {
        String recipeID = t.getRecipeID();
        if (recipeID != null) {
            recipeIDMap.put(t.getRecipeID(), t);
        }
        return super.add(t);
    }

    @Override
    public T set(int index, T t) {
        String recipeID = t.getRecipeID();
        if (recipeID != null) {
            recipeIDMap.put(t.getRecipeID(), t);
        }
        return super.set(index, t);
    }

    public T get(String recipeID) {
        return recipeIDMap.get(recipeID);
    }
}
