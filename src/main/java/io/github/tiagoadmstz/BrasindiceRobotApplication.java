package io.github.tiagoadmstz;

import io.github.tiagoadmstz.config.Configuration;
import io.github.tiagoadmstz.robots.BrasindiceRobot;
import io.github.tiagoadmstz.util.ConfigurationFileUtil;

public class BrasindiceRobotApplication {

    public static void main(String[] args) {
        new BrasindiceRobotApplication().start();
    }

    private void start() {
        ConfigurationFileUtil configurationFileUtil = new ConfigurationFileUtil();
        Configuration load = configurationFileUtil.load();
        BrasindiceRobot brasindiceRobot = new BrasindiceRobot();
        if (!load.getIsInstaled()) {
            brasindiceRobot.createInstallationPath();
            brasindiceRobot.downloadBrasindiceSoftwareAndUnrar();
        }
        brasindiceRobot.downloadBrasindiceDatabase();
    }

}
