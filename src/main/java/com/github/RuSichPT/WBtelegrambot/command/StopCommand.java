package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command {

    private final SendBotService sendBotService;
    private final TelegramUserService telegramUserService;

    public final static String STOP_MESSAGE = "Уведомления о новыз заказах отключены. Wildberries token удален. До свидания!  \uD83D\uDE1F.";

    public StopCommand(SendBotService sendBotService, TelegramUserService telegramUserService) {
        this.sendBotService = sendBotService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();

        if (telegramUserService.findUserByChatId(chatId).isPresent()) {
            telegramUserService.deleteUserByChatId(chatId);
        }
        sendBotService.sendMessage(update.getMessage().getChatId(), STOP_MESSAGE);
    }
}
