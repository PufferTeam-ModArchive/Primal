package net.pufferlab.primitivelife.recipes;

import net.minecraft.item.ItemStack;

public class KnappingPattern {

    public boolean[][] pattern = new boolean[5][5];
    public ItemStack output;
    public int type;

    public KnappingPattern(int type, ItemStack output, String... rows) {
        for (int i = 0; i < rows.length; i++) {
            char[] charArray = rows[i].toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                if (charArray[j] == 'C') {
                    pattern[j][i] = false;
                }
                if (charArray[j] == ' ') {
                    pattern[j][i] = true;
                }
            }
        }
        this.output = output;
        this.type = type;

    }

    public boolean equals(int type, boolean[][] pattern2) {
        if (this.type != type) return false;
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
