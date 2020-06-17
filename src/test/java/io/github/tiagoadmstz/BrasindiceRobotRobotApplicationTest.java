package io.github.tiagoadmstz;

import io.github.tiagoadmstz.config.Configuration;
import io.github.tiagoadmstz.robots.BrasindiceRobot;
import io.github.tiagoadmstz.util.ConfigurationFileUtil;
import io.github.tiagoadmstz.util.KeyboardUtil;
import io.github.tiagoadmstz.util.WindowsUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BrasindiceRobotRobotApplicationTest {

    Logger logger = LoggerFactory.getLogger(BrasindiceRobotRobotApplicationTest.class);

    @Test
    @Order(0)
    public void configurationFileUtilTest() {
        logger.info(() -> "Creating configuration util class");
        ConfigurationFileUtil configurationFileUtil = new ConfigurationFileUtil();
        logger.info(() -> "Creating configuration file");
        assertTrue(configurationFileUtil.create());
        logger.info(() -> "Load configurations and parse to object");
        Configuration configuration = configurationFileUtil.load();
        assertNotNull(configuration);
    }

    @Test
    @Order(1)
    public void createBrasindiceInstallationPathTest() {
        logger.info(() -> "Creating installation path");
        assertTrue(new BrasindiceRobot().createInstallationPath());
    }

    @Test
    @Order(2)
    public void downloadBrasindiceSoftwareAndUnrarTest() {
        logger.info(() -> "Downloading software and unrar");
        assertTrue(new BrasindiceRobot().downloadBrasindiceSoftwareAndUnrar());
    }

    @Test
    @Order(3)
    public void loginTest() {
        logger.info(() -> "Makes login and get cookie values");
        assertNotNull(new BrasindiceRobot().loginAndGetCookie());
    }

    @Test
    @Order(4)
    public void getLastEditionTest() {
        logger.info(() -> "Finds last edition number and makes file to download");
        assertDoesNotThrow(() -> new BrasindiceRobot().getLastEditonFileNameAndDate());
    }

    @Test
    @Order(5)
    public void downloadBransindiceDatabaseTest() {
        logger.info(() -> "Downloading database file and saving into installation folder");
        new BrasindiceRobot().downloadBrasindiceDatabase();
    }

    @Test
    @Order(6)
    public void createDesktopShortcutTest() {
        logger.info(() -> "Creating desktop shortcut");
        assertTrue(new WindowsUtil().createDesktopShortcut());
    }

    @Test
    @Order(7)
    public void keyboardUtilTest() {
        logger.info(() -> "Parse string to keyEvent array");
        assertArrayEquals(new Integer[][]{
                {66, 1, 16}, {82, 1}, {65, 1}, {83, 1}, {73, 1}, {78, 1}, {68, 1}, {73, 1}, {67, 1}, {69, 1}
        }, KeyboardUtil.parseStringToKeyEventArray("Brasindice"));
        assertArrayEquals(new Integer[][]{
                {80, 1, 16}, {77, 1, 16}, {67, 1, 16}, {45, 1, 16}, {48, 1}, {53, 1}, {48, 1}, {54, 1}, {50, 1}, {48, 1}, {50, 1}, {48, 1}
        }, KeyboardUtil.parseStringToKeyEventArray("PMC_05062020"));
    }

    @Test
    @Order(8)
    public void openBrasindiceSoftwareTest() {
        logger.info(() -> "Open Brasindice software and configure database");
        new WindowsUtil().openBrasindiceSoftwareAndConfigureDatabaseV2();
    }

    @Test
    @Order(9)
    public void exportPmcAndPfbFileTest() {
        BrasindiceRobot brasindiceRobot = new BrasindiceRobot();
        new WindowsUtil().openBrasindiceSoftware();
        logger.info(() -> "Export pmc, pfb, solution and material file from current database version");
        assertTrue(brasindiceRobot.exportBrasindiceFiles());
    }

}
