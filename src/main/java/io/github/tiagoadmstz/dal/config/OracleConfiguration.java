package io.github.tiagoadmstz.dal.config;

import io.github.tiagoadmstz.config.SpringConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
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

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Primary
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "oracleEntityManagerFactory",
        transactionManagerRef = "oracleTransactionManager",
        basePackages = {"io.github.tiagoadmstz.dal.oracle.repositories"}
)
public class OracleConfiguration {

    @Autowired
    private SpringConfiguration configuration;

    @Primary
    @Bean(name = "oracleDataSource")
    @ConfigurationProperties(prefix = "oracle.datasource")
    public DataSource oracleDataSource() {
        return DataSourceBuilder.create()
                .driverClassName("oracle.jdbc.OracleDriver")
                .url(configuration.getCspsUrl())
                .username(configuration.getCspsUsername())
                .password(configuration.getCspsPassword())
                .build();
    }

    @Primary
    @Bean(name = "oracleEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(
            @Autowired EclipseLinkJpaBaseConfiguration eclipseLinkJpaBaseConfiguration,
            @Qualifier("oracleDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan(
                "io.github.tiagoadmstz.dal.oracle.models",
                "io.github.tiagoadmstz.dal.converters"
        );
        entityManagerFactoryBean.setJpaPropertyMap(getProperties());
        entityManagerFactoryBean.setPersistenceUnitName("oraclePU");
        entityManagerFactoryBean.setJpaVendorAdapter(eclipseLinkJpaBaseConfiguration.createJpaVendorAdapter());
        return entityManagerFactoryBean;
    }

    @Bean
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }

    @Primary
    @Bean(name = "oracleTransactionManager")
    public PlatformTransactionManager oracleTransactionManager(
            @Qualifier("oracleEntityManagerFactory") EntityManagerFactory oracleEntityManagerFactory) {
        return new JpaTransactionManager(oracleEntityManagerFactory);
    }

    public Map<String, String> getProperties() {
        Map<String, String> map = new HashMap();
        map.put("eclipselink.target-database", "Oracle");
        map.put("eclipselink.persistence-context.flush-mode", "commit");
        //map.put("eclipselink.jdbc.exclusive-connection.mode", "Always");
        map.put("eclipselink.persistence-context.close-on-commit", "true");
        map.put("eclipselink.cache.shared.default", "false");
        map.put("eclipselink.flush", "true");
        map.put("eclipselink.connection-pool.default.initial", "1");
        map.put("eclipselink.connection-pool.node2.min", "2000");
        map.put("eclipselink.connection-pool.node2.max", "2000");
        map.put("eclipselink.jdbc.timeout", "5000");
        map.put("eclipselink.weaving", "static");
        map.put("eclipselink.weaving.eager", "FALSE");
        map.put("eclipselink.id-validation", "NEGATIVE");
        //map.put("eclipselink.logging.level", "FINE");
        return map;
    }

}
