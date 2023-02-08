package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnauthorizedCommand extends AbstractWbCommand {
    public UnauthorizedCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        super(sendBotMessageService, telegramUserService);
    }

    public final static String MESSAGE = "<b>Ты не авторизован!</b>\n\n"
            + "Чтобы <b>начать работу</b> со мной, пришли мне WB токен (стандартный), используя следующую команду:\n"
            + CommandName.SET_WB_TOKEN.getCommandName() + " WB токен\n\n";

    @Override
    public String executeWbCommand(Update update) {
        return MESSAGE;
    }
}
