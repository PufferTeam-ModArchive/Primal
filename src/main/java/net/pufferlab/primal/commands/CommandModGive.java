package net.pufferlab.primal.commands;

import java.util.List;

import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.Utils;

public class CommandModGive extends CommandSub {

    public String getCommandName() {
        return "give";
    }

    /**
     * Return the required permission level for this command.
     */
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException("commands." + Primal.MODID + ".give.usage", new Object[0]);
        } else {
            EntityPlayerMP entityplayermp = getPlayer(sender, args[0]);
            int i = 1;

            if (args.length >= 3) {
                i = parseIntBounded(sender, args[2], 1, 64);
            }

            ItemStack itemstack = Utils.getModItem(args[1], i);
            if (itemstack == null) {
                itemstack = Utils.getItem("minecraft:" + args[1]);
            }
            if (itemstack == null) return;

            if (args.length >= 4) {
                String s = func_147178_a(sender, args, 3).getUnformattedText();

                try {
                    NBTBase nbtbase = JsonToNBT.func_150315_a(s);

                    if (!(nbtbase instanceof NBTTagCompound)) {
                        func_152373_a(sender, this, "commands.give.tagError", new Object[] { "Not a valid tag" });
                        return;
                    }

                    itemstack.setTagCompound((NBTTagCompound) nbtbase);
                } catch (NBTException nbtexception) {
                    func_152373_a(sender, this, "commands.give.tagError", new Object[] { nbtexception.getMessage() });
                    return;
                }
            }

            EntityItem entityitem = entityplayermp.dropPlayerItemWithRandomChoice(itemstack, false);
            entityitem.delayBeforeCanPickup = 0;
            entityitem.func_145797_a(entityplayermp.getCommandSenderName());
            func_152373_a(
                sender,
                this,
                "commands.give.success",
                new Object[] { itemstack.func_151000_E(), Integer.valueOf(i), entityplayermp.getCommandSenderName() });
        }
    }

    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length == 1 ? getListOfStringsMatchingLastWord(args, this.getPlayers())
            : (args.length == 2 ? getListOfStringsFromIterableMatchingLastWord(args, Utils.getModItems()) : null);
    }

    protected String[] getPlayers() {
        return MinecraftServer.getServer()
            .getAllUsernames();
    }

    /**
     * Return whether the specified command parameter index is a username parameter.
     */
    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }
}
