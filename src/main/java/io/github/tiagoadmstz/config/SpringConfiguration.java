package io.github.tiagoadmstz.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Component
@ConfigurationProperties
@EnableConfigurationProperties
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SpringConfiguration implements Serializable {

    private static final long serialVersionUID = 1282107780694928091L;
    @Value(value = "${spring.datasource.brasindice.url}")
    private String brasindiceUrl;
    @Value(value = "${spring.datasource.brasindice.username}")
    private String brasindiceUsername;
    @Value(value = "${spring.datasource.brasindice.password}")
    private String brasindicePassword;
    @Value(value = "${spring.datasource.csps.url}")
    private String cspsUrl;
    @Value(value = "${spring.datasource.csps.username}")
    private String cspsUsername;
    @Value(value = "${spring.datasource.csps.password}")
    private String cspsPassword;

}
