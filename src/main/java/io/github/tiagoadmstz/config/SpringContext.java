package io.github.tiagoadmstz.config;

import io.github.tiagoadmstz.dal.config.EclipseLinkJpaBaseConfiguration;
import io.github.tiagoadmstz.dal.config.OracleConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public abstract class SpringContext {

    private static final AnnotationConfigApplicationContext context = load();

    public static ApplicationContext getContext() {
        return context;
    }

    private static AnnotationConfigApplicationContext load() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.setAllowBeanDefinitionOverriding(true);
        context.register(
                YamlPropertiesConfiguration.class,
                SpringConfiguration.class,
                EclipseLinkJpaBaseConfiguration.class,
                OracleConfiguration.class
                //, FirebirdConfiguration.class);
        );
        context.refresh();
        return context;
    }

}
