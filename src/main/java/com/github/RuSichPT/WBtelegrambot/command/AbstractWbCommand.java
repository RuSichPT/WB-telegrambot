package com.github.RuSichPT.WBtelegrambot.command;

import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageService;
import com.github.RuSichPT.WBtelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractWbCommand implements Command {
    protected final SendBotMessageService sendBotMessageService;
    protected final TelegramUserService telegramUserService;

    public abstract void executeWbCommand(Update update);

    public AbstractWbCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    private AbstractWbCommand filter(Update update) {
        if (telegramUserService.hasWbToken(update.getMessage().getChatId())) {
            return this;
        } else {
            return new UnauthorizedCommand(sendBotMessageService, telegramUserService);
        }
    }

    @Override
    public final void execute(Update update) {
        filter(update).executeWbCommand(update);
    }

    @Override
    public void executeCallback(Update update) {

    }
}
