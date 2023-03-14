package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownCommand implements Command {

    private final SendBotService sendBotService;

    public static final String UNKNOWN_MESSAGE = "Не понимаю тебя \uD83D\uDE1F, напиши /help чтобы узнать что я понимаю.";

    public UnknownCommand(SendBotService sendBotService) {
        this.sendBotService = sendBotService;
    }

    @Override
    public void execute(Update update) {
        sendBotService.sendMessage(update.getMessage().getChatId(), UNKNOWN_MESSAGE);
    }
}
