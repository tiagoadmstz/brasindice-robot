package io.github.tiagoadmstz;

import io.github.tiagoadmstz.config.BrasindiceRobotConfiguration;
import io.github.tiagoadmstz.config.SpringContext;
import io.github.tiagoadmstz.dal.config.BrasindiceDao;
import io.github.tiagoadmstz.dal.firebird.repositories.MedicamentoRepository;
import io.github.tiagoadmstz.dal.firebird.repositories.SysDbaRepository;
import io.github.tiagoadmstz.robots.BrasindiceRobot;
import io.github.tiagoadmstz.util.ConfigurationFileUtil;
import io.github.tiagoadmstz.util.KeyboardUtil;
import io.github.tiagoadmstz.util.WindowsUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BrasindiceRobotRobotApplicationTest {

    Logger logger = LoggerFactory.getLogger(BrasindiceRobotRobotApplicationTest.class);

    @Test
    @Order(0)
    public void configurationFileUtilTest() {
        logger.info(() -> "Creating configuration util class");
        ConfigurationFileUtil configurationFileUtil = new ConfigurationFileUtil();
        logger.info(() -> "Creating configuration file");
        assertTrue(configurationFileUtil.create());
        logger.info(() -> "Load configurations and parse to object");
        BrasindiceRobotConfiguration brasindiceRobotConfiguration = configurationFileUtil.load();
        assertNotNull(brasindiceRobotConfiguration);
    }

    @Test
    @Order(1)
    public void createBrasindiceInstallationPathTest() {
        logger.info(() -> "Creating installation path");
        assertTrue(new BrasindiceRobot().createInstallationPath());
    }

    @Test
    @Order(2)
    public void downloadBrasindiceSoftwareAndUnrarTest() {
        logger.info(() -> "Downloading software and unrar");
        assertTrue(new BrasindiceRobot().downloadBrasindiceSoftwareAndUnrar());
    }

    @Test
    @Order(3)
    public void loginTest() {
        logger.info(() -> "Makes login and get cookie values");
        assertNotNull(new BrasindiceRobot().loginAndGetCookie());
    }

    @Test
    @Order(4)
    public void getLastEditionTest() {
        logger.info(() -> "Finds last edition number and makes file to download");
        assertDoesNotThrow(() -> new BrasindiceRobot().getLastEditonFileNameAndDate());
    }

    @Test
    @Order(5)
    public void downloadBransindiceDatabaseTest() {
        logger.info(() -> "Downloading database file and saving into installation folder");
        new BrasindiceRobot().downloadAndConfigureBrasindiceDatabase();
    }

    @Test
    @Order(6)
    public void createDesktopShortcutTest() {
        logger.info(() -> "Creating desktop shortcut");
        assertTrue(new WindowsUtil().createDesktopShortcut());
    }

    @Test
    @Order(7)
    public void keyboardUtilTest() {
        logger.info(() -> "Parse string to keyEvent array");
        assertArrayEquals(new Integer[][]{
                {66, 1, 16}, {82, 1}, {65, 1}, {83, 1}, {73, 1}, {78, 1}, {68, 1}, {73, 1}, {67, 1}, {69, 1}
        }, KeyboardUtil.parseStringToKeyEventArray("Brasindice"));
        assertArrayEquals(new Integer[][]{
                {80, 1, 16}, {77, 1, 16}, {67, 1, 16}, {45, 1, 16}, {48, 1}, {53, 1}, {48, 1}, {54, 1}, {50, 1}, {48, 1}, {50, 1}, {48, 1}
        }, KeyboardUtil.parseStringToKeyEventArray("PMC_05062020"));
    }

    @Test
    @Order(8)
    public void openBrasindiceSoftwareTest() {
        logger.info(() -> "Open Brasindice software and configure database");
        new WindowsUtil().openBrasindiceSoftwareAndConfigureDatabaseV2();
    }

    @Test
    @Order(9)
    public void exportBrasindiceFilesTest() {
        logger.info(() -> "Export pmc, pfb, solution and material file from current database version");
        assertTrue(new BrasindiceRobot().exportBrasindiceFiles());
    }

    @Test
    @Order(10)
    public void importBrasindiceFilesTest() {
        logger.info(() -> "Import pmc, pfb, solution and material file from current database version");
        assertTrue(new BrasindiceRobot().importBrasindiceFiles(false));
    }

    @Test
    @Order(11)
    public void deleteExportedFilesTest() {
        logger.info(() -> "Deleting exported files");
        BrasindiceRobotConfiguration configuration = new ConfigurationFileUtil().load();
        List<File> exportFiles = configuration.getExportFiles();
        exportFiles.forEach(File::deleteOnExit);
        assertFalse(configuration.isFilesExported());
    }

    //@Test
    public void firebirdTest() throws Exception {
        BrasindiceDao brasindiceDao = new BrasindiceDao();
        logger.info(() -> "Connecting Brasindice database");
        Connection connection = brasindiceDao.getConnection();
        //logger.info(() -> "Find tables names");
        //brasindiceDao.getTablesNames(connection).forEach(System.out::println);
        //logger.info(() -> "Find views names");
        //brasindiceDao.getViewsNames(connection).forEach(System.out::println);
        //logger.info(() -> "Find tabels and views fields names");
        //brasindiceDao.getTablesAndViewsFieldsNames(connection).forEach(System.out::println);
        brasindiceDao.getRelations(connection).forEach(System.out::println);
        //brasindiceDao.getRoles(connection).forEach(System.out::println);
        //brasindiceDao.getFunctions(connection).forEach(System.out::println);
        //brasindiceDao.getFilters(connection).forEach(System.out::println);
        //brasindiceDao.getTransactions(connection).forEach(System.out::println);
        //brasindiceDao.getTriggers(connection).forEach(System.out::println);
        //brasindiceDao.getProcedures(connection).forEach(System.out::println);
        connection.close();
    }

    //@Test
    public void brasindiceRepositoriesTest() {
        ApplicationContext context = SpringContext.getContext();
        //Apresentacao
        //ApresentacaoRepository apresentacaoRepository = context.getBean(ApresentacaoRepository.class);
        //apresentacaoRepository.findAll().forEach(System.out::println);
        //Auxiliar
        //AuxiliarRepository auxiliarRepository = context.getBean(AuxiliarRepository.class);
        //auxiliarRepository.findAll().forEach(System.out::println);
        //DescricaoPrecoRepository descricaoPrecoRepository = context.getBean(DescricaoPrecoRepository.class);
        //descricaoPrecoRepository.findAll().forEach(System.out::println);
        //EstadoRepository estadoRepository = context.getBean(EstadoRepository.class);
        //estadoRepository.findAll().forEach(System.out::println);
        //ExcluidoRepository excluidoRepository = context.getBean(ExcluidoRepository.class);
        //excluidoRepository.findAll().forEach(System.out::println);
        //HierarquiaRepository hierarquiaRepository = context.getBean(HierarquiaRepository.class);
        //hierarquiaRepository.findAll().forEach(System.out::println);
        //LaboratorioRepository laboratorioRepository = context.getBean(LaboratorioRepository.class);
        //laboratorioRepository.findAll().forEach(System.out::println);
        //LaboratorioSubstanciaRepository laboratorioSubstanciaRepository = context.getBean(LaboratorioSubstanciaRepository.class);
        //laboratorioSubstanciaRepository.findAll().forEach(System.out::println);
        MedicamentoRepository medicamentoRepository = context.getBean(MedicamentoRepository.class);
        //medicamentoRepository.findAll().stream().limit(100).forEach(System.out::println);
        medicamentoRepository.findByCodigo("21031");
        //MedicamentoSubstanciaRepository medicamentoSubstanciaRepository = context.getBean(MedicamentoSubstanciaRepository.class);
        //medicamentoSubstanciaRepository.findAll().forEach(System.out::println);
        //NovoRepository novoRepository = context.getBean(NovoRepository.class);
        //novoRepository.findAll().forEach(System.out::println);
        //novoRepository.findByEan("7896006245926");
        //novoRepository.findByTiss("0000060619");
        //novoRepository.findByTuss("90210506");
        //ParametroRepository parametroRepository = context.getBean(ParametroRepository.class);
        //parametroRepository.findAll().forEach(System.out::println);
        //PrecoRepository precoRepository = context.getBean(PrecoRepository.class);
        //precoRepository.findAll().forEach(System.out::println);
        //PrecoSubstanciaRepository precoSubstanciaRepository = context.getBean(PrecoSubstanciaRepository.class);
        //precoSubstanciaRepository.findAll().forEach(System.out::println);
        //SubstanciaRepository substanciaRepository = context.getBean(SubstanciaRepository.class);
        //substanciaRepository.findAll().forEach(System.out::println);

        //SysDbaRepository sysDbaRepository = context.getBean(SysDbaRepository.class);
        //sysDbaRepository.findAll().forEach(System.out::println);

        //BrasindiceDataModelRepository brasindiceDataModelRepository = context.getBean(BrasindiceDataModelRepository.class);
        //brasindiceDataModelRepository.findAll().forEach(System.out::println);

        try {
            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
