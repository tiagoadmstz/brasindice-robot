package io.github.tiagoadmstz.config;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BrasindiceRobotConfiguration implements Serializable {

    private static final long serialVersionUID = 3663419321029583563L;
    @SerializedName("setup-path")
    private File setupPath = new File("C:/Brasindice");
    @SerializedName("local-disc-name")
    private String localDiskName = "OS";
    @SerializedName("winrar-path")
    private String winrarPath = "C:\\Program Files\\Winrar";
    @SerializedName("is-instaled")
    private Boolean isInstaled = false;
    @SerializedName("base-url")
    private String baseUrl = "http://www.brasindice.com.br";
    @SerializedName("url-login")
    private String urlLogin = "http://www.brasindice.com.br/Admin/LoginUsuario";
    @SerializedName("url-download")
    private String urlDownload = "http://www.brasindice.com.br/Arquivos/bras8-5.rar";
    @SerializedName("url-edition-archives-list")
    private String urlEditionsArchivesList = "http://www.brasindice.com.br/Admin/Arquivos";
    @SerializedName("url-uploads")
    private String urlUploads = "http://www.brasindice.com.br/Uploads";
    @SerializedName("last-edition")
    private String lastEdition = "";
    @SerializedName("last-edition-date")
    private String lastEditionDate = "";
    @SerializedName("remove-accetuation")
    private Boolean removeAccentuation = false;
    @SerializedName("export-file-names")
    private ExportDataFileConfiguration exportDataFile = new ExportDataFileConfiguration();
    private String login = "";
    private String password = "";

    public BrasindiceRobotConfiguration(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LocalDate getLastEditionDate() {
        return LocalDate.parse(lastEditionDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setLastEditionDate(LocalDate lastEditionDate) {
        this.lastEditionDate = lastEditionDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /**
     * Encrypted password to save into configuration file
     *
     * @return String with encoded password
     */
    public String getEncodedPassword() {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    /**
     * Creates a new instance of this class and copy all values with encrypted password
     *
     * @return new Configuration class cloned
     */
    public BrasindiceRobotConfiguration cloneWithEncodedPassword() {
        BrasindiceRobotConfiguration brasindiceRobotConfiguration = new BrasindiceRobotConfiguration();
        try {
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!"password".equals(field.getName()) && !"serialVersionUID".equals(field.getName())) {
                    Field targetField = brasindiceRobotConfiguration.getClass().getDeclaredField(field.getName());
                    targetField.setAccessible(true);
                    targetField.set(brasindiceRobotConfiguration, field.get(this));
                }
            }
            brasindiceRobotConfiguration.setPassword(getEncodedPassword());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return brasindiceRobotConfiguration;
    }

}
