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
     * Open brasindice software
     */
    public void openBrasindiceSoftware() {
        try {
            Process process = Runtime.getRuntime().exec(configuration.getSetupPath() + "\\Brasindice.exe");
            Robot robot = new Robot();
            robot.delay(5000);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_RIGHT);
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_RIGHT);
            robot.keyPress(KeyEvent.VK_RIGHT);
            robot.keyRelease(KeyEvent.VK_RIGHT);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_SPACE);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);

            robot.keyPress(KeyEvent.VK_B);
            robot.keyRelease(KeyEvent.VK_B);
            robot.keyPress(KeyEvent.VK_R);
            robot.keyRelease(KeyEvent.VK_R);
            robot.keyPress(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_A);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_S);
            robot.keyPress(KeyEvent.VK_I);
            robot.keyRelease(KeyEvent.VK_I);
            robot.keyPress(KeyEvent.VK_N);
            robot.keyRelease(KeyEvent.VK_N);
            robot.keyPress(KeyEvent.VK_D);
            robot.keyRelease(KeyEvent.VK_D);
            robot.keyPress(KeyEvent.VK_I);
            robot.keyRelease(KeyEvent.VK_I);
            robot.keyPress(KeyEvent.VK_C);
            robot.keyRelease(KeyEvent.VK_C);
            robot.keyPress(KeyEvent.VK_E);
            robot.keyRelease(KeyEvent.VK_E);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(15000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            //process.destroy();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void keyStrokeArray(Robot robot, int[][] keys) {
        for (int key = 0; key < keys.length; key++) {
            for (int repeat = 0; repeat < keys[key][1]; repeat++) {
                robot.keyPress(keys[key][0]);
                robot.keyRelease(keys[key][0]);
            }
        }
    }

}
