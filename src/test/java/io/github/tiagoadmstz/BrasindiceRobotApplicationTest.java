package io.github.tiagoadmstz;

import io.github.tiagoadmstz.models.Configuration;
import io.github.tiagoadmstz.util.ConfigurationFileUtil;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BrasindiceRobotApplicationTest {

    @Test
    public void ConfigurationFileUtilTest() {
        Logger logger = LoggerFactory.getLogger(ConfigurationFileUtil.class);
        logger.info(() -> "Creating configuration util class");
        ConfigurationFileUtil configurationFileUtil = new ConfigurationFileUtil();
        logger.info(() -> "Creating configuration file");
        assertTrue(configurationFileUtil.create());
        logger.info(() -> "Load configurations and parse to object");
        Configuration configuration = configurationFileUtil.load();
        assertNotNull(configuration);
    }

}
