package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.repository.entity.TelegramUser;
import com.github.RuSichPT.WBtelegrambot.service.SendBotService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class StartCommand implements Command {

    private final SendBotService sendBotService;
    private final TelegramUserService telegramUserService;

    public final static String START_MESSAGE = "Привет. <b>Я Wildberries Telegram Bot</b>.\n" +
            "Я помогу тебе с управлением на Wildberries."
            + " А также я умею уведомлять тебя о новых заказах. \n\n"
            + "Чтобы <b>начать работу</b> со мной, пришли мне WB токен (стандартный), используя следующую команду:\n"
            + CommandName.SET_WB_TOKEN.getCommandName() + " WB токен\n\n"
            + "Напиши /help чтобы узнать что я еще умею.";

    public StartCommand(SendBotService sendBotService, TelegramUserService telegramUserService) {
        this.sendBotService = sendBotService;
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

        sendBotService.sendMessage(chatId, START_MESSAGE);
    }
}
