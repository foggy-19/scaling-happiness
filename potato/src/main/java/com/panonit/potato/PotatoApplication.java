package com.panonit.potato;

import com.panonit.potato.services.ColourPrinter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class PotatoApplication implements CommandLineRunner {

    private final Logger logger = Logger.getLogger(PotatoApplication.class.getName());

    private final ColourPrinter colourPrinter;

    private PotatoApplication(ColourPrinter colourPrinter) {
        this.colourPrinter = colourPrinter;
    }

    public static void main(String[] args) {
        SpringApplication.run(PotatoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info(colourPrinter.print());
    }
}
