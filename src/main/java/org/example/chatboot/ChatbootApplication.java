package org.example.chatboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ChatbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatbootApplication.class, args);
    }

}
