package net.pufferlab.primal.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.AxisAlignedBB;

import org.apache.commons.io.FileUtils;

import io.netty.buffer.ByteBuf;

public class IOUtils {

    public static File getResourceDir() {
        File rpDir = new File(Minecraft.getMinecraft().mcDataDir, "resourcepacks");

        if (!rpDir.exists()) rpDir.mkdirs();
        return rpDir;
    }

    public static File getConfigDir() {
        File rpDir = new File(Launch.minecraftHome, "config");

        if (!rpDir.exists()) rpDir.mkdirs();
        return rpDir;
    }

    public static File createResourceFile(String name, String extension) throws IOException {
        return new File(getResourceDir(), name + "." + extension);
    }

    public static File createConfigFile(String name) {
        return new File(getConfigDir(), name + ".cfg");
    }

    public static File createTempFile() throws IOException {
        return File.createTempFile("tmp_" + UUID.randomUUID() + "_", ".tmp");
    }

    public static String readFile(File file) throws IOException {
        return file.exists() ? FileUtils.readFileToString(file, "UTF-8")
            .trim() : null;
    }

    public static void writeFile(File file, String content) throws IOException {
        FileUtils.writeStringToFile(file, content.trim(), "UTF-8");
    }

    public static void copyFile(File from, File to) throws IOException {
        FileUtils.copyFile(from, to);
    }

    public static void downloadFile(String urlTxt, String extension, File out) throws IOException {
        try {
            URL url = new URL(urlTxt + "." + extension);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            try (InputStream in = connection.getInputStream()) {
                FileUtils.copyInputStreamToFile(in, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String sha256(File file) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        try (InputStream is = new FileInputStream(file)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = is.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
        }

        byte[] hash = digest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static void writeString(ByteBuf buf, String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        buf.writeInt(bytes.length); // write length first
        buf.writeBytes(bytes); // then write bytes
    }

    public static String readString(ByteBuf buf) {
        int length = buf.readInt(); // first read length
        byte[] bytes = new byte[length];
        buf.readBytes(bytes); // then read bytes
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static void writeBBList(ByteBuf buf, List<AxisAlignedBB> list) {
        buf.writeInt(list.size());
        for (AxisAlignedBB bb : list) {
            writeBB(buf, bb);
        }
    }

    public static List<AxisAlignedBB> readBBList(ByteBuf buf) {
        int length = buf.readInt();
        List<AxisAlignedBB> list = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            AxisAlignedBB bb = readBB(buf);
            list.add(bb);
        }
        return list;
    }

    public static void writeBB(ByteBuf buf, AxisAlignedBB bb) {
        buf.writeFloat((float) bb.minX);
        buf.writeFloat((float) bb.minY);
        buf.writeFloat((float) bb.minZ);
        buf.writeFloat((float) bb.maxX);
        buf.writeFloat((float) bb.maxY);
        buf.writeFloat((float) bb.maxZ);
    }

    public static AxisAlignedBB readBB(ByteBuf buf) {
        double minX = buf.readFloat();
        double minY = buf.readFloat();
        double minZ = buf.readFloat();
        double maxX = buf.readFloat();
        double maxY = buf.readFloat();
        double maxZ = buf.readFloat();
        return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
