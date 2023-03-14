package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * No {@link Command}.
 */
public class NoCommand implements Command {

    private final SendBotService sendBotService;

    public static final String NO_MESSAGE = "Я поддерживаю команды, начинающиеся со слеша(/).\n"
            + "Чтобы посмотреть список команд введите /help";

    public NoCommand(SendBotService sendBotService) {
        this.sendBotService = sendBotService;
    }

    @Override
    public void execute(Update update) {
        sendBotService.sendMessage(update.getMessage().getChatId(), NO_MESSAGE);
    }
}
