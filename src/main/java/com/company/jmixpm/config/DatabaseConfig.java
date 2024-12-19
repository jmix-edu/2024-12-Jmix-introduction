package com.company.jmixpm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.vault.core.VaultTemplate;

@Configuration
public class DatabaseConfig {

    private final VaultTemplate vaultTemplate;

    public DatabaseConfig(@Lazy VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }

    @Primary
    @Profile("default")
    @Bean("dataSourceProperties")
    @ConfigurationProperties("main.datasource")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Profile("prod")
    @Bean("dataSourceProperties")
    DataSourceProperties DataSourcePropertiesVault() {
        DataSourceProperties properties = new DataSourceProperties();

        DbConfigDTO configDTO = new ObjectMapper().convertValue(
                vaultTemplate.read("secret/data/application/database/credentials").getData().get("data"), DbConfigDTO.class);

            properties.setUrl(configDTO.getUrl());
            properties.setUsername(configDTO.getUsername());
            properties.setPassword(configDTO.getPassword());

            return properties;
    }

}
