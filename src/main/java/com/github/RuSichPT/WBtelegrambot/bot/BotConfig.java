package com.github.RuSichPT.WBtelegrambot.bot;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BotConfig {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;
}