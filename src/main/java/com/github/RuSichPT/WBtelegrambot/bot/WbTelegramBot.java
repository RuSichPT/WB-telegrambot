package com.github.RuSichPT.WBtelegrambot.bot;

import com.github.RuSichPT.WBtelegrambot.command.CommandContainer;
import com.github.RuSichPT.WBtelegrambot.service.SendBotMessageServiceImpl;
import com.github.RuSichPT.WBtelegrambot.wbclient.WbClientPrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.RuSichPT.WBtelegrambot.command.CommandName.NO;

@Component
public class WbTelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    private final CommandContainer commandContainer;

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    @Autowired
    public WbTelegramBot(WbClientPrices wbClientPrices) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), wbClientPrices);
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();

            String commandName;
            if (message.startsWith(COMMAND_PREFIX)) {
                commandName = message.split(" ")[0].toLowerCase();
            } else {
                commandName = NO.getCommandName();
            }
            commandContainer.findCommand(commandName).execute(update);
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
