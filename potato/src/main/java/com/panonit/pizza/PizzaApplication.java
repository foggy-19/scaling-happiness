package com.panonit.pizza;

import com.panonit.pizza.config.PizzaConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class PizzaApplication implements CommandLineRunner {

    private final Logger logger = Logger.getLogger(PizzaApplication.class.getName());

    private final PizzaConfig pizzaConfig;

    public PizzaApplication(PizzaConfig pizzaConfig) {
        this.pizzaConfig = pizzaConfig;
    }

    public static void main(String[] args) {
        SpringApplication.run(PizzaApplication.class, args);
    }

    @Override
    public void run(String... args) {
        logger.info(pizzaConfig.toString());
    }
}
