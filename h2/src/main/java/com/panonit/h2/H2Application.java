package com.panonit.h2;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class H2Application implements CommandLineRunner {

    private final DataSource dataSource;

    public H2Application(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(H2Application.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Datasource: " + dataSource.toString());
        final JdbcTemplate template = new JdbcTemplate(dataSource);
        template.execute("select 1");
    }
}
