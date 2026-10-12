package net.pufferlab.primal.recipes;

import net.pufferlab.primal.utils.TextUtils;

public class Recipe<T> {

    public String recipeID;

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public T setRecipeID(Object... objects) {
        this.recipeID = TextUtils.getRecipeHash(objects);
        return (T) this;
    }

    public String getRecipeID() {
        return recipeID;
    }
}
