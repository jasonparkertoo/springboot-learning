package com.example.customer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DbConfig
{
    @Value("${db.username}")
    private String username;

    @Value("${db.username}")
    private String password;

    @Value("${db.uri}")
    private String dbUrl;

    @Bean
    public DataSource dataSource()
    {
        return DataSourceBuilder.create()
                .username(username)
                .password(password)
                .url(dbUrl)
                .build();
    }
}
