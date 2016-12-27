package xyz.pandawan.mc.backup;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MCBackup {


    public static void main(String[] args) {

        System.out.println(OSUtils.GetPath());

        IO.CreateDir(new File(OSUtils.ConvertPathForOS(OSUtils.GetPath() + "/backups")));

        ApplyConfig();

        System.out.println(OSUtils.GetPath());

        GUI gui = new GUI();
    }

    /// Backup the current world from /saves/saveName to the path /backups/backupName
    public static void BackupWorld(String saveName, String backupName) {
        String savePath = OSUtils.ConvertPathForOS(OSUtils.GetPath() + "/saves/" + saveName + "/");
        String backupPath = OSUtils.ConvertPathForOS(OSUtils.GetPath() + "/backups/" + backupName + "/");

        int result = IO.Copy(new File(savePath), new File(backupPath));

        if (result == 0) {
            System.out.println("Successfully backed up world!");
        }
    }

    /// Load the Backup of the backup at /backups/backupName to /saves/saveName
    public static void LoadBackup(String backupName, String saveName) {
        String savePath = OSUtils.ConvertPathForOS(OSUtils.GetPath() + "/saves/" + saveName + "/");
        String backupPath = OSUtils.ConvertPathForOS(OSUtils.GetPath() + "/backups/" + backupName + "/");

        IO.DeleteDir(new File(savePath));
        System.out.println("Successfully deleted save " + saveName + "!");
        int loadResult = IO.Copy(new File(backupPath), new File(savePath));

        if (loadResult == 0) {
            System.out.println("Successfully loaded backup " + backupName + "!");
        }
    }

    /// Gets all the backup folders in the /backups directory
    public static File[] GetAllBackups() {
        String backupPath = OSUtils.ConvertPathForOS(OSUtils.GetPath() + "/backups/");

        return IO.GetDirectories(new File(backupPath));
    }

    /// Gets all the backup folders in the /backups directory
    public static File[] GetAllSaves() {
        String savesPath = OSUtils.ConvertPathForOS(OSUtils.GetPath() + "/saves/");

        return IO.GetDirectories(new File(savesPath));
    }

    /// Delete the given backup
    public static void DeleteBackup(String backupName) {
        String backupPath = OSUtils.ConvertPathForOS(OSUtils.GetPath() + "/backups/" + backupName + "/");

        IO.DeleteDir(new File(backupPath));
    }

    public static void DeleteSave(String saveName) {
        String savePath = OSUtils.ConvertPathForOS(OSUtils.GetPath() + "/saves/" + saveName + "/");

        IO.DeleteDir(new File(savePath));
    }

    public static void ApplyConfig() {
        // Check if file exists
        if (IO.GetConfigFile()) {
            String content = IO.GetFileContent(IO.GetConfig());

            if (content.equals("") || content.equals(" ") || content.equals(null) || content == null) {
                MakeConfig();
                return;
            }

            // Check if not same as default path
            if (!OSUtils.GetPath().equals(content)) {
                OSUtils.customPath = content;
            }
        }
        // File doesn't exist, ask for first time config
        else {
            MakeConfig();
        }


    }

    public static void MakeConfig() {
        JOptionPane.showMessageDialog(null, "On the next window, press Cancel if you are using the default .minecraft folder. If not, select it with the File Chooser.", "Info", JOptionPane.INFORMATION_MESSAGE);

        try {
            EventQueue.invokeAndWait(new Runnable() {

                @Override
                public void run() {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Choose your .minecraft folder");
                    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    chooser.setAcceptAllFileFilterUsed(false);
                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        OSUtils.customPath = chooser.getSelectedFile().getAbsolutePath();
                        System.out.println("Using Custom Path " + OSUtils.customPath);
                        JOptionPane.showMessageDialog(null, "Using Custom Path " + OSUtils.customPath);
                    }
                }

            });
        } catch (Exception e) {
            System.out.println(e);
        }

        if (OSUtils.customPath == "" || OSUtils.customPath == null) {
            JOptionPane.showMessageDialog(null, "Using default .minecraft " + OSUtils.GetPath());
        }

        // Write to config file
        List<String> config = Arrays.asList(OSUtils.ConvertSlashToBackslash(OSUtils.GetPath()));

        IO.WriteToFile(config, IO.GetConfig());
    }

}
