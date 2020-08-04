package io.github.tiagoadmstz.dal.config;

import io.github.tiagoadmstz.config.BrasindiceRobotConfiguration;
import io.github.tiagoadmstz.config.SpringConfiguration;
import io.github.tiagoadmstz.util.ConfigurationFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Primary
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "fbEntityManagerFactory",
        transactionManagerRef = "fbTransactionManager",
        basePackages = {"io.github.tiagoadmstz.dal.firebird.repositories"}
)
public class FirebirdConfiguration {

    @Autowired
    private SpringConfiguration configuration;
    private final BrasindiceRobotConfiguration brasindiceRobotConfiguration;

    public FirebirdConfiguration() {
        this.brasindiceRobotConfiguration = new ConfigurationFileUtil().load();
        System.load(new File(brasindiceRobotConfiguration.getSetupPath() + "\\fbembed.dll").getPath());
        System.load(new File(brasindiceRobotConfiguration.getSetupPath() + "\\gds32.dll").getPath());
    }

    @Bean(name = "fbDataSource")
    @ConfigurationProperties(prefix = "fb.datasource")
    public DataSource fbDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.firebirdsql.jdbc.FBDriver")
                .url(String.format(configuration.getBrasindiceUrl(), brasindiceRobotConfiguration.getSetupPath()))
                .username(configuration.getBrasindiceUsername())
                .password(configuration.getBrasindicePassword())
                .build();
    }

    @Bean(name = "fbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean fbEntityManagerFactory(
            @Autowired EclipseLinkJpaBaseConfiguration eclipseLinkJpaBaseConfiguration,
            @Qualifier("fbDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("io.github.tiagoadmstz.dal.firebird.models", "io.github.tiagoadmstz.dal.firebird.sysmodels", "io.github.tiagoadmstz.dal.converters");
        entityManagerFactoryBean.setJpaPropertyMap(getProperties());
        entityManagerFactoryBean.setPersistenceUnitName("firebirdPU");
        entityManagerFactoryBean.setJpaVendorAdapter(eclipseLinkJpaBaseConfiguration.createJpaVendorAdapter());
        return entityManagerFactoryBean;
    }

    @Bean(name = "fbTransactionManager")
    public PlatformTransactionManager fbTransactionManager(
            @Qualifier("fbEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    public Map<String, String> getProperties() {
        return new HashMap() {{
            put("eclipselink.target-database", "Auto");
            put("eclipselink.logging.level", "OFF");
            put("eclipselink.ddl-generation", "none");
            put("eclipselink.persistence-context.flush-mode", "commit");
            put("eclipselink.persistence-context.close-on-commit", "true");
            put("eclipselink.cache.shared.default", "true");
            put("eclipselink.flush", "true");
            put("eclipselink.connection-pool.default.initial", "1");
            put("eclipselink.connection-pool.node2.min", "800");
            put("eclipselink.connection-pool.node2.max", "800");
            put("eclipselink.jdbc.timeout", "5000");
            put("eclipselink.weaving", "STATIC");
            put("eclipselink.weaving.eager", "FALSE");
            put("eclipselink.id-validation", "NONE");
        }};
    }

}
