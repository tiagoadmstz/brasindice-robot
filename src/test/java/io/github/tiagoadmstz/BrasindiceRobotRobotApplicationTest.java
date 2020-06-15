package io.github.tiagoadmstz;

import io.github.tiagoadmstz.config.Configuration;
import io.github.tiagoadmstz.robots.BrasindiceRobot;
import io.github.tiagoadmstz.util.ConfigurationFileUtil;
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
        assertDoesNotThrow(() -> new BrasindiceRobot().getLastEditonFileName());
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
    public void openBrasindiceSoftwareTest() {
        new WindowsUtil().openBrasindiceSoftwareAndConfigureDatabaseV2();
    }

}
