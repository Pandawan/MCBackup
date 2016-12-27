package xyz.pandawan.mc.backup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OSUtils {

    public static String customPath = "";

    public static String GetDate() {
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy_HH-mm");
        Date dateobj = new Date();

        return (df.format(dateobj));
    }

    /// Converts the given path to work with the current OS
    public static String ConvertPathForOS(String path) {
        String newPath = "";

        if (GetOS() == OS.Windows)
            newPath = path.replace('/', '\\');
        else
            newPath = path;

        return newPath;
    }

    public static String ConvertSlashToBackslash(String path) {
        String newPath = "";
        if (GetOS() == OS.Windows)
            newPath = path.replace("\\", "\\\\");
        else
            newPath = path;

        return newPath;
    }

    /// Returns the basic .minecraft folder
    public static String GetPath() {

        if (customPath != "")
            return customPath;

        OS os = GetOS();

        // Give Windows Path
        if (os == OS.Windows) {
            return System.getenv("APPDATA") + "\\.minecraft";
        }
        // Give Mac Path
        else if (os == OS.Mac) {
            return System.getProperty("user.home") + "/Library/Application Support/minecraft";
        }
        // Not Win or Mac, most likely on Linux
        else {
            return "~/.minecraft";
        }
    }

    /// Returns the current OS
    public static OS GetOS() {
        String osName = System.getProperty("os.name").toLowerCase();

        // OS is Win
        if (osName.contains("win")) {
            return OS.Windows;
        }
        // OS is Mac
        else if (osName.contains("mac")) {
            return OS.Mac;
        }
        // Not Win or Mac, most likely on Linux
        else {
            return OS.Linux;
        }
    }

    public enum OS {Windows, Mac, Linux}
}
