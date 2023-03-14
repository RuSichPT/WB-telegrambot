package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractWbCommand implements Command {
    protected final SendBotService sendBotService;
    protected final TelegramUserService telegramUserService;

    public abstract void executeWbCommand(Update update);

    public AbstractWbCommand(SendBotService sendBotService, TelegramUserService telegramUserService) {
        this.sendBotService = sendBotService;
        this.telegramUserService = telegramUserService;
    }

    private AbstractWbCommand filter(Update update) {
        if (telegramUserService.hasWbToken(update.getMessage().getChatId())) {
            return this;
        } else {
            return new UnauthorizedCommand(sendBotService, telegramUserService);
        }
    }

    @Override
    public final void execute(Update update) {
        filter(update).executeWbCommand(update);
    }

}
