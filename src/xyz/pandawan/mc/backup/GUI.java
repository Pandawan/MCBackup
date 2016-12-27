package xyz.pandawan.mc.backup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GUI extends JFrame {
    private JPanel mainPanel;
    private JButton SaveButton;
    private JButton LoadButton;
    private JLabel titleText;
    private JList listOfFiles;
    private JLabel info;
    private JButton deleteBackup;
    private JButton Credit;
    private JList savesList;
    private JButton deleteWorld;

    public GUI() {
        // Create the window
        super("MC Backup");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setContentPane(mainPanel);


        ImageIcon caIcon = new ImageIcon(getClass().getResource("/icon.png"));

        setIconImage(caIcon.getImage());

        info.setText("Welcome!");


        // Apply the content
        if (GetBackups() != null)
            listOfFiles.setListData(GetBackups());

        // Apply the content
        if (GetSaves() != null)
            savesList.setListData(GetSaves());


        // Add event listener
        SaveButton.addActionListener((ActionEvent event) -> {

            if (savesList.isSelectionEmpty()) {
                info.setText("No Save World selected!");
                return;
            }

            info.setText("Creating Backup for selected world...");

            MCBackup.BackupWorld(GetSaves()[savesList.getSelectedIndex()].toString(), GetSaves()[savesList.getSelectedIndex()].toString() + "/" + OSUtils.GetDate());
            if (GetBackups() != null)
                listOfFiles.setListData(GetBackups()); // Update the list
            info.setText("Done!");
        });

        // Add event listener
        LoadButton.addActionListener((ActionEvent event) -> {

            if (listOfFiles.isSelectionEmpty()) {
                info.setText("No Backup World selected!");
                return;
            }

            info.setText("Loading current backup...");
            MCBackup.LoadBackup(GetBackups()[listOfFiles.getSelectedIndex()].worldName + "/" + GetBackups()[listOfFiles.getSelectedIndex()].date, GetBackups()[listOfFiles.getSelectedIndex()].worldName);
            if (GetSaves() != null)
                savesList.setListData(GetSaves()); // Update the list
            info.setText("Done!");
        });

        // Add event listener
        Credit.addActionListener((ActionEvent event) -> {
            try {
                Desktop.getDesktop().browse(new URI("http://twitter.com/PandawanYT"));
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        // Add event listener
        deleteBackup.addActionListener((ActionEvent event) -> {
            if (GetBackups() != null) {
                if (listOfFiles.isSelectionEmpty()) {
                    info.setText("No backup selected!");
                    return;
                }
                info.setText("Deleting selected backup...");
                int selectedOption = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete backup '" + GetBackups()[listOfFiles.getSelectedIndex()].toString() + "' ?",
                        "Delete?",
                        JOptionPane.YES_NO_OPTION);
                if (selectedOption == JOptionPane.YES_OPTION) {
                    MCBackup.DeleteBackup(GetBackups()[listOfFiles.getSelectedIndex()].file.getName());
                    info.setText("Backup successfully deleted!");
                    if (GetBackups() != null)
                        listOfFiles.setListData(GetBackups());
                } else {
                    info.setText("Canceled.");
                }
            } else {
                info.setText("There are no backups to delete!");
            }
        });

        // Add event listener
        deleteWorld.addActionListener((ActionEvent event) -> {
            if (GetSaves() != null) {
                if (savesList.isSelectionEmpty()) {
                    info.setText("No world selected!");
                    return;
                }
                info.setText("Deleting selected world...");
                int selectedOption = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to delete world '" + GetSaves()[savesList.getSelectedIndex()].toString() + "' ?",
                        "Delete?",
                        JOptionPane.YES_NO_OPTION);
                if (selectedOption == JOptionPane.YES_OPTION) {
                    MCBackup.DeleteSave(GetSaves()[savesList.getSelectedIndex()].toString());
                    info.setText("Backup successfully deleted!");
                    if (GetSaves() != null)
                        savesList.setListData(GetSaves());
                } else {
                    info.setText("Canceled.");
                }
            } else {
                info.setText("There are no worlds to delete!");
            }
        });

        // Finish setup
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /// Create a list of BackupNames of all current backups for the list
    public BackupName[] GetBackups() {
        File[] files = MCBackup.GetAllBackups();
        info.setText("Searching for Backups");
        if (files != null && files.length > 0) {
            List<BackupName> fileNames = new ArrayList<BackupName>();

            for (File f : files) {
                for (File child : IO.GetDirectories(f)) {
                    BackupName b = new BackupName();
                    b.file = f;
                    b.date = child.getName();
                    b.worldName = f.getName();
                    fileNames.add(b);
                }
            }
            info.setText("Backups found!");

            return fileNames.toArray(new BackupName[fileNames.size()]);
        } else {
            info.setText("No Backup found...");
            return null;
        }
    }

    /// Create a list of BackupNames of all current backups for the list
    public Save[] GetSaves() {
        File[] files = MCBackup.GetAllSaves();
        info.setText("Searching for Saves");
        if (files != null && files.length > 0) {
            Save[] fileNames = new Save[files.length];

            int i = 0;
            for (File f : files) {
                Save b = new Save();
                b.file = files[i];
                b.name = files[i].getName();
                fileNames[i] = b;
                i++;
            }
            info.setText("Saves found!");

            return fileNames;
        } else {
            info.setText("No Saves found...");
            return null;
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 3, new Insets(5, 5, 5, 5), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(10, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        titleText = new JLabel();
        titleText.setFont(new Font(titleText.getFont().getName(), titleText.getFont().getStyle(), 22));
        titleText.setText("Backup Manager");
        panel1.add(titleText, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        LoadButton = new JButton();
        LoadButton.setText("> Load Selected Backup >");
        panel1.add(LoadButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        SaveButton = new JButton();
        SaveButton.setHideActionText(false);
        SaveButton.setText("< Save World As Backup <");
        panel1.add(SaveButton, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, 30), null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setIcon(new ImageIcon(getClass().getResource("/med_icon.png")));
        label1.setText("");
        panel2.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteBackup = new JButton();
        deleteBackup.setText("Delete Selected Backup");
        panel1.add(deleteBackup, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Credit = new JButton();
        Credit.setText("By Pandawan");
        panel1.add(Credit, new com.intellij.uiDesigner.core.GridConstraints(9, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteWorld = new JButton();
        deleteWorld.setText("Delete Selected World");
        panel1.add(deleteWorld, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        info = new JLabel();
        info.setText("");
        panel1.add(info, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        mainPanel.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 2, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        savesList = new JList();
        savesList.setLayoutOrientation(0);
        savesList.setSelectionMode(0);
        scrollPane1.setViewportView(savesList);
        final JScrollPane scrollPane2 = new JScrollPane();
        mainPanel.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        listOfFiles = new JList();
        listOfFiles.setSelectionMode(0);
        scrollPane2.setViewportView(listOfFiles);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

    /// Used to display different backups on the GUI
    public class BackupName {
        File file;

        String date;
        String worldName;

        public String toString() {
            String month = date.substring(0, 2);
            String day = date.substring(3, 5);
            String year = date.substring(6, 10);
            String hour = date.substring(11, 13);
            String min = date.substring(14, 16);

            String formatDate = month + "/" + day + "/" + year + " at " + hour + ":" + min;

            return "Backup " + worldName + " on " + formatDate;
        }
    }

    /// Used to display different backups on the GUI
    public class Save {
        File file;
        String name;

        public String toString() {
            return name;
        }
    }
}
