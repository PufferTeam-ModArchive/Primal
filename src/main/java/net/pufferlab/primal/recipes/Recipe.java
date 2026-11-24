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

    public Object[] getRecipeElements() {
        return null;
    }

    public String getRecipeID() {
        if (this.recipeID == null) {
            setRecipeID(getRecipeElements());
        }
        return this.recipeID;
    }

    @Override
    public String getIdentifierType() {
        return "recipe";
    }
}
