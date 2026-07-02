package com.panonit.postgres;

import lombok.extern.java.Log;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class PostgresApplication implements CommandLineRunner {

    private final DataSource dataSource;

    public PostgresApplication(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(PostgresApplication.class, args);
    }

    @Override
    public void run(String @NonNull ... args) {
        log.info("Datasource: " + dataSource);
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        template.execute("SELECT 1");
    }
}
