package net.pufferlab.primal.recipes;

import net.pufferlab.primal.utils.IIdentifiable;
import net.pufferlab.primal.utils.TextUtils;

public abstract class Recipe<T> implements IIdentifiable {

    private String recipeID = null;

    public T setRecipeID(Object... objects) {
        if (this.recipeID == null) {
            this.recipeID = TextUtils.getRecipeHash(objects);
        }
        return (T) this;
    }

    public String getRecipeID() {
        return recipeID;
    }

    @Override
    public String getIdentifierType() {
        return "recipe";
    }
}
