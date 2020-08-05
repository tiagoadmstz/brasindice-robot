package io.github.tiagoadmstz;

import io.github.tiagoadmstz.robots.BrasindiceRobot;

public class BrasindiceRobotApplication {

    public static void main(String[] args) {
        new BrasindiceRobotApplication().start();
    }

    private void start() {
        new BrasindiceRobot().installOrUpdateAndExportDataFilesAndInsertInNetworkDataBase();
    }

}
