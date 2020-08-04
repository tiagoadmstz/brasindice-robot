package io.github.tiagoadmstz.util;

import com.google.gson.Gson;
import io.github.tiagoadmstz.config.BrasindiceRobotConfiguration;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Base64;
import java.util.stream.Collectors;

public class ConfigurationFileUtil {

    private final File file;

    public ConfigurationFileUtil() {
        this.file = new File(System.getProperty("user.dir") + "/config/configuration.json");
    }

    /**
     * Creates configuration path and file if it not exist
     *
     * @return true if successful
     */
    public boolean create() {
        try {
            if (!file.exists()) {
                String login = JOptionPane.showInputDialog("Informe o login");
                String password = JOptionPane.showInputDialog("Informe a senha");
                save(new BrasindiceRobotConfiguration(login, password));
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Save configuration file
     *
     * @return true if successful
     */
    public boolean save(BrasindiceRobotConfiguration brasindiceRobotConfiguration) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new Gson().toJson(brasindiceRobotConfiguration.cloneWithEncodedPassword()));
            fileWriter.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Loads content of the configuration file
     *
     * @return Configuration class if successful else null
     */
    public BrasindiceRobotConfiguration load() {
        try {
            BrasindiceRobotConfiguration brasindiceRobotConfiguration = new Gson().fromJson(IOUtils.readLines(new FileInputStream(file)).stream().collect(Collectors.joining()), BrasindiceRobotConfiguration.class);
            brasindiceRobotConfiguration.setPassword(new String(Base64.getDecoder().decode(brasindiceRobotConfiguration.getPassword())));
            return brasindiceRobotConfiguration;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
