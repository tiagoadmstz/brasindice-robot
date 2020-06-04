package io.github.tiagoadmstz.util;

import com.google.gson.Gson;
import io.github.tiagoadmstz.models.Configuration;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.Base64;
import java.util.stream.Collectors;

public class ConfigurationFileUtil {

    private final File file;
    private final String privateKey;

    public ConfigurationFileUtil() {
        this.file = new File(System.getProperty("user.dir") + "/config/configuration.json");
        this.privateKey = "br4S1nD1c3";
    }

    /**
     * Creates configuration path and file if it not exist
     *
     * @return true if successful
     */
    public boolean create() {
        try {
            if (!file.exists()) {
                FileWriter fileWriter = new FileWriter(file);
                String login = JOptionPane.showInputDialog("Informe o login");
                String password = JOptionPane.showInputDialog("Informe a senha");
                fileWriter.write(new Gson().toJson(new Configuration(login, Base64.getEncoder().encodeToString(password.getBytes()))));
                fileWriter.close();
            }
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
    public Configuration load() {
        try {
            Configuration configuration = new Gson().fromJson(Files.readAllLines(file.toPath()).stream().collect(Collectors.joining()), Configuration.class);
            configuration.setPassword(new String(Base64.getDecoder().decode(configuration.getPassword())));
            return configuration;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
