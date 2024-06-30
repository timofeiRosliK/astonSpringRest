package org.example.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManagerFactory;
import org.junit.ClassRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"org.example.service", "org.example.mapper", "org.example.web"})
@EnableJpaRepositories(basePackages = "org.example.repository")
@Testcontainers
public class TestContainerConfig {
    @Container
    @ClassRule
    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.4.0")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("db.sql");


    @PostConstruct
    public void init(){
        mysqlContainer.start();
    }

    @PreDestroy
    public void destroy(){
        mysqlContainer.stop();
    }


    @Bean
    public HikariConfig config(){
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() {
        config().setDriverClassName(mysqlContainer.getDriverClassName());
        config().setJdbcUrl(mysqlContainer.getJdbcUrl());
        config().setUsername(mysqlContainer.getUsername());
        config().setPassword(mysqlContainer.getPassword());
        return new HikariDataSource(config());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("org.example.model");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return em;
    }

    @Bean
    protected PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}