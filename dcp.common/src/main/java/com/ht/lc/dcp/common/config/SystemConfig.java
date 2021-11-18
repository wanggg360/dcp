package com.ht.lc.dcp.common.config;

import com.ht.lc.dcp.common.constants.CipherConstant;
import com.ht.lc.dcp.common.crypto.CipherServiceManager;
import com.ht.lc.dcp.common.exception.ServiceComException;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-09-10 08:36
 * @Version 1.0
 **/
@Configuration
@PropertySource("classpath:/conf/system.properties")
public class SystemConfig {

    private static final Logger LOG = LoggerFactory.getLogger(SystemConfig.class);

    @Configuration
    static class KeyConfig {
        @Value("${aes.key}")
        private String aesKey;

        @Value("${aes.iv}")
        private String aesIv;

        public String getAesKey() {
            return aesKey;
        }

        public String getAesIv() {
            return aesIv;
        }
    }
    @Configuration
    static class HikariDataSourceConfig {

        @Value("${jdbc.url}")
        private String url;

        @Value("${jdbc.driverClass}")
        private String driverClass;

        @Value("${jdbc.username}")
        private String username;

        @Value("${jdbc.password}")
        private String password;

        @Value("${datasource.minIdle}")
        private int minIdle;

        @Value("${datasource.maxPoolSize}")
        private int maxPoolSize;

        @Autowired
        KeyConfig keyConfig;

        @Bean(name = "hikariDataSource")
        @Qualifier("hikariDataSource")
        public HikariDataSource dataSource() {
            LOG.info("Load hikari datasource");
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setDriverClassName(this.driverClass);
            dataSource.setJdbcUrl(this.url);
            dataSource.setUsername(this.username);
            String pwd = "";
            try {
                pwd = CipherServiceManager.getInstance().decrypt(CipherConstant.AES_GCM_256,
                        password, keyConfig.getAesKey(), keyConfig.getAesIv());
            } catch (ServiceComException e) {
                LOG.error("decrypt db password error, init datasource failed. ");
            }
            dataSource.setPassword(pwd);
            dataSource.setMaximumPoolSize(this.maxPoolSize);
            dataSource.setMinimumIdle(this.minIdle);
            return dataSource;
        }
    }
}
