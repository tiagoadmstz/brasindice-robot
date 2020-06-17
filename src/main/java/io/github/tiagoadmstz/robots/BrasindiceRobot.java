package io.github.tiagoadmstz.robots;

import br.com.dev.engine.date.Datas;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import io.github.tiagoadmstz.config.Configuration;
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

    private final Configuration configuration;
    private WebClient webClient;

    public BrasindiceRobot() {
        this.configuration = new ConfigurationFileUtil().load();
        initWebClient();
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
        webClient.getOptions().setHomePage(configuration.getBaseUrl());
    }

    /**
     * Creates the path to install Brasindice system
     *
     * @return true if successful
     */
    public boolean createInstallationPath() {
        try {
            File installationPath = configuration.getSetupPath();
            if (!installationPath.exists()) {
                Files.createDirectories(configuration.getSetupPath().toPath());
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
            File rarFile = new File(configuration.getSetupPath() + "\\brasindice.rar");
            if (!rarFile.exists()) {
                WebResponse webResponse = webClient.getPage(configuration.getUrlDownload()).getWebResponse();
                InputStream inputStream = webResponse.getContentAsStream();
                FileOutputStream outputStream = new FileOutputStream(rarFile);
                IOUtils.write(IOUtils.toByteArray(inputStream), outputStream);
                inputStream.close();
                outputStream.close();
            }
            Runtime.getRuntime().exec(configuration.getWinrarPath() + "\\Winrar.exe e " + rarFile + " " + configuration.getSetupPath().getPath());
            configuration.setIsInstaled(true);
            new ConfigurationFileUtil().save(configuration);
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
        String uriString = UriComponentsBuilder.fromHttpUrl(configuration.getUrlLogin())
                .queryParam("nome", configuration.getLogin())
                .queryParam("senha", configuration.getPassword())
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

        Document docCustomConn = Jsoup.connect(configuration.getUrlEditionsArchivesList())
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
     * Downloads database file from Brasindice website if is
     */
    public void downloadBrasindiceDatabase() {
        try {
            String[] lastEditionFileNameAndDate = getLastEditonFileNameAndDate();
            if (!isUptaded(lastEditionFileNameAndDate[1])) {
                if (MESSAGES.UPDATE(isFirstInstallation())) {
                    deleteBrasindiceDatabase(configuration.getLastEdition());
                    new ConfigurationFileUtil().save(configuration);
                    File lastEditionFile = new File(configuration.getSetupPath().getPath() + "/" + lastEditionFileNameAndDate);
                    String toUriString = UriComponentsBuilder.fromHttpUrl(configuration.getUrlUploads()).path("/" + lastEditionFileNameAndDate).toUriString();
                    BufferedOutputStream lastEdition = new RestTemplate().execute(toUriString, HttpMethod.GET, null, clientHttpResponse -> {
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(lastEditionFile));
                        StreamUtils.copy(clientHttpResponse.getBody(), bufferedOutputStream);
                        return bufferedOutputStream;
                    });
                    lastEdition.close();
                    MESSAGES.UPDATE_SUCCESS(isFirstInstallation());
                    configuration.setLastEditionDate(Datas.stringToLocalDate(lastEditionFileNameAndDate[0]));
                    configuration.setLastEdition(lastEditionFileNameAndDate[1]);
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
            String pmc = getFormattedExportFileName(configuration.getExportDataFile().getPmc());
            String pfb = getFormattedExportFileName(configuration.getExportDataFile().getPfb());
            String solution = getFormattedExportFileName(configuration.getExportDataFile().getSolucao());
            String material = getFormattedExportFileName(configuration.getExportDataFile().getMaterial());
            File pmcFile = new File(configuration.getSetupPath() + "/" + pmc + ".txt");
            File pfbFile = new File(configuration.getSetupPath() + "/" + pfb + ".txt");
            File solutionFile = new File(configuration.getSetupPath() + "/" + solution + ".txt");
            File materialFile = new File(configuration.getSetupPath() + "/" + material + ".txt");
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
        return fileName.replaceAll("_.*", "_") + configuration.getLastEditionDate().format(DateTimeFormatter.ofPattern(fileName.replaceAll(".*_", "")));
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
                new File(configuration.getSetupPath().getPath() + "/" + edition).delete();
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
        return configuration.getLastEdition().equals(lastEdition);
    }

    /**
     * Verify if is first installation
     *
     * @return true if is first installation
     */
    private boolean isFirstInstallation() {
        return configuration.getLastEdition() == null || "".equals(configuration.getLastEdition());
    }

}
