package com.epam.esm.repository.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class RepositoryConfig {

    @Bean
    @Profile("prod")
    public DataSource dataSourceProd(DatabaseProperties props) {
        HikariDataSource ds = new HikariDataSource();
        ds.setConnectionTimeout(props.getConnectionTimeout());
        ds.setIdleTimeout(props.getIdleTimeout());
        ds.setMaximumPoolSize(props.getMaxPoolSize());
        ds.setDriverClassName(props.getDbDriver());
        ds.setJdbcUrl(props.getDbUrl());
        ds.setUsername(props.getDbUser());
        ds.setPassword(props.getDbPassword());

        return ds;
    }

    @Bean
    @Profile("dev")
    public DataSource dataSourceDev(DatabaseProperties props) {
        HikariDataSource ds = new HikariDataSource();
        ds.setMaximumPoolSize(props.getMaxPoolSize());
        ds.setDriverClassName(props.getDbDriver());
        ds.setJdbcUrl(props.getDbUrl());
        ds.setUsername(props.getDbUser());
        ds.setPassword(props.getDbPassword());

        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
