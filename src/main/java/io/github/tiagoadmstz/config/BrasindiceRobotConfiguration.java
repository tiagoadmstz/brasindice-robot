package io.github.tiagoadmstz.config;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

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
    @SerializedName("delete-exported-files")
    private Boolean deleteExportedFiles = false;
    @SerializedName("save-into-network-database")
    private Boolean saveIntoNetworkDatabase = false;
    @SerializedName("export-file-names")
    private ExportDataFileConfiguration exportDataFile = new ExportDataFileConfiguration();
    @SerializedName("csps-user")
    private String cspsUser = "PLANO";
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

    /**
     * Verify if installed edition is updated
     *
     * @param lastEdition number of the last edition to verify in the configuration file
     * @return true if updated
     */
    public boolean isUptaded(String lastEdition) {
        return this.lastEdition.equals(lastEdition);
    }

    /**
     * Verify if is first installation
     *
     * @return true if is first installation
     */
    public boolean isFirstInstallation() {
        return lastEdition == null || "".equals(lastEdition);
    }

    public File getAtualDatabaseFile() {
        return new File(setupPath.getPath() + "/" + lastEdition);
    }

    public String[] getExportFilesNames() {
        return new String[]{
                getFormattedExportFileName(exportDataFile.getPmc()),
                getFormattedExportFileName(exportDataFile.getPfb()),
                getFormattedExportFileName(exportDataFile.getSolucao()),
                getFormattedExportFileName(exportDataFile.getMaterial())
        };
    }

    public List<File> getExportFiles() {
        return Arrays.stream(getExportFilesNames())
                .map(fileName -> new File(setupPath + "/" + fileName + ".txt"))
                .collect(Collectors.toList());
    }

    /**
     * Get formatted file name
     *
     * @param fileName name of the file
     * @return String with final export file name - Ex: Material_20072020
     */
    private String getFormattedExportFileName(String fileName) {
        return fileName.replaceAll("_.*", "_") + getLastEditionDate().format(DateTimeFormatter.ofPattern(fileName.replaceAll(".*_", "")));
    }

    public boolean isFilesExported() {
        boolean result = false;
        for (String fileName : getExportFilesNames()) {
            result = new File(setupPath + "/" + fileName + ".txt").exists();
        }
        return result;
    }

}
