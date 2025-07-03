package me.mircoporetti.elmai.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "vector-db")
public record PGVectorConfig(
        String host,
        int port,
        String database,
        String user,
        String password
){}