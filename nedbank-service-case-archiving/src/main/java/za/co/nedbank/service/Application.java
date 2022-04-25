package za.co.nedbank.service;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableBatchProcessing
@Configuration
public class Application {
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
