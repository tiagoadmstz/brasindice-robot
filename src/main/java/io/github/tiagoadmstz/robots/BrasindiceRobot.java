package io.github.tiagoadmstz.robots;

import br.com.dev.engine.date.Datas;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import io.github.tiagoadmstz.config.BrasindiceRobotConfiguration;
import io.github.tiagoadmstz.config.SpringContext;
import io.github.tiagoadmstz.dal.oracle.repositories.BrasindiceDataModelRepository;
import io.github.tiagoadmstz.util.ConfigurationFileUtil;
import io.github.tiagoadmstz.util.ImportBrasindiceUtil;
import io.github.tiagoadmstz.util.MESSAGES;
import io.github.tiagoadmstz.util.WindowsUtil;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.nio.file.Files;
import java.util.ListIterator;
import java.util.stream.Collectors;

public final class BrasindiceRobot {

    private BrasindiceRobotConfiguration brasindiceRobotConfiguration;
    private WebClient webClient;

    public BrasindiceRobot() {
        loadConfigurationFile();
    }

    public void installOrUpdateAndExportDataFilesAndInsertInNetworkDataBase() {
        if (brasindiceRobotConfiguration.getActiveInstallUpdate()) installOrUpdate();
        if (brasindiceRobotConfiguration.getActiveExportFiles()) exportDataFilesAndInsertInNetworkDataBase();
        System.exit(0);
    }

    /**
     * Install or update Brasindice software
     */
    public void installOrUpdate() {
        loadConfigurationFile();
        if (!brasindiceRobotConfiguration.getIsInstaled()) {
            createInstallationPath();
            downloadBrasindiceSoftwareAndUnrar();
        } else {
            downloadAndConfigureBrasindiceDatabase();
        }
    }

    private void loadConfigurationFile() {
        ConfigurationFileUtil configurationFileUtil = new ConfigurationFileUtil();
        if (brasindiceRobotConfiguration == null) {
            configurationFileUtil.create();
            brasindiceRobotConfiguration = configurationFileUtil.load();
        }
    }

    public void exportDataFilesAndInsertInNetworkDataBase() {
        if (MESSAGES.EXPORT_DATA_FILES()) {
            boolean exportSuccess = exportBrasindiceFiles();
            if (exportSuccess) {
                importBrasindiceFiles(brasindiceRobotConfiguration.getSaveIntoNetworkDatabase());
                if (brasindiceRobotConfiguration.getDeleteExportedFiles()) deleteExportedFiles();
            }
            MESSAGES.EXPORT_DATA_FILES_SUCCESS(exportSuccess);
        }
    }

    /**
     * Start webclient
     */
    private void initWebClient() {
        webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setPopupBlockerEnabled(false);
        webClient.getOptions().setTimeout(200000);
        webClient.getOptions().setThrowExceptionOnScriptError(true);
        webClient.waitForBackgroundJavaScript(90000);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setHomePage(brasindiceRobotConfiguration.getBaseUrl());
    }

