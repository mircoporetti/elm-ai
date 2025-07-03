package me.mircoporetti.elmai.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan("me.mircoporetti.elmai")
@ComponentScan("me.mircoporetti.elmai")
public class ElmAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElmAiApplication.class, args);
    }

}