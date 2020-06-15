package io.github.tiagoadmstz.util;

import io.github.tiagoadmstz.config.Configuration;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WindowsUtil {

    private final String batFilePath;
    private final Configuration configuration;

    public WindowsUtil() {
        this.configuration = new ConfigurationFileUtil().load();
        this.batFilePath = System.getProperty("user.dir") + "/config/shortcut.bat";
    }

    /**
     * Create desktop shortcut for Brasindice software
     *
     * @return true if successful
     */
    public boolean createDesktopShortcut() {
        try {
            if (createBatFile()) {
                Runtime.getRuntime().exec(batFilePath);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Create bat file
     *
     * @return true if successful
     */
    private boolean createBatFile() {
        try {
            File batFile = new File(batFilePath);
            if (!batFile.exists()) {
                batFile.createNewFile();
                String content = "@echo off\n" +
                        "echo Set oWS = WScript.CreateObject(\"WScript.Shell\") > CreateShortcut.vbs\n" +
                        "echo sLinkFile = \"%HOMEDRIVE%%HOMEPATH%\\Desktop\\Brasindice.lnk\" >> CreateShortcut.vbs\n" +
                        "echo Set oLink = oWS.CreateShortcut(sLinkFile) >> CreateShortcut.vbs\n" +
                        "echo oLink.TargetPath = \"" + configuration.getSetupPath() + "\\Brasindice.exe\" >> CreateShortcut.vbs\n" +
                        "echo oLink.Save >> CreateShortcut.vbs\n" +
                        "cscript CreateShortcut.vbs\n" +
                        "del CreateShortcut.vbs";
                Files.write(batFile.toPath(), content.getBytes());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Opens Brasindice software and configure database
     */
    public void openBrasindiceSoftwareAndConfigureDatabase() {
        try {
            Runtime.getRuntime().exec(configuration.getSetupPath() + "\\Brasindice.exe");
            Robot robot = new Robot();
            Integer[][] commands = new Integer[][]{
                    {null, 5000},
                    {KeyEvent.VK_ALT, 1}, {KeyEvent.VK_RIGHT, 3}, {KeyEvent.VK_ENTER, 3},
                    {null, 2000},
                    {KeyEvent.VK_TAB, 6}, {KeyEvent.VK_DOWN, 3}, {KeyEvent.VK_SPACE, 1},
                    {null, 2000},
                    {KeyEvent.VK_TAB, 1}, {KeyEvent.VK_ENTER, 1},
                    {null, 2000},
                    {KeyEvent.VK_DOWN, 1},
                    {KeyEvent.VK_B, 1}, {KeyEvent.VK_R, 1}, {KeyEvent.VK_A, 1}, {KeyEvent.VK_S, 1}, {KeyEvent.VK_I, 1},
                    {KeyEvent.VK_N, 1}, {KeyEvent.VK_D, 1}, {KeyEvent.VK_I, 1}, {KeyEvent.VK_C, 1}, {KeyEvent.VK_E, 1},
                    {KeyEvent.VK_ENTER, 1},
                    {null, 2000},
                    {KeyEvent.VK_DOWN, 1}, {KeyEvent.VK_ENTER, 1},
                    {null, 15000},
                    {KeyEvent.VK_ENTER, 1}
            };
            for (Integer[] command : commands) {
                if (command[0] == null) robot.delay(command[1]);
                else {
                    for (int r = 0; r < command[1]; r++) {
                        robot.keyPress(command[0]);
                        robot.keyRelease(command[0]);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