    /**
     * Creates the path to install Brasindice system
     *
     * @return true if successful
     */
    public boolean createInstallationPath() {
        try {
            File installationPath = brasindiceRobotConfiguration.getSetupPath();
            if (!installationPath.exists()) {
                Files.createDirectories(brasindiceRobotConfiguration.getSetupPath().toPath());
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Makes download zip file of the Brasindice software and unrar
     */
    public boolean downloadBrasindiceSoftwareAndUnrar() {
        try {
            File rarFile = new File(brasindiceRobotConfiguration.getSetupPath() + "\\brasindice.rar");
            if (!rarFile.exists()) {
                initWebClient();
                WebResponse webResponse = webClient.getPage(brasindiceRobotConfiguration.getUrlDownload()).getWebResponse();
                InputStream inputStream = webResponse.getContentAsStream();
                FileOutputStream outputStream = new FileOutputStream(rarFile);
                IOUtils.write(IOUtils.toByteArray(inputStream), outputStream);
                inputStream.close();
                outputStream.close();
            }
            Runtime.getRuntime().exec(brasindiceRobotConfiguration.getWinrarPath() + "\\Winrar.exe e " + rarFile + " " + brasindiceRobotConfiguration.getSetupPath().getPath());
            brasindiceRobotConfiguration.setIsInstaled(true);
            new ConfigurationFileUtil().save(brasindiceRobotConfiguration);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Makes login and get authentication cookie
     *
     * @return String cookie values
     */
    public String loginAndGetCookie() {
        String uriString = UriComponentsBuilder.fromHttpUrl(brasindiceRobotConfiguration.getUrlLogin())
                .queryParam("nome", brasindiceRobotConfiguration.getLogin())
                .queryParam("senha", brasindiceRobotConfiguration.getPassword())
                .toUriString();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uriString, String.class);

        return responseEntity.getHeaders().get("Set-Cookie").stream().map(value -> value.split(";")[0]).collect(Collectors.joining(";"));
    }

    /**
     * Finds last edition of the Brasindice database file
     *
     * @return String[] with date and name for database Brasindice
     * @throws IOException
     */
    public String[] getLastEditonFileNameAndDate() throws IOException {
        String cookie = loginAndGetCookie();

        Document docCustomConn = Jsoup.connect(brasindiceRobotConfiguration.getUrlEditionsArchivesList())
                .userAgent("Mozilla")
                .timeout(5000)
                .cookie("Cookie", cookie)
                .get();

        String[] lastEditionFile = new String[2];
        ListIterator<Element> elementListIterator = docCustomConn.getElementsByTag("td").listIterator(0);
        while (elementListIterator.hasNext()) {
            Element element = elementListIterator.next();
            if (lastEditionFile[0] == null) {
                lastEditionFile[0] = element.text();
            } else {
                lastEditionFile[1] = String.format("%sc.GDB", element.text().replace("Edição ", "").replace(" link direto gdb", ""));
                break;
            }
            continue;
        }
        return lastEditionFile;
    }

    /**
     * Downloads database file from Brasindice website and configure
     */
    public void downloadAndConfigureBrasindiceDatabase() {
        try {
            String[] lastEditionFileNameAndDate = getLastEditonFileNameAndDate();
            if (!brasindiceRobotConfiguration.isUptaded(lastEditionFileNameAndDate[1])) {
                if (MESSAGES.UPDATE(brasindiceRobotConfiguration.isFirstInstallation())) {
                    deleteBrasindiceDatabase();
                    File lastEditionFile = new File(brasindiceRobotConfiguration.getSetupPath().getPath() + "/" + lastEditionFileNameAndDate[1]);
                    String toUriString = UriComponentsBuilder.fromHttpUrl(brasindiceRobotConfiguration.getUrlUploads()).path("/" + lastEditionFileNameAndDate[1]).toUriString();
                    BufferedOutputStream lastEdition = new RestTemplate().execute(toUriString, HttpMethod.GET, null, clientHttpResponse -> {
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(lastEditionFile));
                        StreamUtils.copy(clientHttpResponse.getBody(), bufferedOutputStream);
                        return bufferedOutputStream;
                    });
                    lastEdition.close();
                    brasindiceRobotConfiguration.setLastEditionDate(Datas.stringToLocalDate(lastEditionFileNameAndDate[0]));
                    brasindiceRobotConfiguration.setLastEdition(lastEditionFileNameAndDate[1]);
                    new ConfigurationFileUtil().save(brasindiceRobotConfiguration);
                    //configures database in Brasindice software
                    WindowsUtil windowsUtil = new WindowsUtil();
                    windowsUtil.createDesktopShortcut();
                    windowsUtil.openBrasindiceSoftwareAndConfigureDatabaseV2();
                    MESSAGES.UPDATE_SUCCESS(brasindiceRobotConfiguration.isFirstInstallation());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Exports PMC, PFB, Solution and Material file
     *
     * @return true if successful
     */
    public boolean exportBrasindiceFiles() {
        try {
            if (!brasindiceRobotConfiguration.isFilesExported()) {
                String[] exportFilesNames = brasindiceRobotConfiguration.getExportFilesNames();
                new WindowsUtil().exportBrasindiceFiles(exportFilesNames[0], exportFilesNames[1], exportFilesNames[2], exportFilesNames[3]);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean importBrasindiceFiles(boolean saveIntoNetworkDatabase) {
        try {
            BrasindiceDataModelRepository repository = SpringContext.getContext().getBean(BrasindiceDataModelRepository.class);
            brasindiceRobotConfiguration.getExportFiles().stream()
                    .map(file -> ImportBrasindiceUtil.importBrasindice(
                            brasindiceRobotConfiguration.getLastEditionDate(),
                            true,
                            brasindiceRobotConfiguration.getCspsUser(),
                            file))
                    .forEach(list -> {
                        if (saveIntoNetworkDatabase) repository.saveAll(list);
                    });
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void deleteExportedFiles() {
        brasindiceRobotConfiguration.getExportFiles().forEach(File::delete);
    }

    /**
     * Delete last database file in installation folder
     *
     * @return true if successful
     */
    private boolean deleteBrasindiceDatabase() {
        try {
            if (!brasindiceRobotConfiguration.isFirstInstallation()) {
                brasindiceRobotConfiguration.getAtualDatabaseFile().delete();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
