package com.trial.kafka;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KafkaApplication {

    public static void main(String[] args) {

        new SpringApplicationBuilder(KafkaApplication.class)
                .logStartupInfo(false)
                .web(WebApplicationType.NONE).run();

    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> new HelloKafka().start();
    }
}

