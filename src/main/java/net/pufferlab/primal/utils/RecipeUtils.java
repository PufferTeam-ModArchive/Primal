package net.pufferlab.primal.utils;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.pufferlab.primal.Constants;
import net.pufferlab.primal.Utils;
import net.pufferlab.primal.world.GlobalTickingData;

public class RecipeUtils {

    public static String getDisplayName(Object... objects) {
        for (Object object : objects) {
            if (object instanceof List list) {
                if (list.get(0) instanceof ItemStack is) {
                    return is.getDisplayName();
                }
            }
            if (object instanceof ItemStack is) {
                return is.getDisplayName();
            }
            if (object instanceof FluidStack fs) {
                return fs.getLocalizedName();
            }
        }
        return "";
    }

    public static int appendCount;

    public static String getRecipeHash(Object... objects) {
        StringBuilder builder = new StringBuilder();
        appendCount = 0;

        for (int i = 0; i < objects.length; i++) {
            Object obj = objects[i];

            appendRecipeElement(builder, obj);
        }

        return builder.toString()
            .trim();
    }

    private static void appendRecipeElement(StringBuilder builder, Object obj) {
        if (obj == null) return;

        if (appendCount > 0) {
            builder.append("|");
        }
        if (obj instanceof Iterable<?>iterable) {
            for (Object o : iterable) {
                appendRecipeElement(builder, o);
            }
        } else if (obj instanceof ItemStack is) {
            builder.append(ItemUtils.getName(is))
                .append('#')
                .append(is.stackSize)
                .append("@")
                .append(is.getItemDamage());
            appendCount++;
        } else if (obj instanceof FluidStack fs) {
            builder.append(ItemUtils.getName(fs))
                .append('#')
                .append(fs.amount);
            appendCount++;
        } else if (obj instanceof String str) {
            builder.append(str);
            appendCount++;
        }
    }

    public static void throwInvalidRecipe(String id) {
        throw new IllegalStateException(
            "Duplicate recipe ID detected.\nRecipe ID: " + id
                + "\nThis usually means two recipes were registered with the same ID.");
    }

    public static String getRecipeTooltip(String name, int timePassed, int timeToProcess, String suffix) {
        float percentagePassed = (float) timePassed / (float) timeToProcess;
        int percentage = Utils.floor((percentagePassed) * 100);
        return Constants.gray + name + ": " + percentage + "% " + suffix;
    }

    public static String getRecipeTooltip(String name, World world, long nextUpdate, int timeToProcess, String suffix) {
        long now = GlobalTickingData.getTickTime(world);

        long start = nextUpdate - timeToProcess;
        long elapsed = now - start;

        float pct = (float) elapsed / (float) timeToProcess;
        pct = MathHelper.clamp_float(pct, 0f, 1f);

        int percentage = (int) (pct * 100);

        return Constants.gray + name + ": " + percentage + "% " + suffix;
    }

    public static int getCurrentProgress(World world, long nextUpdate, int maxProgress) {
        long now = GlobalTickingData.getTickTime(world);

        long start = nextUpdate - maxProgress;
        long elapsed = now - start;

        if (elapsed < 0) elapsed = 0;
        if (elapsed > maxProgress) elapsed = maxProgress;

        return (int) elapsed;
    }

    public static String getStateTooltip(boolean state, String on, String off) {
        if (state) {
            return Constants.gray + "State: " + EnumChatFormatting.GREEN + on;
        } else {
            return Constants.gray + "State: " + EnumChatFormatting.RED + off;
        }
    }
}
