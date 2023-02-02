package com.github.RuSichPT.WBtelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WbTelegrambotApplication {

    public static void main(String[] args) {
        SpringApplication.run(WbTelegrambotApplication.class, args);
    }

}
