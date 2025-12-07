package net.pufferlab.primal.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class KnappingRecipe {

    private static final List<KnappingRecipe> recipeList = new ArrayList<>();

    public static void addKnappingRecipe(KnappingType type, ItemStack item, String... rows) {
        recipeList.add(new KnappingRecipe(type, item, rows));
    }

    public static ItemStack getOutput(KnappingType type, boolean[][] icons) {
        for (KnappingRecipe currentPattern : recipeList) {
            if (currentPattern.equals(type, icons)) {
                return currentPattern.output.copy();
            }
        }
        return null;
    }

    public static List<KnappingRecipe> getRecipeList() {
        return recipeList;
    }

    public boolean[][] pattern = new boolean[5][5];
    public ItemStack output;
    public KnappingType type;

    public KnappingRecipe(KnappingType type, ItemStack output, String... rows) {
        for (int i = 0; i < rows.length; i++) {
            char[] charArray = rows[i].toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                pattern[j][i] = charArray[j] == ' ';
            }
        }
        this.output = output;
        this.type = type;
    }

    public boolean equals(KnappingType type, boolean[][] pattern2) {
        if (!type.equals(this.type)) return false;
        boolean base = true;
        boolean mirror = true;
        boolean mirror2 = true;
        boolean mirror3 = true;
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                boolean thisIcon = this.pattern[x][y];
                boolean otherIcon = pattern2[x][y];
                boolean otherIcon2 = pattern2[4 - x][y];
                boolean otherIcon3 = pattern2[x][4 - y];
                boolean otherIcon4 = pattern2[4 - x][4 - y];
                if (thisIcon != otherIcon) {
                    base = false;
                }
                if (thisIcon != otherIcon2) {
                    mirror = false;
                }
                if (thisIcon != otherIcon3) {
                    mirror2 = false;
                }
                if (thisIcon != otherIcon4) {
                    mirror3 = false;
                }
                if (!base && !mirror && !mirror2 && !mirror3) return false;
            }
        }
        return true;
    }
}
