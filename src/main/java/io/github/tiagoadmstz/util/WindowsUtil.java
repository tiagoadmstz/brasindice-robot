package io.github.tiagoadmstz.util;

import io.github.tiagoadmstz.config.BrasindiceRobotConfiguration;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class WindowsUtil {

    private final String batFilePath;
    private final BrasindiceRobotConfiguration brasindiceRobotConfiguration;

    public WindowsUtil() {
        this.brasindiceRobotConfiguration = new ConfigurationFileUtil().load();
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
                        "echo oLink.TargetPath = \"" + brasindiceRobotConfiguration.getSetupPath() + "\\Brasindice.exe\" >> CreateShortcut.vbs\n" +
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
            Runtime.getRuntime().exec(brasindiceRobotConfiguration.getSetupPath() + "\\Brasindice.exe");
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

    /**
     * Opens Brasindice software and configure database
     */
    public void openBrasindiceSoftwareAndConfigureDatabaseV2() {
        try {
            openBrasindiceSoftware();
            executeCommands(new Integer[][]{{null, 5000}, {KeyEvent.VK_ALT, 1}, {KeyEvent.VK_RIGHT, 3}, {KeyEvent.VK_ENTER, 3}});
            executeCommands(getBasicCommandsFileChooser());
            executeCommands(new Integer[][]{{KeyEvent.VK_DOWN, 1}, {KeyEvent.VK_ENTER, 1}, {null, 15000}, {KeyEvent.VK_ENTER, 1}});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Export Brasindice Files
     *
     * @param pmcFileName      String with pmc file name
     * @param pfbFileName      String with pfb file name
     * @param solutionFileName String with solution file name
     * @param materialFileName String with material file name
     */
    public void exportBrasindiceFiles(String pmcFileName, String pfbFileName, String solutionFileName, String materialFileName) {
        Process process = openBrasindiceSoftware();
        openMedicinesFileExportFrame(pmcFileName, pfbFileName);
        openSolutionFileExportFrame(solutionFileName);
        openMaterialFileExportFrame(materialFileName);
        if (process != null) process.destroy();
    }

    /**
     * Export pmc and pfb data
     *
     * @param pmcFileName
     * @param pfbFileName
     */
    private void openMedicinesFileExportFrame(String pmcFileName, String pfbFileName) {
        try {
            int count = 0;
            do {
                if (count == 0) {
                    executeCommands(getCommandsToOpenExportFileFrame("pmc"));
                    executeCommands(new Integer[][]{{KeyEvent.VK_TAB, 6}});
                    confirmExportWithMousePointer();
                    executeCommands(KeyboardUtil.parseStringToKeyEventArray(pmcFileName));
                    executeCommands(getBasicCommandsFileChooser());
                    executeCommands(new Integer[][]{{KeyEvent.VK_TAB, 4}, {KeyEvent.VK_SPACE, 1}, {null, 10000}, {KeyEvent.VK_ENTER, 1}});
                } else {
                    executeCommands(getCommandsToOpenExportFileFrame("pfb"));
                    executeCommands(new Integer[][]{{KeyEvent.VK_TAB, 6}, {KeyEvent.VK_DOWN, 1}});
                    confirmExportWithMousePointer();
                    executeCommands(KeyboardUtil.parseStringToKeyEventArray(pfbFileName));
                    executeCommands(new Integer[][]{{KeyEvent.VK_TAB, 2}, {KeyEvent.VK_SPACE, 1}, {null, 10000}, {KeyEvent.VK_ENTER, 1}});
                }
                count++;
            } while (count < 2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Export solution data
     *
     * @param solutionFileName
     */
    private void openSolutionFileExportFrame(String solutionFileName) {
        try {
            executeCommands(getCommandsToOpenExportFileFrame("solucao"));
            executeCommands(new Integer[][]{{KeyEvent.VK_TAB, 1}});
            confirmExportWithMousePointer();
            executeCommands(KeyboardUtil.parseStringToKeyEventArray(solutionFileName));
            executeCommands(new Integer[][]{{KeyEvent.VK_TAB, 2}, {KeyEvent.VK_SPACE, 1}, {null, 10000}, {KeyEvent.VK_ENTER, 1}});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Export material data
     *
     * @param materialFileName
     */
    private void openMaterialFileExportFrame(String materialFileName) {
        try {
            executeCommands(getCommandsToOpenExportFileFrame("material"));
            executeCommands(new Integer[][]{{KeyEvent.VK_TAB, 1}});
            confirmExportWithMousePointer();
            executeCommands(KeyboardUtil.parseStringToKeyEventArray(materialFileName));
            executeCommands(new Integer[][]{{KeyEvent.VK_TAB, 2}, {KeyEvent.VK_SPACE, 1}, {null, 10000}, {KeyEvent.VK_ENTER, 1}});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Uses mouse pointer to confirm export
     */
    private void confirmExportWithMousePointer() {
        try {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Robot robot = new Robot();
            robot.delay(2000);
            robot.mouseMove(100, (((int) screenSize.getHeight() / 4) * 3) - 30);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(2000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Opens Brasindice software
     */
    public Process openBrasindiceSoftware() {
        try {
            return Runtime.getRuntime().exec(brasindiceRobotConfiguration.getSetupPath() + "\\Brasindice.exe");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Execute keyboards commands
     *
     * @param commands arrays with commands
     */
    public void executeCommands(Integer[][] commands) {
        try {
            Robot robot = new Robot();
            for (Integer[] command : commands) {
                if (command[0] == null) robot.delay(command[1]);
                else {
                    if (command.length == 3) robot.keyPress(command[2]);
                    for (int r = 0; r < command[1]; r++) doType(robot, command[0]);
                    if (command.length == 3) robot.keyRelease(command[2]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Press and release informed key
     *
     * @param robot    Robot instance
     * @param keyEvent int KeyEvent
     */
    private void doType(Robot robot, int keyEvent) {
        robot.keyPress(keyEvent);
        robot.keyRelease(keyEvent);
    }

    /**
     * Creates array with commands to open export frame
     *
     * @param fileName
     * @return
     */
    private Integer[][] getCommandsToOpenExportFileFrame(String fileName) {
        return new Integer[][]{
                {null, 5000},
                {KeyEvent.VK_ALT, 1},
                {KeyEvent.VK_RIGHT, 3},
                {KeyEvent.VK_DOWN, fileName.contains("pmc") || fileName.contains("pfb") ? 3 : (fileName.contains("solucao") ? 4 : 5)},
                {KeyEvent.VK_ENTER, 1}
        };
    }

    /**
     * Creates array with basic commands to navigate on file chooser
     *
     * @return Integer[][]
     */
    private Integer[][] getBasicCommandsFileChooser() {
        return ArrayUtils.addAll(ArrayUtils.addAll(new Integer[][]{
                        {null, 2000},
                        {KeyEvent.VK_TAB, 6}, {KeyEvent.VK_DOWN, 3}, {KeyEvent.VK_SPACE, 1},
                        {null, 2000},
                        {KeyEvent.VK_TAB, 1}},
                ArrayUtils.addAll(KeyboardUtil.parseStringToKeyEventArray(brasindiceRobotConfiguration.getLocalDiskName()), new Integer[][]{
                        {KeyEvent.VK_ENTER, 1},
                        {null, 2000},
                        {KeyEvent.VK_DOWN, 1}})),
                ArrayUtils.addAll(KeyboardUtil.parseStringToKeyEventArray("brasindice"),
                        new Integer[][]{{KeyEvent.VK_ENTER, 1}, {null, 2000}})
        );
    }

}
