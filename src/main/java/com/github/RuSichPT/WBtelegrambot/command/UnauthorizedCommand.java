package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnauthorizedCommand extends AbstractWbCommand {
    public UnauthorizedCommand(SendBotService sendBotService, TelegramUserService telegramUserService) {
        super(sendBotService, telegramUserService);
    }

    public final static String MESSAGE = "<b>Ты не авторизован!</b>\n\n"
            + "Чтобы <b>начать работу</b> со мной, пришли мне WB токен (стандартный), используя следующую команду:\n"
            + CommandName.SET_WB_TOKEN.getCommandName() + " WB токен\n\n";

    @Override
    public void executeWbCommand(Update update) {
        sendBotService.sendMessage(update.getMessage().getChatId(), MESSAGE);
    }
}
