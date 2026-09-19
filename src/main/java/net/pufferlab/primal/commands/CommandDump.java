package net.pufferlab.primal.commands;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.command.ICommandSender;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.pufferlab.primal.Mods;
import net.pufferlab.primal.Primal;
import net.pufferlab.primal.utils.BlockUtils;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;

public class CommandDump extends CommandSub {

    public static String[] arguments = new String[] { "", "isbrh", "te" };

    @Override
    public void handleCommand(ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();

        if (args.length == 1) {
            if (args[0].equals("isbrh")) {
                Primal.proxy.packet.sendClientCommand(this, sender, args);
            }
            if (args[0].equals("te")) {
                Map<?, ?> map = TileEntity.nameToClassMap;
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    String name = null;
                    Class<?> cl = null;
                    if (entry.getKey() instanceof String str) {
                        name = str;
                    }
                    if (entry.getValue() instanceof Class<?>cl0) {
                        cl = cl0;
                    }
                    if (cl == null) continue;
                    sendChatMessage(sender, "Class: " + cl.getSimpleName() + " Name: " + name);
                }
            }
        }
    }

    @Override
    public void handleClientCommand(ICommandSender sender, String[] args) {
        if (args.length == 1) {
            if (args[0].equals("isbrh")) {
                try {
                    Field field = RenderingRegistry.class.getDeclaredField("blockRenderers");
                    field.setAccessible(true);

                    @SuppressWarnings("unchecked")
                    Map<Integer, ISimpleBlockRenderingHandler> blockRenderers = (Map<Integer, ISimpleBlockRenderingHandler>) field
                        .get(RenderingRegistry.instance());

                    TIntList list = new TIntArrayList();
                    for (Map.Entry<Integer, ISimpleBlockRenderingHandler> entry : blockRenderers.entrySet()) {
                        Class<?> cl = entry.getValue()
                            .getClass();
                        String modName = Mods.getModIDFromClass(cl);
                        List<Block> blocks = BlockUtils.getBlocksWithRenderID(entry.getKey());
                        if (modName == null && !blocks.isEmpty()) {
                            modName = BlockUtils.getModId(blocks.get(0));
                        }
                        sendChatMessage(
                            sender,
                            "Mod: " + modName + " Class: " + cl.getSimpleName() + " ID: " + entry.getKey());
                        if (list.contains(entry.getKey())) {
                            sendChatMessage(sender, EnumChatFormatting.RED + "Duplicate entry: " + entry.getKey());
                        } else {
                            list.add(entry.getKey());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }

    @Override
    public String[] getSubArgs(String[] args) {
        return arguments;
    }

    @Override
    public String getCommandName() {
        return "dumpinfo";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }
}
