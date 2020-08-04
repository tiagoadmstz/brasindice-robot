package io.github.tiagoadmstz.robots;

import br.com.dev.engine.date.Datas;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import io.github.tiagoadmstz.config.BrasindiceRobotConfiguration;
import io.github.tiagoadmstz.util.ConfigurationFileUtil;
import io.github.tiagoadmstz.util.MESSAGES;
import io.github.tiagoadmstz.util.WindowsUtil;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.ListIterator;
import java.util.stream.Collectors;

public final class BrasindiceRobot {

    private BrasindiceRobotConfiguration brasindiceRobotConfiguration;
    private WebClient webClient;

    public BrasindiceRobot() {
        this.brasindiceRobotConfiguration = new ConfigurationFileUtil().load();
        initWebClient();
    }

    /**
     * Install or update Brasindice software
     */
    public void installOrUpdate() {
        ConfigurationFileUtil configurationFileUtil = new ConfigurationFileUtil();
        if (brasindiceRobotConfiguration == null) {
            configurationFileUtil.create();
            brasindiceRobotConfiguration = configurationFileUtil.load();
        }
        if (!brasindiceRobotConfiguration.getIsInstaled()) {
            createInstallationPath();
            downloadBrasindiceSoftwareAndUnrar();
        } else {
            downloadAndConfigureBrasindiceDatabase();
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
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);

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
            if (!isUptaded(lastEditionFileNameAndDate[1])) {
                if (MESSAGES.UPDATE(isFirstInstallation())) {
                    deleteBrasindiceDatabase(brasindiceRobotConfiguration.getLastEdition());
                    new ConfigurationFileUtil().save(brasindiceRobotConfiguration);
                    File lastEditionFile = new File(brasindiceRobotConfiguration.getSetupPath().getPath() + "/" + lastEditionFileNameAndDate);
                    String toUriString = UriComponentsBuilder.fromHttpUrl(brasindiceRobotConfiguration.getUrlUploads()).path("/" + lastEditionFileNameAndDate).toUriString();
                    BufferedOutputStream lastEdition = new RestTemplate().execute(toUriString, HttpMethod.GET, null, clientHttpResponse -> {
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(lastEditionFile));
                        StreamUtils.copy(clientHttpResponse.getBody(), bufferedOutputStream);
                        return bufferedOutputStream;
                    });
                    lastEdition.close();
                    MESSAGES.UPDATE_SUCCESS(isFirstInstallation());
                    brasindiceRobotConfiguration.setLastEditionDate(Datas.stringToLocalDate(lastEditionFileNameAndDate[0]));
                    brasindiceRobotConfiguration.setLastEdition(lastEditionFileNameAndDate[1]);
                    //configures database in Brasindice software
                    WindowsUtil windowsUtil = new WindowsUtil();
                    windowsUtil.createDesktopShortcut();
                    windowsUtil.openBrasindiceSoftwareAndConfigureDatabaseV2();
                    //exports all database files
                    if (MESSAGES.EXPORT_DATA_FILES()) {
                        MESSAGES.EXPORT_DATA_FILES_SUCCESS(exportBrasindiceFiles());
                    }
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
            String pmc = getFormattedExportFileName(brasindiceRobotConfiguration.getExportDataFile().getPmc());
            String pfb = getFormattedExportFileName(brasindiceRobotConfiguration.getExportDataFile().getPfb());
            String solution = getFormattedExportFileName(brasindiceRobotConfiguration.getExportDataFile().getSolucao());
            String material = getFormattedExportFileName(brasindiceRobotConfiguration.getExportDataFile().getMaterial());
            File pmcFile = new File(brasindiceRobotConfiguration.getSetupPath() + "/" + pmc + ".txt");
            File pfbFile = new File(brasindiceRobotConfiguration.getSetupPath() + "/" + pfb + ".txt");
            File solutionFile = new File(brasindiceRobotConfiguration.getSetupPath() + "/" + solution + ".txt");
            File materialFile = new File(brasindiceRobotConfiguration.getSetupPath() + "/" + material + ".txt");
            new WindowsUtil().exportBrasindiceFiles(pmc, pfb, solution, material);
            //pmcFile.delete();
            //pfbFile.delete();
            //solutionFile.delete();
            //materialFile.delete();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Get formatted file name
     *
     * @param fileName
     * @return
     */
    private String getFormattedExportFileName(String fileName) {
        return fileName.replaceAll("_.*", "_") + brasindiceRobotConfiguration.getLastEditionDate().format(DateTimeFormatter.ofPattern(fileName.replaceAll(".*_", "")));
    }

    /**
     * Delete last database file in installation folder
     *
     * @param edition Ex: 950c.GDB
     * @return true if successful
     */
    private boolean deleteBrasindiceDatabase(String edition) {
        try {
            if (!isFirstInstallation()) {
                new File(brasindiceRobotConfiguration.getSetupPath().getPath() + "/" + edition).delete();
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Verify if installed edition is updated
     *
     * @param lastEdition
     * @return
     */
    private boolean isUptaded(String lastEdition) {
        return brasindiceRobotConfiguration.getLastEdition().equals(lastEdition);
    }

    /**
     * Verify if is first installation
     *
     * @return true if is first installation
     */
    private boolean isFirstInstallation() {
        return brasindiceRobotConfiguration.getLastEdition() == null || "".equals(brasindiceRobotConfiguration.getLastEdition());
    }

}
