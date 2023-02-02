package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StopCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String STOP_MESSAGE = "Уведомления о новыз заказах отключены. До свидания!  \uD83D\uDE1F.";

    public StopCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        String userName= update.getMessage().getFrom().getUserName();

        if (telegramUserService.findUserByChatId(chatId).isPresent()){
            telegramUserService.deleteUserByChatId(chatId);
        }
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), STOP_MESSAGE);
    }
}
