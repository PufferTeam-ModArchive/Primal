package net.pufferlab.primal.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;

import org.apache.commons.io.FileUtils;

public class IOUtils {

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

    public static void downloadFile(String urlTxt, File out) throws IOException {
        try {
            URL url = new URL(urlTxt);
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
}
