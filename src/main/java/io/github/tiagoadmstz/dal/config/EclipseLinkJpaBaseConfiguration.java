package io.github.tiagoadmstz.dal.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class EclipseLinkJpaBaseConfiguration extends JpaBaseConfiguration {

    protected EclipseLinkJpaBaseConfiguration(DataSource dataSource, JpaProperties properties, ObjectProvider<JtaTransactionManager> jtaTransactionManager) {
        super(dataSource, properties, jtaTransactionManager);
    }

    @Override
    protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
        return new EclipseLinkJpaVendorAdapter();
    }

    @Override
    protected Map<String, Object> getVendorProperties() {
        return new HashMap<String, Object>() {
            {
                put("eclipselink.cache.shared.default", "false");
                put("eclipselink.flush", "true");
                put("eclipselink.connection-pool.default.initial", "1");
                put("eclipselink.connection-pool.node2.min", "800");
                put("eclipselink.connection-pool.node2.max", "800");
                put("eclipselink.jdbc.timeout", "5000");
                put("eclipselink.weaving", "static");
                put("eclipselink.weaving.eager", "false");
                put("eclipselink.id-validation", "NONE");
            }
        };
    }

}
