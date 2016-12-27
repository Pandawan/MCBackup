package xyz.pandawan.mc.backup;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class IO {

    /// Copies a file from File to File
    public static int Copy(File from, File to) {
        try {
            FileUtils.copyDirectory(from, to);
            return 0;
        } catch (IOException e) {
            System.out.println(e);
            return 1;
        }
    }

    public static int DeleteDir(File dir) {
        try {
            FileUtils.deleteDirectory(dir);
            return 0;
        } catch (IOException e) {
            System.out.println(e);
            return 1;
        }
    }

    public static File[] GetDirectories(File dir) {
        File[] directories = dir.listFiles(File::isDirectory);
        return directories;
    }

    public static void CreateDir(File dir) {
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public static boolean GetConfigFile() {
        File f = new File("config.txt");
        if (!f.exists()) {
            try {
                f.createNewFile();
                return false;
            } catch (IOException e) {
                System.out.println(e);
                return false;
            }
        } else {
            return true;
        }
    }

    public static File GetConfig() {
        return new File("config.txt");
    }

    public static String GetFileContent(File f) {
        try {
            return new String(Files.readAllLines(f.toPath()).get(0));
        } catch (IOException e) {
            System.out.println(e);
            return "";
        }
    }

    public static void WriteToFile(List<String> content, File f) {
        try {
            Files.write(f.toPath(), content, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
