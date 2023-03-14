package com.github.RuSichPT.WBtelegrambot.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Service for sending messages via telegram-bot.
 */
public interface SendBotService {
    /**
     * Send message via telegram bot.
     *
     * @param chatId  provided chatId in which messages would be sent.
     * @param message provided message to be sent.
     */
    void sendMessage(Long chatId, String message);

    void sendMessage(Long chatId, List<String> messages);

    void sendMessage(Long chatId, String message, List<List<InlineKeyboardButton>> listButtons);

    void sendPhoto(Long chatId, URL url, String message) throws IOException;
}
