package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String START_MESSAGE = "Привет. Я Wildberries Telegram Bot. Теперь я буду уведомлять тебя о новых заказах."
            + " Напиши /help чтобы узнать что я еще умею.";

    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        String userName = update.getMessage().getFrom().getUserName();

        if (telegramUserService.findUserByChatId(chatId).isEmpty()) {
            TelegramUser user = new TelegramUser(chatId, userName);
            telegramUserService.saveUser(user);
        }

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
