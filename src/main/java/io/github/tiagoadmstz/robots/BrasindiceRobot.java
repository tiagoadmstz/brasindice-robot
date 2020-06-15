package io.github.tiagoadmstz.robots;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import io.github.tiagoadmstz.config.Configuration;
import io.github.tiagoadmstz.util.ConfigurationFileUtil;
import io.github.tiagoadmstz.util.MESSAGES;
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
     * @return String with name for database Brasindice
     * @throws IOException
     */
    public String getLastEditonFileName() throws IOException {
        String cookie = loginAndGetCookie();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Cookie", cookie);

        Document docCustomConn = Jsoup.connect(configuration.getUrlEditionsArchivesList())
                .userAgent("Mozilla")
                .timeout(5000)
                .cookie("Cookie", cookie)
                .get();

        String lastEditionFile = null;
        ListIterator<Element> elementListIterator = docCustomConn.getElementsByTag("td").listIterator(1);
        while (elementListIterator.hasNext()) {
            Element element = elementListIterator.next();
            lastEditionFile = String.format("%sc.GDB", element.text().replace("Edição ", "").replace(" link direto gdb", ""));
            break;
        }
        return lastEditionFile;
    }

    /**
     * Downloads database file from Brasindice website if is
     */
    public void downloadBrasindiceDatabase() {
        try {
            String lastEditionFileName = getLastEditonFileName();
            if (!isUptaded(lastEditionFileName)) {
                if (MESSAGES.UPDATE(isFirstInstallation(lastEditionFileName))) {
                    deleteBrasindiceDatabase(configuration.getLastEdition());
                    configuration.setLastEdition(lastEditionFileName);
                    new ConfigurationFileUtil().save(configuration);
                    File lastEditionFile = new File(configuration.getSetupPath().getPath() + "/" + lastEditionFileName);
                    String toUriString = UriComponentsBuilder.fromHttpUrl(configuration.getUrlUploads()).path("/" + lastEditionFileName).toUriString();
                    BufferedOutputStream lastEdition = new RestTemplate().execute(toUriString, HttpMethod.GET, null, clientHttpResponse -> {
                        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(lastEditionFile));
                        StreamUtils.copy(clientHttpResponse.getBody(), bufferedOutputStream);
                        return bufferedOutputStream;
                    });
                    lastEdition.close();
                    MESSAGES.UPDATE_SUCCESS(isFirstInstallation(lastEditionFileName));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Delete last database file in installation folder
     *
     * @param edition Ex: 950c.GDB
     * @return true if successful
     */
    private boolean deleteBrasindiceDatabase(String edition) {
        try {
            if (!isFirstInstallation(edition)) {
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
     * @param edition
     * @return true if is first installation
     */
    private boolean isFirstInstallation(String edition) {
        return edition == null & "".equals(edition);
    }

}
