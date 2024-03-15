package edu.java.scrapper.integration;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@Testcontainers
public abstract class IntegrationTest {

    @ServiceConnection
    protected static PostgreSQLContainer<?> postgres;

    protected static JdbcTemplate jdbcTemplate;

    @BeforeAll
    static void setup() {
        postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16"))
            .withDatabaseName("scrapper")
            .withUsername("postgres")
            .withPassword("postgres");
        postgres.start();

        runMigrations(postgres);

        jdbcTemplate = new JdbcTemplate(DataSourceBuilder.create()
            .url(postgres.getJdbcUrl())
            .username(postgres.getUsername())
            .password(postgres.getPassword())
            .build());
    }

    @AfterAll
    static void stop() {
        postgres.stop();
    }

    private static void runMigrations(JdbcDatabaseContainer<?> c) {
        try (Connection connection = DriverManager.getConnection(c.getJdbcUrl(), c.getUsername(), c.getPassword())) {

            Database dataBase = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Path changeLogPath =
                new File(".").toPath().toAbsolutePath().getParent().getParent().resolve("migrations");

            Liquibase liquibase =
                new Liquibase("master.yml", new DirectoryResourceAccessor(changeLogPath), dataBase);
            liquibase.update(new Contexts(), new LabelExpression());

        } catch (SQLException | FileNotFoundException | LiquibaseException e) {
            log.error(e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
